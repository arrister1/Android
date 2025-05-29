package com.synrgy7team4.feature_mutasi.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationDataDomain
import com.synrgy7team4.domain.feature_mutasi.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_mutasi.usecase.MutasiUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class MutasiViewmodelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MutasiViewmodel

    @Mock private lateinit var mutasiUseCase: MutasiUseCase
    @Mock private lateinit var tokenHandler: TokenHandler
    @Mock private lateinit var userHandler: UserHandler

    var mutationGetResponseDomain = MutationGetResponseDomain(listOf<MutationDataDomain>(),true, "")

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = MutasiViewmodel(mutasiUseCase, tokenHandler, userHandler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMutationData() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("jwt")
        Mockito.`when`(userHandler.loadAccountNumber()).thenReturn("123")
        Mockito.`when`(mutasiUseCase.getAllMutations("","")).thenReturn(mutationGetResponseDomain)
        // Act
        viewModel.getMutationData()
        // Assert
        assertEquals(null, viewModel.mutationData.value)
    }

    @Test
    fun filterMutationByType() = runTest {
        // Act
        val result = viewModel.filterMutationByType(listOf<MutationDataDomain>(), "Semua")
        // Assert
        assertEquals(listOf<MutationDataDomain>(), result)
    }

    @Test
    fun groupMutationsByDate() = runTest {
        // Act
        val result = viewModel.groupMutationsByDate(listOf())
        // Assert
        assertEquals(mapOf<String, List<MutationDataDomain>>(), result)
    }
}