package com.synrgy7team4.feature_dashboard.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.model.response.UserDataDomain
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
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
class AccountViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: AccountViewModel
    @Mock private lateinit var tokenHandler: TokenHandler
    @Mock private lateinit var userHandler: UserHandler

    var userDataDomain = UserDataDomain("", "", "", "", "", "", "", "")

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = AccountViewModel(tokenHandler, userHandler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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

    @Test
    fun deleteTokens() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.deleteTokens()).thenReturn(Unit)
        // Assert
        assertDoesNotThrow { viewModel.deleteTokens() }
    }

    @Test
    fun deleteUserData() = runTest {
        // Arrange
        Mockito.`when`(userHandler.deleteUserData()).thenReturn(Unit)
        // Assert
        assertDoesNotThrow { viewModel.deleteUserData() }
    }
}