package com.tickr.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tickr.challenge.adapters.ArticleAdapter
import com.tickr.challenge.databinding.FragmentNewsBinding
import com.tickr.challenge.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val adapter = ArticleAdapter()
    private val disposable = CompositeDisposable()
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.newsList.adapter = adapter
        search()

        return binding.root
    }

    private fun search(query: String = "") {
        disposable.add(
            viewModel.searchContent(query).subscribe {
                adapter.submitData(lifecycle, it)
            }
        )
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
