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
import com.github.erguerra.topshows.view_model.TvShowListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_tvshow_list.view.*


class TvShowListFragment : Fragment(), RecyclerViewItemClickListener{

    private val params: HashMap<String?, Any?> = hashMapOf()

    private lateinit var viewModel: TvShowListViewModel

    private val adapter: TvShowListAdapter by lazy {
        TvShowListAdapter(this, R.layout.fragment_tvshow)
    }

    private var recyclerState: Parcelable? = null

    private lateinit var disposable: Disposable

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_tvshow_list, container, false)
        setupRecyclerView(view.list)
        return view
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
                {e -> Log.e("SLE", "Error", e)}
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun onRecyclerViewItemClick(view: View?, position: Int) {
        val tvShow = adapter?.let {
            it.currentList?.get(position)
        }

        //TODO: Make transition to Details Activity
    }

    companion object {
        fun newInstance() : TvShowListFragment{
            return TvShowListFragment()
        }
    }
}
