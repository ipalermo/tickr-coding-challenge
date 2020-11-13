package com.tickr.challenge.utils

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.tickr.challenge.api.GuardianService
import com.tickr.challenge.data.GuardianArticle
import com.tickr.challenge.data.GuardianPagingSource
import com.tickr.challenge.data.GuardianRepository
import io.reactivex.Flowable
import javax.inject.Inject

class FakeGuardianRepository @Inject constructor(private val service: GuardianService) :
    GuardianRepository {

    override fun getSearchResultStream(query: String): Flowable<PagingData<GuardianArticle>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { GuardianPagingSource(service, query, RESULTS_ORDER_BY) }
        ).flowable
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
        private const val RESULTS_ORDER_BY = "newest"
    }
}
