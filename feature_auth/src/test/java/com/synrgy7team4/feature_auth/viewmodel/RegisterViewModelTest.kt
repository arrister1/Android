package com.synrgy7team4.feature_auth.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.response.EmailCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.KtpNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.PhoneNumberCheckResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterDataDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
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
class RegisterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModel
    @Mock
    private lateinit var registerUseCase: RegisterUseCase

    var registerRequest = RegisterRequest("", "", "", "", "", "", "", "", "", true)
    var registerDataDomain = RegisterDataDomain("", "", "", "", "", "", "", "")
    var registerResponseDomain = RegisterResponseDomain(registerDataDomain, true, "")

    var  emailCheckRequest = EmailCheckRequest("")
    var emailCheckResponseDomain = EmailCheckResponseDomain("", true)

    var  phoneNumberCheckRequest = PhoneNumberCheckRequest("")
    var phoneNumberCheckResponseDomain = PhoneNumberCheckResponseDomain("", true)

    var ktpNumberCheckRequest = KtpNumberCheckRequest("")
    var ktpNumberCheckResponseDomain = KtpNumberCheckResponseDomain("", true)

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = RegisterViewModel(registerUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun registerSuccess() = runTest {
        // Arrange
        Mockito.`when`(registerUseCase.register(registerRequest)).thenReturn(registerResponseDomain)
        // Assert
        assertDoesNotThrow { viewModel.register("", "", "", "", "", "", "", "", "", true) }
    }

    @Test
    fun checkEmailAvailability() = runTest {
        // Arrange
        Mockito.`when`(registerUseCase.checkEmailAvailability(emailCheckRequest)).thenReturn(emailCheckResponseDomain)
        // Act
        viewModel.checkEmailAvailability("")
        // Assert
        assertEquals(true, viewModel.isEmailAvailable.value)
    }

    @Test
    fun checkPhoneNumberAvailability() = runTest {
        // Arrange
        Mockito.`when`(registerUseCase.checkPhoneNumberAvailability(phoneNumberCheckRequest)).thenReturn(phoneNumberCheckResponseDomain)
        // Act
        viewModel.checkPhoneNumberAvailability("")
        // Assert
        assertEquals(true, viewModel.isPhoneNumberAvailable.value)
    }

    @Test
    fun checkKtpNumberAvailability() = runTest {
        // Arrange
        Mockito.`when`(registerUseCase.checkKtpNumberAvailability(ktpNumberCheckRequest)).thenReturn(ktpNumberCheckResponseDomain)
        // Act
        viewModel.checkKtpNumberAvailability("")
        // Assert
        assertEquals(true, viewModel.isKtpNumberAvailable.value)
    }
}