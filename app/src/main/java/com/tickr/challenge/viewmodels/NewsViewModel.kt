package com.tickr.challenge.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.tickr.challenge.data.GuardianArticle
import com.tickr.challenge.data.GuardianRepository
import io.reactivex.Flowable
import javax.inject.Named

class NewsViewModel @ViewModelInject constructor(
    @Named("defaultRepository") private val repository: GuardianRepository
) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flowable<PagingData<GuardianArticle>>? = null

    fun searchContent(queryString: String): Flowable<PagingData<GuardianArticle>> {
        currentQueryValue = queryString
        val newResult = repository
            .getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
