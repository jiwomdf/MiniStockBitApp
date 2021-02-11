package com.katilijiwo.ministockbitapp.ui.watchlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.katilijiwo.ministockbitapp.R
import com.katilijiwo.ministockbitapp.base.BaseFragment
import com.katilijiwo.ministockbitapp.data.remote.websocket.CryptoCompareWebSocket
import com.katilijiwo.ministockbitapp.data.remote.websocket.CryptoCompareWebSocket.Companion.NORMAL_CLOSURE_STATUS
import com.katilijiwo.ministockbitapp.databinding.FragmentWatchListBinding
import com.katilijiwo.ministockbitapp.ui.MainActivity
import com.katilijiwo.ministockbitapp.ui.watchlist.adapter.WatchListLoadStateAdapter
import com.katilijiwo.ministockbitapp.ui.watchlist.adapter.WatchListPageAdapter
import com.katilijiwo.ministockbitapp.ui.watchlist.adapter.WatchListSpinnerAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class WatchListFragment : BaseFragment<FragmentWatchListBinding>(
    R.layout.fragment_watch_list
), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: WatchListViewModel by viewModel()
    private val webSocketClient: Request by inject()
    private val subs = arrayListOf("5~CCCAGG~BTC~USD")
    private var isFromRefresh = false
    lateinit var watchListPageAdapter: WatchListPageAdapter
    lateinit var client: OkHttpClient
    lateinit var webSocket: WebSocket

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
        startWebSocket()
    }

    private fun startWebSocket(){
        client = OkHttpClient()
        webSocket = client.newWebSocket(webSocketClient, CryptoCompareWebSocket(subs))
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
        client.dispatcher().executorService().shutdown()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
        setObserver()
        setUpNavigationView()
        viewModel.searchCryptoCompare()
    }

    private fun setUpNavigationView() {
        (activity as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun setListener() {
        super.setListener()
        binding.ivMenu.setOnClickListener(this)
        binding.srlWatchList.setOnRefreshListener(this)
    }

    private fun setObserver() {
        viewModel.datas.observe(viewLifecycleOwner, {
            watchListPageAdapter.submitData(lifecycle, it)
        })
        watchListPageAdapter.addLoadStateListener { combinedLoadStates  ->
            if(combinedLoadStates.source.refresh is LoadState.NotLoading &&
                combinedLoadStates.append.endOfPaginationReached &&
                watchListPageAdapter.itemCount < 1){
                setComponentVisibility(DATA_NOT_FOUND)
            }

            when(combinedLoadStates.source.refresh){
                is LoadState.Loading -> {
                    setShimmerEffectVisibility(true)
                }
                is LoadState.NotLoading -> {
                    setShimmerEffectVisibility(false)
                    binding.srlWatchList.isRefreshing = false
                    if(combinedLoadStates.source.refresh !is LoadState.Error){
                        setComponentVisibility(DATA_FOUND)
                    } else {
                        setComponentVisibility(DATA_NOT_FOUND)
                    }
                }
                is LoadState.Error -> {
                    setShimmerEffectVisibility(false)
                    setComponentVisibility(DATA_NOT_FOUND)
                    binding.srlWatchList.isRefreshing = false
                    setTextViewErrorMessage(combinedLoadStates)
                }
            }
        }
    }

    private fun setTextViewErrorMessage(combinedLoadStates: CombinedLoadStates) {
        try {
            binding.tvErrorMessage.text = (combinedLoadStates.refresh as LoadState.Error).error.message
        } catch (ex :Exception){
            binding.tvErrorMessage.text = getString(R.string.data_not_found)
        }
    }

    private fun setupSpinner() {
        val listWatchOption: Array<String> = resources.getStringArray(R.array.watch_list_option)
        binding.sWatchList.adapter = WatchListSpinnerAdapter(
            requireActivity(),
            R.layout.list_spinner_item,
            listWatchOption.toList()
        )
    }

    private fun setComponentVisibility(status: Int) {
        when(status){
            DATA_FOUND -> {
                binding.rvWatchList.visibility = View.VISIBLE
                binding.tvErrorMessage.visibility = View.GONE
            }
            DATA_NOT_FOUND -> {
                binding.rvWatchList.visibility = View.GONE
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
        binding.shimmerFrameLayout.stopShimmerAnimation()
        binding.shimmerFrameLayout.visibility = View.GONE
    }

    private fun setShimmerEffectVisibility(isLoading: Boolean){
        if(isLoading) {
            if(!isFromRefresh){
                binding.shimmerFrameLayout.startShimmerAnimation()
                binding.shimmerFrameLayout.visibility = View.VISIBLE
            }
        } else {
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(){
        watchListPageAdapter = WatchListPageAdapter()
        binding.rvWatchList.apply {
            adapter = watchListPageAdapter.withLoadStateHeaderAndFooter(
                header = WatchListLoadStateAdapter({watchListPageAdapter.retry()},{loadState -> showScrollError(loadState)}),
                footer = WatchListLoadStateAdapter({watchListPageAdapter.retry()},{loadState -> showScrollError(loadState)})
            )
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_menu -> {
                (activity as MainActivity).binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun showScrollError(loadState: LoadState) {
        try {
            Toast.makeText(requireContext(), (loadState as LoadState.Error).error.message, Toast.LENGTH_SHORT).show()
        }
        catch (ex: Exception){
            Toast.makeText(requireContext(), resources.getString(R.string.text_error_title), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRefresh() {
        isFromRefresh = true
        watchListPageAdapter.submitData(lifecycle, PagingData.empty())
        watchListPageAdapter.notifyDataSetChanged()
        viewModel.searchCryptoCompare()
    }

}