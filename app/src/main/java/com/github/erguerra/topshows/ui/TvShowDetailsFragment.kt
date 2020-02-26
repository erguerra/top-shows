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
import com.github.erguerra.topshows.utils.ERROR
import com.github.erguerra.topshows.utils.SUBMIT_TO_LIST_DEBUG_TAG
import com.github.erguerra.topshows.utils.TV_SHOW_ID_SERIALIZABLE_KEY
import com.github.erguerra.topshows.view_model.RelatedTvShowsListViewModel
import com.github.erguerra.topshows.view_model.TvShowDetailsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_tv_show_details.*
import kotlinx.android.synthetic.main.fragment_tv_show_details.view.*


class TvShowDetailsFragment : Fragment() {

    private var recyclerState: Parcelable? = null

    private val parameters: HashMap<String?, Any?> = hashMapOf()

    private lateinit var relatedTvShowsListViewModel: RelatedTvShowsListViewModel

    private lateinit var disposable: Disposable

    private lateinit var relatedTvShowsRecyclerView: RecyclerView


    private val relatedTvShowsListAdapter: RelatedTvShowsListAdapter by lazy {
        RelatedTvShowsListAdapter(R.layout.related_tv_show)
    }

    private val detailsViewModel: TvShowDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(TvShowDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTvShowDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.detailsViewModel = detailsViewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        arguments?.let{
            detailsViewModel.tvShowId.value = getTvShowIdFromBundle()
            detailsViewModel.updateDetailsById()
        }

        setupRelatedTvShowsList(related_tv_shows_list)
    }

    private fun getTvShowIdFromBundle(): Int {
        val bundle = arguments
        return bundle?.getSerializable(TV_SHOW_ID_SERIALIZABLE_KEY) as Int
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
        relatedTvShowsListViewModel = RelatedTvShowsListViewModel(parameters, detailsViewModel.tvShowId.value!!)
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
                {exception -> Log.e(SUBMIT_TO_LIST_DEBUG_TAG, ERROR, exception)}
            )
    }


    companion object {
        fun newInstance() : TvShowDetailsFragment{
            return TvShowDetailsFragment()
        }
    }
}
