package com.synrgy7team4.feature_dashboard.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.model.response.UserDataDomain
import com.synrgy7team4.domain.feature_dashboard.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_dashboard.usecase.BalanceUseCase
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
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    @Mock private lateinit var balanceUseCase: BalanceUseCase
    @Mock private lateinit var tokenHandler: TokenHandler
    @Mock private lateinit var userHandler: UserHandler

    var userDataDomain = UserDataDomain("", "", "", "", "", "", "", "")
    var balanceGetResponseDomain = BalanceGetResponseDomain(Double.NaN, "", true)

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = HomeViewModel(balanceUseCase, tokenHandler, userHandler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getUserBalance() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(balanceUseCase.getBalance("", "")).thenReturn(balanceGetResponseDomain)
        // Act
        val result = viewModel.getUserBalance()
        // Assert
        assertEquals(null, viewModel.userBalance.value)
    }

    @Test
    fun getUserData() = runTest {
        // Arrange
        Mockito.`when`(userHandler.loadUserName()).thenReturn("")
        Mockito.`when`(userHandler.loadUserEmail()).thenReturn("")
        Mockito.`when`(userHandler.loadUserPhoneNumber()).thenReturn("")
        Mockito.`when`(userHandler.loadAccountNumber()).thenReturn("")
        Mockito.`when`(userHandler.loadAccountPin()).thenReturn("")
        Mockito.`when`(userHandler.loadDateOfBirth()).thenReturn("")
        Mockito.`when`(userHandler.loadKtpNumber()).thenReturn("")
        Mockito.`when`(userHandler.loadKtpPhoto()).thenReturn("")
        // Act
        val result = viewModel.getUserData()
        // Assert
        assertEquals(userDataDomain, viewModel.userData.value)
    }
}