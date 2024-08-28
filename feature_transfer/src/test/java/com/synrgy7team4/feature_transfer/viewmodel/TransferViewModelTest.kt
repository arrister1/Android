package com.synrgy7team4.feature_transfer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_transfer.model.request.AccountSaveRequest
import com.synrgy7team4.domain.feature_transfer.model.request.TransferRequest
import com.synrgy7team4.domain.feature_transfer.model.response.AccountSaveResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.AccountsResponseItemDomain
import com.synrgy7team4.domain.feature_transfer.model.response.BalanceGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.MutationGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountGetDataDomain
import com.synrgy7team4.domain.feature_transfer.model.response.SavedAccountsGetResponseDomain
import com.synrgy7team4.domain.feature_transfer.model.response.TransferResponseDomain
import com.synrgy7team4.domain.feature_transfer.usecase.TransferUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class TransferViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TransferViewModel
    @Mock private lateinit var transferUseCase: TransferUseCase
    @Mock private lateinit var tokenHandler: TokenHandler
    @Mock private lateinit var userHandler: UserHandler

    lateinit var jwtToken: String
    lateinit var accountNumber: String
    lateinit var accountPin: String
    lateinit var userName: String

    private var transferRequest : TransferRequest = TransferRequest("","",1,"","", "", "")
    private var accountSaveRequest : AccountSaveRequest = AccountSaveRequest("")

    private var transferResponse : TransferResponseDomain = TransferResponseDomain()
    private var balanceGetResponse : BalanceGetResponseDomain = BalanceGetResponseDomain(0.0,"",true)
    private var savedAccountsGetResponse : SavedAccountsGetResponseDomain = SavedAccountsGetResponseDomain(listOf(SavedAccountGetDataDomain()), true, "")
    private var accountSaveResponse : AccountSaveResponseDomain = AccountSaveResponseDomain(true)
    private var mutationGetResponse : MutationGetResponseDomain = MutationGetResponseDomain(null,true,"")
    private var checkAccountResponse : List<AccountsResponseItemDomain> = listOf(
        AccountsResponseItemDomain()
    )

    @Before
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.initMocks(this)
        viewModel = TransferViewModel(transferUseCase, tokenHandler, userHandler)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initializeData() = runTest {
        // Given
        Mockito.`when`(tokenHandler.isTokenExpired()).thenReturn(false)
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("valid-jwt-token")
        Mockito.`when`(userHandler.loadAccountNumber()).thenReturn("account-number")
        Mockito.`when`(userHandler.loadAccountPin()).thenReturn("account-pin")
        Mockito.`when`(userHandler.loadUserName()).thenReturn("user-name")

        // When
        awaitAll(viewModel.initializeData())

        // Then
        assertEquals("valid-jwt-token", viewModel.jwtToken)
        assertEquals("account-number", viewModel.accountNumber)
        assertEquals("account-pin", viewModel.accountPin)
        assertEquals("user-name", viewModel.userName)
    }


    @Test
    fun transfer() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(transferUseCase.transfer("", transferRequest)).thenReturn(transferResponse)
        // Act
        viewModel.transfer("", "", 0, "", "", "")
        // Assert
        assertEquals(viewModel.transferResult.value, transferResponse)
    }

    @Test
    fun getSavedAccounts() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(transferUseCase.getSavedAccounts("")).thenReturn(savedAccountsGetResponse)
        // Act
        viewModel.getSavedAccounts()
        // Assert
        assertEquals(viewModel.savedAccountsData.value, savedAccountsGetResponse)
    }

    @Test
    fun getBalance() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(transferUseCase.getBalance("", "")).thenReturn(balanceGetResponse)
        // Act
        viewModel.getBalance()
        // Assert
        assertEquals(null, viewModel.balanceData.value)
    }

    @Test
    fun saveAccount() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(transferUseCase.saveAccount("", accountSaveRequest)).thenReturn(accountSaveResponse)
        // Act
        viewModel.saveAccount("")
        // Assert
        assertEquals(viewModel.accountSaveResponse.value, accountSaveResponse)
    }

    @Test
    fun getMutation() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(transferUseCase.getMutation("", "")).thenReturn(mutationGetResponse)
        // Act
        viewModel.getMutation("")
        // Assert
        assertEquals(viewModel.mutationData.value, mutationGetResponse.data)
    }

    @Test
    fun cekAccountList() = runTest {
        // Arrange
        Mockito.`when`(tokenHandler.loadJwtToken()).thenReturn("")
        Mockito.`when`(transferUseCase.checkAccount("")).thenReturn(checkAccountResponse)
        // Act
        viewModel.cekAccountList("")
        // Assert
        assertEquals(viewModel.accountAllList.value, checkAccountResponse)
    }
}