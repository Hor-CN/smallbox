package cn.itbk.smallbox.Module.Main.Home

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import cn.itbk.smallbox.APP.Base.observeState
import cn.itbk.smallbox.APP.Base.viewBinding
import cn.itbk.smallbox.APP.State.EmptyState
import cn.itbk.smallbox.APP.State.ErrorState
import cn.itbk.smallbox.APP.State.LoadingState
import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.Module.Main.MainViewAction
import cn.itbk.smallbox.Module.Main.MainViewModel
import cn.itbk.smallbox.R
import cn.itbk.smallbox.Utils.PickUtils
import cn.itbk.smallbox.Views.RvItemDecoration
import cn.itbk.smallbox.databinding.FragmentHomeBinding
import cn.itbk.smallbox.model.applet.AppletConfigModel
import com.drake.brv.utils.*
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.kongzue.dialogx.dialogs.BottomMenu
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.OnMenuItemClickListener
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState
import io.dcloud.feature.sdk.DCUniMPSDK
import io.dcloud.feature.unimp.config.UniMPReleaseConfiguration
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var multiState: MultiStateContainer
    private val viewModel: HomeViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private val binding: FragmentHomeBinding by viewBinding()
    private val launcher: ActivityResultLauncher<Boolean> =
        registerForActivityResult(ResultContract()) {
            if (it == null) {
                return@registerForActivityResult
            }
            val name = PickUtils.getFileName(requireContext(), it.data)
            val filepath = PickUtils.getPath(requireContext(), it.data)
            if (File(filepath).extension == "wgt") {

                wgt(name, filepath)

            }
        }

    private fun wgt(filename: String, filepath: String) {
        val un = UniMPReleaseConfiguration()
        un.wgtPath = filepath
        un.password = ""
        DCUniMPSDK.getInstance().releaseWgtToRunPath(filename, un) { i, _ ->
            if (i == 1) {
                MotionToast.createColorToast(
                    requireActivity(),
                    "导入成功",
                    "${filename}拓展导入成功!",
                    MotionToastStyle.SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
                )
                viewModel.dispatch(HomeViewAction.OnRefresh)
            } else {
//                PopTip.show("${filename}拓展导入失败")
                MotionToast.createToast(
                    requireActivity(),
                    "导入失败",
                    "${filename}文件错误或已损坏!",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    null
                )
            }
        }
    }

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
        }

        setEvents()
    }

    private fun setEvents() {
        //下拉刷新
        binding.fragmentHomeSrl.setOnRefreshListener {
            viewModel.dispatch(HomeViewAction.OnRefresh)
            binding.fragmentHomeSrl.finishRefresh(true)
        }

        // 创建拓展
        binding.fragmentHomeToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_create_applet) {

                XXPermissions.with(this)
                    .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                    .request(object : OnPermissionCallback {
                        override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                        }

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

                BottomMenu.show(arrayOf("导入小程序"))
                    .onMenuItemClickListener =
                    OnMenuItemClickListener { dialog, text, index ->
                        launcher.launch(true)
                        false
                    }
            }
            false
        }

//        //列表的点击事件
//        adapter.setOnItemChildClickListener { adapter, view, position ->
//            if (view.id == R.id.item_home_applet_rl) {
//                gotoLuaView(adapter.data[position] as AppletConfigModel)
//            }
//        }
//
//        adapter.setOnItemChildLongClickListener { adapter, view, position ->
//            val data = adapter.data[position] as AppletConfigModel
//            if (view.id == R.id.item_home_applet_rl) {
//
//                BottomMenu.show(arrayOf("运行", "创意工坊", "查看详情", "卸载插件"))
//                    .setTitle(data.name)
//                    .setMessage(data.description)
//                    .setOnIconChangeCallBack(object : OnIconChangeCallBack<BottomMenu>(true) {
//                        override fun getIcon(
//                            bottomMenu: BottomMenu,
//                            index: Int,
//                            menuText: String
//                        ): Int {
//                            when (menuText) {
//                                "运行" -> return R.drawable.ic_run
//                                "创意工坊" -> return R.drawable.workshop
//                                "查看详情" -> return R.drawable.img_menu_ck
//                                "卸载插件" -> return R.drawable.uninstall
//                            }
//                            return 0
//                        }
//                    })
//                    .setOnMenuItemClickListener { dialog: BottomMenu?, text: CharSequence?, index: Int ->
//                        when (text) {
//                            "运行" -> gotoLuaView(data)
//                            "创意工坊" -> PopTip.show("正在开发")
//                            "查看详情" -> {
//                                val bundle = bundleOf(
//                                    "data" to data
//                                )
//                                navigator.push(InfoFragment::class) {
//                                    arguments = bundle
//                                    applySlideInOut()
//                                }
//                            }
//                            "卸载插件" -> {
//                                MessageDialog.show("提示", "确认删除吗？", "确定", "取消")
//                                    .setOkTextInfo(
//                                        TextInfo().setFontColor(Color.RED).setBold(true)
//                                    ).okButtonClickListener =
//                                    OnDialogButtonClickListener { baseDialog: MessageDialog?, v: View? ->
//                                        false
//                                    }
//                            }
//                        }
//                        false
//                    }
//            }
//            true
//        }

    }

    private fun gotoLuaView(data: AppletConfigModel) {
        DCUniMPSDK.getInstance().openUniMP(requireContext(), data.id)
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
                    is PageState.Success -> {
                        multiState.show(SuccessState())
                    }
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

//        viewModel.viewEvents.observeEvent(viewLifecycleOwner) {
//            when(it) {
//                is HomeViewEvent.CreateApplet -> {
//
//                }
//            }
//        }

    }

}

