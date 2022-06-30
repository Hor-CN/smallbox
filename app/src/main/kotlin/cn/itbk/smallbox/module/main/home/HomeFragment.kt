package cn.itbk.smallbox.module.main.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.base.BaseConstant
import cn.itbk.smallbox.app.base.observeState
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.app.state.EmptyState
import cn.itbk.smallbox.app.state.ErrorState
import cn.itbk.smallbox.app.state.LoadingState
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.databinding.FragmentHomeBinding
import cn.itbk.smallbox.model.applet.AppletConfigModel
import cn.itbk.smallbox.module.info.InfoFragment
import cn.itbk.smallbox.module.main.MainViewAction
import cn.itbk.smallbox.module.main.MainViewModel
import cn.itbk.smallbox.views.RvItemDecoration
import com.drake.brv.utils.linear
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import com.github.fragivity.applySlideInOut
import com.github.fragivity.navigator
import com.github.fragivity.push
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener
import com.kongzue.dialogx.util.TextInfo
import com.kongzue.filedialog.FileDialog
import com.kongzue.filedialog.interfaces.FileSelectCallBack
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState
import java.io.File


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var multiState: MultiStateContainer
    private val viewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.fragmentHomeToolbar.apply {
            addStatusBarTopPadding()
            inflateMenu(R.menu.home_menu)
        }

        binding.fragmentHomeRv.apply {
            multiState = bindMultiState()
            addItemDecoration(
                RvItemDecoration()
                    .setMargin(220f, 50f)
            )
        }.linear().setup {
            addType<AppletConfigModel>(R.layout.item_home_applet)
            itemDifferCallback = HomeDiffCallback()
            setHasStableIds(true)
            onClick(R.id.item_home_applet_rl, R.id.item_home_applet_open) {
                mainViewModel.dispatch(MainViewAction.OpenApplet(getModel<AppletConfigModel>().id))
            }
            onLongClick(R.id.item_home_applet_rl) {
                showBottomMenu(getModel())
            }
        }

        setEvents()
    }


    private fun setEvents() {
        // 创建拓展
        binding.fragmentHomeToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_create_applet) {
                XXPermissions.with(this)
                    .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                    .request(object : OnPermissionCallback {
                        override fun onGranted(permissions: MutableList<String>, all: Boolean) {}
                        override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                            if (never) {
                                PopTip.show("被永久拒绝授权，请手动授予存储权限")
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(requireContext(), permissions)
                            } else {
                                PopTip.show("获取存储权限失败")
                            }
                        }
                    })

                BottomMenu.show(arrayOf(getString(R.string.ImportApplet)))
                    .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>() {
                        override fun getIcon(
                            dialog: BottomMenu?,
                            index: Int,
                            menuText: String?
                        ): Int {
                            when (index) {
                                0 -> return R.drawable.ic_create_applet
                            }
                            return 0
                        }
                    })
                    .onMenuItemClickListener =
                    OnMenuItemClickListener { _, _, _ ->
                        FileDialog.build().setSuffixArray(arrayOf(BaseConstant.WGT)) //只允许 wgt 后缀
                            .selectFile(object : FileSelectCallBack() {
                                override fun onSelect(file: File, filePath: String) {
                                    mainViewModel.dispatch(MainViewAction.UnWgt(file.nameWithoutExtension,filePath,false))
                                }
                            })
                        false
                    }
            }
            false
        }

    }

    private fun observeViewModel() {
        viewModel.viewStates.run {

            observeState(viewLifecycleOwner, HomeViewState::appletList) {
                if (it.isNotEmpty()) {
                    binding.fragmentHomeRv.setDifferModels(it as MutableList<AppletConfigModel>)
                }
            }

            observeState(viewLifecycleOwner, HomeViewState::pageState) {
                when (it) {
                    is PageState.Loading -> multiState.show(LoadingState())
                    is PageState.Success -> multiState.show(SuccessState())
                    is PageState.Empty -> multiState.show<EmptyState> { state ->
                        state.setEmptyMsg(it.message)
                        state.retry(getString(R.string.go)) {
                            mainViewModel.dispatch(MainViewAction.TabsAt(1))
                        }
                    }
                    is PageState.Error -> multiState.show<ErrorState> { state ->
                        state.setEmptyMsg(it.message)
                        state.retry {
                            viewModel.dispatch(HomeViewAction.LoadApplets)
                        }
                    }
                }
            }

        }
    }

    private fun showBottomMenu(data: AppletConfigModel) {
        BottomMenu.show(arrayOf("运行", "查看详情", "卸载插件"))
            .setTitle(data.name)
            .setMessage(data.description)
            .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(true) {
                override fun getIcon(bottomMenu: BottomMenu, index: Int, menuText: String): Int {
                    when (menuText) {
                        "运行" -> return R.drawable.ic_run
                        "查看详情" -> return R.drawable.img_menu_ck
                        "卸载插件" -> return R.drawable.uninstall
                    }
                    return 0
                }
            })
            .setOnMenuItemClickListener { _: BottomMenu?, text: CharSequence?, _: Int ->
                when (text) {
                    "运行" ->  mainViewModel.dispatch(MainViewAction.OpenApplet(data.id))
                    "查看详情" -> navigator.push(InfoFragment::class) {
                        arguments = bundleOf(
                            "data" to data
                        )
                        applySlideInOut()
                    }
                    "卸载插件" -> MessageDialog.show("提示", "确认删除吗？", "确定", "取消")
                        .setOkTextInfo(
                            TextInfo().setFontColor(Color.RED).setBold(true)
                        ).okButtonClickListener =
                        OnDialogButtonClickListener { _: MessageDialog?, _: View? ->
                            viewModel.dispatch(HomeViewAction.DeleteApplet(data.id))
                            false
                        }
                }
                false
            }
    }

}