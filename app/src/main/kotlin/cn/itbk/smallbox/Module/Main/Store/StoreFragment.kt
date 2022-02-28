package cn.itbk.smallbox.Module.Main.Store

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.itbk.smallbox.APP.Base.observeState
import cn.itbk.smallbox.APP.Base.viewBinding
import cn.itbk.smallbox.APP.State.ErrorState
import cn.itbk.smallbox.APP.State.LoadingState
import cn.itbk.smallbox.APP.State.PageState
import cn.itbk.smallbox.model.applet.AppletModel
import cn.itbk.smallbox.model.GridMenuItem
import cn.itbk.smallbox.model.Srore.Carousel
import cn.itbk.smallbox.model.Srore.ListModel
import cn.itbk.smallbox.Module.Adapter.InfoListAdapter
import cn.itbk.smallbox.Module.Adapter.StoreBannerAdapter
import cn.itbk.smallbox.Module.Adapter.StoreListAdapter
import cn.itbk.smallbox.Module.Applet.AppletFragment
import cn.itbk.smallbox.Module.Search.SearchFragment
import cn.itbk.smallbox.Module.Web.WebViewFragment
import cn.itbk.smallbox.R
import cn.itbk.smallbox.databinding.FragmentStoreBinding
import com.github.fragivity.applySlideInOut
import com.github.fragivity.navigator
import com.github.fragivity.push
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zhpan.bannerview.BannerViewPager
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState
import java.util.*


class StoreFragment : Fragment(R.layout.fragment_store) {

    private lateinit var multiState: MultiStateContainer
    private val viewModel: StoreViewModel by activityViewModels()
    private val binding: FragmentStoreBinding by viewBinding()
    private var storeListBeans: MutableList<ListModel> = ArrayList<ListModel>()

    private lateinit var adapter: StoreListAdapter
    private lateinit var mViewPager: BannerViewPager<Carousel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.fragmentStoreToolbar.addStatusBarTopPadding()
        binding.fragmentStoreToolbar.inflateMenu(R.menu.store_menu)
        multiState = binding.fragmentStoreRv.bindMultiState()

//        binding.fragmentStoreRv.setHasFixedSize(true)
        binding.fragmentStoreRv.layoutManager = GridLayoutManager(context, 2)
        binding.fragmentStoreRv.adapter = StoreListAdapter(
            R.layout.item_store_head,
            R.layout.item_store_content,
            storeListBeans
        ).also {
            it.setDiffCallback(diffCallback = StoreDiffCallback())
            adapter = it
        }

        adapter.addHeaderView(getHeaderBannerView())
        adapter.addHeaderView(getHeaderMenuView())

        setEvents()
    }

    private fun setEvents() {
        //下拉刷新
        binding.fragmentStoreSrl.setOnRefreshListener {
            viewModel.dispatch(StoreViewAction.LoadApplets)
            binding.fragmentStoreSrl.finishRefresh(true)
        }

        binding.fragmentStoreToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_search) {
                navigator.push(SearchFragment::class) {
                    applySlideInOut()
                }
            }
            false
        }

        adapter.addChildClickViewIds(R.id.item_store_content_rl)

        adapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.item_store_content_rl) {
                navigator.push(AppletFragment::class) {
                    arguments = bundleOf(
                        "data" to adapter.data[position].`object` as AppletModel
                    )
                    applySlideInOut()
                }
            }
        }

    }

    private fun observeViewModel() {
        viewModel.viewStates.run {

            observeState(viewLifecycleOwner, StoreViewState::bannerList) {
                if (it.isNotEmpty()) {
                    mViewPager.refreshData(it)
                }
            }

            observeState(viewLifecycleOwner, StoreViewState::contentList) {
                if (it.isNotEmpty()) {
                    adapter.setDiffNewData(it.toMutableList())
                }
            }

            observeState(viewLifecycleOwner, StoreViewState::pageState) {
                when (it) {
                    is PageState.Loading -> multiState.show(LoadingState())
                    is PageState.Success -> multiState.show(SuccessState())
                    is PageState.Error -> {
                        multiState.show<ErrorState> { state ->
                            state.setEmptyMsg(it.message)
                            state.retry {
                                viewModel.dispatch(StoreViewAction.LoadApplets)
                            }
                        }
                    }
                    else -> {}
                }
            }

        }

    }

    private fun getHeaderBannerView(): View {
        val adapter = StoreBannerAdapter()
        val view: View =
            layoutInflater.inflate(R.layout.item_store_banner, binding.fragmentStoreRv, false)
        mViewPager = view.findViewById(R.id.item_store_banner)
        mViewPager.apply {
            this.adapter = adapter
            setLifecycleRegistry(lifecycle)
            setRevealWidth(0, 100)
//            setPageMargin(5)
            setIndicatorVisibility(View.GONE)
        }.create()
        adapter.setOnClickListener(object : StoreBannerAdapter.OnPageClickListener {
            override fun onPageClick(position: Int) {
                val data = mViewPager.data[position]
                val bundle = bundleOf(
                    "url" to data.redirectUrl
                )
                navigator.push(WebViewFragment::class) {
                    arguments = bundle
                    applySlideInOut()
                }
            }

        })
        return view
    }

    private fun getHeaderMenuView(): View {
        val view: View =
            layoutInflater.inflate(R.layout.item_menu_rv, binding.fragmentStoreRv, false)
        val mSubCatRV: RecyclerView = view.findViewById(R.id.item_menu_rv)
        mSubCatRV.layoutManager = GridLayoutManager(view.context, 4)
        val storeMenu: MutableList<GridMenuItem> = ArrayList<GridMenuItem>()
        storeMenu.add(GridMenuItem(R.drawable.ic_selected, "精选"))
        storeMenu.add(GridMenuItem(R.drawable.ic_classify, "分类"))
        storeMenu.add(GridMenuItem(R.drawable.ic_ranking, "排行"))
        storeMenu.add(GridMenuItem(R.drawable.ic_special, "专题"))
        mSubCatRV.adapter = InfoListAdapter(storeMenu)
        return view
    }

}

