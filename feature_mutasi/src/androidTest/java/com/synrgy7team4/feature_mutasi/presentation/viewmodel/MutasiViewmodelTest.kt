package com.synrgy7team4.feature_mutasi.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class MutasiViewmodelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = TransferViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMutations() {
    }

    @Test
    fun getMutationsbydate() {
    }

    @Test
    fun filterByDateRange() {
    }

    @Test
    fun groupMutationsByDate() {
    }

    @Test
    fun fetchFilteredUserData() {
    }

    @Test
    fun loadMutations() {
    }

    @Test
    fun loadDummyData() {
    }
}