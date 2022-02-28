package cn.itbk.smallbox.Module.Web

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import cn.itbk.smallbox.APP.Base.setSupportActionBar
import cn.itbk.smallbox.APP.Base.supportActionBar
import cn.itbk.smallbox.APP.Base.viewBinding
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentWebviewBinding
import com.github.fragivity.navigator
import com.github.fragivity.pop
import com.just.agentweb.AgentWeb
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import com.github.fragivity.swipeback.setEnableGesture
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding


/**
 * @简介 描述作用
 * @作者 Hor QQ:2786773385
 * @版本: 1.0
 * @创建日期 2021-09-18
 */
class WebViewFragment : Fragment(R.layout.fragment_webview) {
    private val binding: FragmentWebviewBinding by viewBinding()
    private lateinit var mAgentWeb: AgentWeb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback( this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // 拦截back键事件
                    if(mAgentWeb.webCreator.webView.canGoBack()){
                        mAgentWeb.webCreator.webView.goBack()
                    }
                    else {
                        navigator.pop()
                    }
                }
            })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setSupportActionBar(binding.webToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true) //设置返回键可用
        setEnableGesture(true) //一句话开启SwipeBack
        binding.webToolbar.title = requireArguments().getString("title")
        mAgentWeb = AgentWeb.with(requireParentFragment()) //
            .setAgentWebParent(binding.webWebView, -1, LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ))
           .useDefaultIndicator()
            .setWebViewClient(WebViewClient())
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    binding.webToolbar.title = title
                }
            })
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .createAgentWeb() //
            .ready() //
            .go(requireArguments().getString("url"))



    }
    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroyView() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroyView()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navigator.pop()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}