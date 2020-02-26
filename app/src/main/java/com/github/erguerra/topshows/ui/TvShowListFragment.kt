package com.github.erguerra.topshows.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.erguerra.topshows.R
import com.github.erguerra.topshows.ui.adapters.TvShowListAdapter
import com.github.erguerra.topshows.utils.ERROR
import com.github.erguerra.topshows.utils.SUBMIT_TO_LIST_DEBUG_TAG
import com.github.erguerra.topshows.utils.TV_SHOW_ID_SERIALIZABLE_KEY
import com.github.erguerra.topshows.view_model.TvShowListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_tvshow_list.view.*


class TvShowListFragment : Fragment(), RecyclerViewItemClickListener{

    private var recyclerState: Parcelable? = null

    private val params: HashMap<String?, Any?> = hashMapOf()

    private lateinit var viewModel: TvShowListViewModel

    private lateinit var disposable: Disposable

    private lateinit var recyclerView: RecyclerView

    private val adapter: TvShowListAdapter by lazy {
        TvShowListAdapter(this, R.layout.fragment_tvshow)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_tvshow_list, container, false)
        setupRecyclerView(view.list)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onRecyclerViewItemClick(view: View?, position: Int) {
        val tvShow = adapter?.let {
            it.currentList?.get(position)
        }
        tvShow?.let {
            makeFragmentTransaction(it.id)
        }

    }

    private fun createBundle(tvShowId: Int): Bundle{
        val bundle = Bundle()
        bundle.putSerializable(TV_SHOW_ID_SERIALIZABLE_KEY, tvShowId)
        return bundle
    }

    private fun makeFragmentTransaction(tvShowId: Int) {
        val bundle = createBundle(tvShowId)
        val detailsFragment = TvShowDetailsFragment.newInstance()
        fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, detailsFragment)
            ?.addToBackStack(null)
            ?.commit()
        detailsFragment.arguments = bundle
    }

    private fun setupRecyclerView(recycler: RecyclerView){
        val layoutManager = LinearLayoutManager(this.context)
        recyclerView = recycler
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        subscribeToList()
    }

    private fun subscribeToList() {
        viewModel = TvShowListViewModel(params)
        disposable = viewModel.tvShowList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listToSubmit ->
                    adapter.submitList(listToSubmit)
                    if (recyclerState != null) {
                        view?.let {
                            it.list.layoutManager?.onRestoreInstanceState(recyclerState)
                            recyclerState = null
                        }
                    }

                },
                {exception -> Log.e(SUBMIT_TO_LIST_DEBUG_TAG, ERROR, exception)}
            )
    }

    companion object {
        fun newInstance() : TvShowListFragment{
            return TvShowListFragment()
        }
    }
}
