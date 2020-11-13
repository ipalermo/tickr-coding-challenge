package com.tickr.challenge.data

import androidx.paging.rxjava2.RxPagingSource
import com.tickr.challenge.api.GuardianService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

private const val GUARDIAN_STARTING_PAGE_INDEX = 1

class GuardianPagingSource(
    private val service: GuardianService,
    private val query: String,
    private val orderBy: String
) : RxPagingSource<Int, GuardianArticle>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GuardianArticle>> {
        val page = params.key ?: GUARDIAN_STARTING_PAGE_INDEX

        return service.searchContent(
            query = query,
            page = page,
            pageSize = params.loadSize,
            orderBy = orderBy,
            showFields = listOf("headline", "trailText", "thumbnail").joinToString(",")
        )
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it.root, page) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(response: GuardianSearchResponseRoot, page: Int): LoadResult<Int, GuardianArticle> {
        return LoadResult.Page(
            data = response.results,
            prevKey = if (page == GUARDIAN_STARTING_PAGE_INDEX) null else page - 1,
            nextKey = if (page == response.totalPages) null else page + 1
        )
    }
}
