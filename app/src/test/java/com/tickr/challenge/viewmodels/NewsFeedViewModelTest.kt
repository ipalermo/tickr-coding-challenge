@file:Suppress("FunctionName")

package com.tickr.challenge.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.tickr.challenge.api.GuardianService
import com.tickr.challenge.data.GuardianArticle
import com.tickr.challenge.data.GuardianRepository
import com.tickr.challenge.data.MainCoroutineRule
import com.tickr.challenge.data.runBlockingTest
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.verify

class NewsFeedViewModelTest {

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val testDispatcher = coroutineRule.testDispatcher

    @Test
    fun searchContent_searchesResultsFromRepository() = coroutineRule.runBlockingTest {
        // Given a view model with a mocked repository
        val pagedFlow: Flowable<PagingData<GuardianArticle>> = Flowable.create(
            FlowableOnSubscribe<PagingData<GuardianArticle>> {
            },
            BackpressureStrategy.BUFFER
        )
        val mockRepository = mock<GuardianRepository> {
            on { getSearchResultStream("") }.doReturn(pagedFlow)
        }
        val viewModel = NewsViewModel(mockRepository)

        // When searchContent is called
        viewModel.searchContent("")

        // Then the repository attempts to fetch data
        verify(mockRepository).getSearchResultStream("")

//        viewModel.swipeRefreshing.observeForTesting {
//            // And the swipe refreshing status is set to false
//            assertEquals(false, LiveDataTestUtil.getValue(viewModel.swipeRefreshing))
//        }
    }
}
