package com.github.erguerra.topshows.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.erguerra.topshows.R
import com.github.erguerra.topshows.databinding.FragmentTvShowDetailsBinding
import com.github.erguerra.topshows.ui.adapters.RelatedTvShowsListAdapter
import com.github.erguerra.topshows.view_model.RelatedTvShowsListViewModel
import com.github.erguerra.topshows.view_model.TvShowDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_tv_show_details.*
import kotlinx.android.synthetic.main.fragment_tv_show_details.view.*


class TvShowDetailsFragment : Fragment(), RecyclerViewItemClickListener {

    private val parameters: HashMap<String?, Any?> = hashMapOf()

    private lateinit var relatedTvShowsListViewModel: RelatedTvShowsListViewModel

    private val relatedTvShowsListAdapter: RelatedTvShowsListAdapter by lazy {
        RelatedTvShowsListAdapter(this, R.layout.related_tv_show)
    }

    private var recyclerState: Parcelable? = null

    private lateinit var disposable: Disposable

    private lateinit var relatedTvShowsRecyclerView: RecyclerView

    private val detailsViewModel: TvShowDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(TvShowDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTvShowDetailsBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.detailsViewModel = detailsViewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupRelatedTvShowsList(related_tv_shows_list)
    }


    private fun setupRelatedTvShowsList(recyclerView: RecyclerView){
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        relatedTvShowsRecyclerView = recyclerView
        relatedTvShowsRecyclerView.layoutManager = layoutManager
        relatedTvShowsRecyclerView.adapter = relatedTvShowsListAdapter
        relatedTvShowsRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        subscribeToList()
    }

    private fun subscribeToList() {
        relatedTvShowsListViewModel = RelatedTvShowsListViewModel(parameters, 456)
        disposable = relatedTvShowsListViewModel.relatedTvShowsList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listToSubmit ->
                    relatedTvShowsListAdapter.submitList(listToSubmit)
                    if (recyclerState != null) {
                        view?.let {
                            it.related_tv_shows_list.layoutManager?.onRestoreInstanceState(recyclerState)
                            recyclerState = null
                        }
                    }

                },
                {e -> Log.e("SLE", "Error", e)}
            )
    }

    override fun onRecyclerViewItemClick(view: View?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        fun newInstance() : TvShowDetailsFragment{
            return TvShowDetailsFragment()
        }
    }
}
