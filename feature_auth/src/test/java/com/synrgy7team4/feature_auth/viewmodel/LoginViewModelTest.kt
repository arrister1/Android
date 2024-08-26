package com.synrgy7team4.feature_auth.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.response.LoginDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.LoginResponseDomain
import com.synrgy7team4.domain.feature_auth.usecase.LoginUseCase
import com.synrgy7team4.domain.feature_auth.usecase.UserUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    @Mock private lateinit var loginUseCase: LoginUseCase
    @Mock private lateinit var userUseCase: UserUseCase
    @Mock private lateinit var tokenHandler: TokenHandler
    @Mock private lateinit var userHandler: UserHandler

    var loginRequest = LoginRequest("", "")
    var loginDataDomain = LoginDataDomain("", "", "", "")
    var loginResponseDomain = LoginResponseDomain(loginDataDomain, true, "")

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = LoginViewModel(loginUseCase, userUseCase, tokenHandler, userHandler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun login() = runTest {
        // Arrange
        Mockito.`when`(loginUseCase.login(loginRequest)).thenReturn(loginResponseDomain)
        // Act
        viewModel.login("", "")
        // Assert
        assertEquals(true, viewModel.isSuccessful.value)
    }

    @Test
    fun saveTokens() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.saveTokens("", "")).thenReturn(Unit)
        // Assert
        assertDoesNotThrow { viewModel.saveTokens() }
    }

    @Test
    fun saveUserData() = runTest {
        assertDoesNotThrow { viewModel.saveUserData() }
    }
}