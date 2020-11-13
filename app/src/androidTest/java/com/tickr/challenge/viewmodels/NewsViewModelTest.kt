package com.tickr.challenge.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.test.platform.app.InstrumentationRegistry
import com.tickr.challenge.data.GuardianArticle
import com.tickr.challenge.data.GuardianRepository
import com.tickr.challenge.utilities.getValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

@HiltAndroidTest
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = RuleChain
            .outerRule(hiltRule)
            .around(instantTaskExecutorRule)

    @Inject
    @Named("defaultRepository")
    lateinit var guardianRepository: GuardianRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext

        viewModel = NewsViewModel(guardianRepository)
    }

    @After
    fun tearDown() {
    }

//    @Test
//    fun empty() {
//        viewModel.searchContent("")
//        Mockito.verifyNoMoreInteractions(guardianRepository)
//
//        // Then the new task event is triggered
//        val value = viewModel.searchContent("")
//
//    }

    @Test
    fun basic() {
        var pagingData: PagingData<GuardianArticle>? = null

        viewModel.searchContent("")
                .blockingSubscribe()

        Mockito.verify(guardianRepository).getSearchResultStream("")
        MatcherAssert.assertThat(
            pagingData, CoreMatchers.not(CoreMatchers.nullValue())
        )
    }
}
