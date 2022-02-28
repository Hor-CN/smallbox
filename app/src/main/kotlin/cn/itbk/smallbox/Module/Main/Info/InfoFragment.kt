package cn.itbk.smallbox.Module.Main.Info

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import cn.itbk.smallbox.APP.Base.setSupportActionBar
import cn.itbk.smallbox.APP.Base.supportActionBar
import cn.itbk.smallbox.APP.Base.viewBinding
import cn.itbk.smallbox.model.applet.AppletConfigModel
import cn.itbk.smallbox.model.GridMenuItem
import cn.itbk.smallbox.Module.Adapter.InfoListAdapter
import cn.itbk.smallbox.Module.Main.MainViewAction
import cn.itbk.smallbox.Module.Main.MainViewModel
import cn.itbk.smallbox.R
import cn.itbk.smallbox.Utils.Utils
import cn.itbk.smallbox.databinding.FragmentInfoBinding
import com.bumptech.glide.Glide
import com.github.fragivity.navigator
import com.github.fragivity.pop
import com.kongzue.dialogx.dialogs.BottomDialog
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import java.io.FileInputStream
import java.util.*


/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-08-30
 */
class InfoFragment : Fragment(R.layout.fragment_info) {

    private val binding: FragmentInfoBinding by viewBinding()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback( this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 拦截back键事件
                    back()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initView()
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        binding.toolbar.addStatusBarTopPadding()

        val data: AppletConfigModel = requireArguments().getSerializable("data") as AppletConfigModel

        binding.toolbar.title = data.name
        Glide.with(binding.infoIv.context)
            .load(data.icon)
            //.placeholder(R.drawable.ic_zwt) //加载未完成时显示占位图
            .into(binding.infoIv)

//        binding.infoVersion.text = data.version.name

        binding.infoTip.text = data.description

        binding.infoJj.setOnClickListener {
            BottomDialog.show("简介", data.description)
                .setCancelButton(
                    "取消"
                ) { baseDialog, v -> //...
                    false
                }

        }

//        binding.infoAuthorTip.text = "@"+data.developer.name


        val bitmap = BitmapFactory.decodeStream(FileInputStream(data.icon))
        Palette.from(bitmap).generate {
            val color  = it?.getDominantColor(ContextCompat.getColor(requireContext(),R.color.colorPrimary))
            if (color != null) {
                binding.appBarLayout.setBackgroundColor(color)
                binding.infoNv.setBackgroundColor(color)
                toggleToolbar(Utils.isLightColor(color))
            }
        }



        binding.infoRv.layoutManager = GridLayoutManager(requireContext(),4)
        val list: MutableList<GridMenuItem> = ArrayList<GridMenuItem>()
        list.add(GridMenuItem(R.drawable.ic_edit, "编辑"))
        list.add(GridMenuItem(R.drawable.ic_shortcut, "快捷"))
        list.add(GridMenuItem(R.drawable.ic_uninstall, "卸载"))
        list.add(GridMenuItem(R.drawable.ic_share, "分享"))
        binding.infoRv.adapter = InfoListAdapter(list)



    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            back()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun back() {
        navigator.pop()
        mainViewModel.dispatch(MainViewAction.StateToolbar(true))
    }

    private fun toggleToolbar(boolean:Boolean) {
        val upArrow = ContextCompat.getDrawable(requireContext(), R.drawable.abc_ic_ab_back_material)
        val color: Int = if (boolean) {
            Color.BLACK
        }else {
            Color.WHITE
        }

        if(!boolean) {
            activity?.statusBar {
                transparent()
                light = false
                lvlColor = Color.WHITE
            }
        }
        binding.toolbarLayout.setExpandedTitleColor(color)
        binding.toolbarLayout.setCollapsedTitleTextColor(color)
        binding.toolbarLayout.setExpandedTitleColor(color)
        binding.toolbarLayout.setCollapsedTitleTextColor(color)
        supportActionBar?.setHomeAsUpIndicator(upArrow?.let { it1 ->
            Utils.setColorFilter(
                it1,color)
        })
        binding.infoVersion.setTextColor(color)
    }

}

