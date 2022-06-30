package cn.itbk.smallbox.module.main.store

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import cn.itbk.smallbox.R
import cn.itbk.smallbox.app.base.observeState
import cn.itbk.smallbox.app.base.viewBinding
import cn.itbk.smallbox.app.state.ErrorState
import cn.itbk.smallbox.app.state.LoadingState
import cn.itbk.smallbox.app.state.PageState
import cn.itbk.smallbox.databinding.StoreFragmentBinding
import cn.itbk.smallbox.model.store.GridMenuItem
import cn.itbk.smallbox.model.applet.AppletModel
import cn.itbk.smallbox.model.store.Carousel
import cn.itbk.smallbox.model.store.CarouselModel
import cn.itbk.smallbox.model.store.Good
import cn.itbk.smallbox.model.store.GridMenuModel
import cn.itbk.smallbox.module.adapter.StoreBannerAdapter
import cn.itbk.smallbox.module.applet.AppletFragment
import cn.itbk.smallbox.module.search.SearchFragment
import cn.itbk.smallbox.module.web.WebViewFragment
import com.drake.brv.utils.*
import com.github.fragivity.applySlideInOut
import com.github.fragivity.navigator
import com.github.fragivity.push
import com.zackratos.ultimatebarx.ultimatebarx.addStatusBarTopPadding
import com.zhpan.bannerview.BannerViewPager
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import com.zy.multistatepage.state.SuccessState


class StoreFragment : Fragment(R.layout.store_fragment) {

    private lateinit var multiState: MultiStateContainer
    private val viewModel: StoreViewModel by activityViewModels()
    private val binding: StoreFragmentBinding by viewBinding()
    private lateinit var mViewPager: BannerViewPager<Carousel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()

        binding.fragmentStoreToolbar.addStatusBarTopPadding()
        binding.fragmentStoreToolbar.inflateMenu(R.menu.store_menu)
        multiState = binding.fragmentStoreRv.bindMultiState()

//        binding.fragmentStoreRv.setHasFixedSize(true)
//        binding.fragmentStoreRv.layoutManager = GridLayoutManager(context, 2)
//        binding.fragmentStoreRv.adapter = StoreListAdapter(
//            R.layout.item_store_head,
//            R.layout.item_store_content,
//            storeListBeans
//        ).also {
//            it.setDiffCallback(diffCallback = StoreDiffCallback())
//            adapter = it
//        }
//        adapter.addHeaderView(getHeaderBannerView())
//        adapter.addHeaderView(getHeaderMenuView())
//        binding.fragmentStoreRv.layoutManager = HoverGridLayoutManager(requireContext(), 4).apply {
//            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    if (position < 0) return 1
//                    // 根据类型设置列表item跨度
//                    return when (binding.fragmentStoreRv.bindingAdapter.getItemViewType(position)) {
//                        R.layout.item_store_banner,
//                        R.layout.item_menu_rv   -> 4 // 占一行
//                        else -> 1
//                    }
//                }
//
//            }
//        }
        binding.fragmentStoreRv.linear().setup {
            addType<CarouselModel>(R.layout.store_recycle_banner)
            addType<GridMenuModel>(R.layout.item_menu_rv)
            addType<Good>(R.layout.store_recycle_content)
            itemDifferCallback = StoreDiffCallback()
            onCreate {
                when (it) {
                    R.layout.store_recycle_banner -> {
                        mViewPager = findView(R.id.item_store_banner)
                        mViewPager.apply {
                            adapter = StoreBannerAdapter().apply {
                                setOnClickListener(object : StoreBannerAdapter.OnPageClickListener {
                                    override fun onPageClick(position: Int) {
                                        navigator.push(WebViewFragment::class) {
                                            arguments = bundleOf(
                                                "url" to mViewPager.data[position].redirectUrl
                                            )
                                            applySlideInOut()
                                        }
                                    }
                                })
                            }
                            setLifecycleRegistry(lifecycle)
                            setRevealWidth(0, 100)
                            setIndicatorVisibility(View.GONE)
                        }.create()
                    }
                    R.layout.item_menu_rv -> {
                        val rv = findView<RecyclerView>(R.id.item_menu_rv)
                        rv.grid(4, scrollEnabled = false).setup {
                            addType<GridMenuItem>(R.layout.item_rv_menu)
                        }
                    }
                    R.layout.store_recycle_content -> {
                        findView<RecyclerView>(R.id.store_recycle_item_content_rv).grid(
                            1,
                            scrollEnabled = false
                        ).setup {
                            addType<AppletModel>(R.layout.store_recycle_content_item2)
                            onClick(R.id.store_rv_content_item2_item) {
                                navigator.push(AppletFragment::class) {
                                    arguments = bundleOf(
                                        "data" to getModel<AppletModel>()
                                    )
                                    applySlideInOut()
                                }
                            }
                            onClick(R.id.store_rv_content_item2_open) {
                                navigator.push(AppletFragment::class) {
                                    arguments = bundleOf(
                                        "data" to getModel<AppletModel>()
                                    )
                                    applySlideInOut()
                                }
                            }
                        }
                    }
                }
            }
            onBind {
                when (itemViewType) {
                    R.layout.store_recycle_banner -> {
                        mViewPager.refreshData(getModel<CarouselModel>().listNested)
                    }
                    R.layout.item_menu_rv -> {
                        val rv = findView<RecyclerView>(R.id.item_menu_rv)
                        rv.models = getModel<GridMenuModel>().listNested
                    }
                    R.layout.store_recycle_content -> {
                        findView<RecyclerView>(R.id.store_recycle_item_content_rv)
                            .addModels(getModel<Good>().value)
                    }
                }
            }
        }

        setEvents()
    }

    private fun setEvents() {
        binding.fragmentStoreToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_search) {
                navigator.push(SearchFragment::class) {
                    applySlideInOut()
                }
            }
            false
        }

    }

    private fun observeViewModel() {
        viewModel.viewStates.run {

            observeState(viewLifecycleOwner, StoreViewState::gridMenuList) {
                binding.fragmentStoreRv.bindingAdapter.addHeader(GridMenuModel(it))
            }

            observeState(viewLifecycleOwner, StoreViewState::bannerList) {
                if (it.isNotEmpty()) {
                    binding.fragmentStoreRv.bindingAdapter.addHeader(CarouselModel(it))
                }
            }

            observeState(viewLifecycleOwner, StoreViewState::contentList) {
                if (it.isNotEmpty()) {
                    binding.fragmentStoreRv.bindingAdapter.setDifferModels(it)
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


}