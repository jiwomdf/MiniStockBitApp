package com.katilijiwo.ministockbitapp.ui.watchlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.katilijiwo.ministockbitapp.R
import com.katilijiwo.ministockbitapp.base.BaseFragment
import com.katilijiwo.ministockbitapp.data.remote.json.websocketrequest.WebSocketRequest
import com.katilijiwo.ministockbitapp.data.remote.json.websocketresponse.WebSocketResponse
import com.katilijiwo.ministockbitapp.data.remote.websocket.CryptoCompareWebSocket
import com.katilijiwo.ministockbitapp.data.remote.websocket.CryptoCompareWebSocket.Companion.NORMAL_CLOSURE_STATUS
import com.katilijiwo.ministockbitapp.databinding.FragmentWatchListBinding
import com.katilijiwo.ministockbitapp.ui.watchlist.adapter.WatchListLoadStateAdapter
import com.katilijiwo.ministockbitapp.ui.watchlist.adapter.WatchListPageAdapter
import com.katilijiwo.ministockbitapp.ui.watchlist.adapter.WatchListSpinnerAdapter
import com.katilijiwo.ministockbitapp.util.Constant.SELECTED_PAGE
import okhttp3.*
import okio.ByteString
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class WatchListFragment : BaseFragment<FragmentWatchListBinding>(
    R.layout.fragment_watch_list
), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    lateinit var watchListPageAdapter: WatchListPageAdapter
    private val viewModel: WatchListViewModel by viewModel()
    private val webSocketClient: Request by inject()
    private var isFromRefresh = false

    lateinit var client: OkHttpClient
    lateinit var webSocket: WebSocket
    lateinit var cryptoCompareWebSocket: CryptoCompareWebSocket

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
        startWebSocket()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye!")
        client.dispatcher().executorService().shutdown()
        super.onPause()
    }

    private fun startWebSocket(){
        client = OkHttpClient()
        webSocket = client.newWebSocket(webSocketClient, cryptoCompareWebSocket)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSpinner()
        setObserver()
        viewModel.searchCryptoCompare()
        cryptoCompareWebSocket.getStatusUpdate()
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
            } else {
                setComponentVisibility(DATA_FOUND)
            }

            when(combinedLoadStates.source.refresh){
                is LoadState.Loading -> {
                    setShimmerEffectVisibility(true)
                }
                is LoadState.NotLoading -> {
                    setShimmerEffectVisibility(false)
                    binding.srlWatchList.isRefreshing = false
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
            adapter = watchListPageAdapter.withLoadStateFooter(
                footer = WatchListLoadStateAdapter({ watchListPageAdapter.retry() }, { loadState ->
                    showScrollError(
                        loadState
                    )
                })
            )
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_menu -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }

    private fun showScrollError(loadState: LoadState) {
        try {
            Toast.makeText(
                requireContext(),
                (loadState as LoadState.Error).error.message,
                Toast.LENGTH_SHORT
            ).show()
        }
        catch (ex: Exception){
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.text_error_title),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRefresh() {
        watchListPageAdapter.submitData(lifecycle, PagingData.empty())
        binding.rvWatchList.visibility = View.GONE
        viewModel.searchCryptoCompare()
        isFromRefresh = true
    }

}