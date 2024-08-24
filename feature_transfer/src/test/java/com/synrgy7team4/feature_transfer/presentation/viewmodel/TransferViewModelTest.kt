package com.synrgy7team4.feature_transfer.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.repository.TransferRepositoryImpl
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransSchedule
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountError
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.Data
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.Errors
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferData
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transschedule.TransSchedData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserErrors
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.domain.model.User
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class TransferViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: TransferViewModel
    @Mock private lateinit var repository: TransferRepositoryImpl

    private var transferRequest : TransferRequest = TransferRequest("","",1,"","")
    private var balanceRequest : BalanceRequest = BalanceRequest("",0)
    private var accountRequest : AccountRequest = AccountRequest("","","")
    private var transSchedule : TransSchedule = TransSchedule("","",0,"","","")

    private var userData : UserData = UserData("","","","","","","","")
    private var accountData : AccountData = AccountData("","","")
    private var transferData : TransferData = TransferData("","",0,0,"","","","","","","")
    private var transSchedData : TransSchedData = TransSchedData()
    private var data : Data = Data("","",0,0,"","","","","","","")


    private var errors : Errors = Errors("","",0,0,"","","","","","","")
    private var accountError : AccountError = AccountError("","","")
    private var userError : UserErrors = UserErrors("","","","","","","","")

    private var transferResponse : TransferResponse = TransferResponse(transferData, errors, "",true)
    private var balanceResponse : BalanceResponse = BalanceResponse(0,0,"",true)
    private var accountResponse : AccountResponse = AccountResponse(listOf(accountData), listOf(accountError), "", true)
    private var userResponse : UserResponse = UserResponse(userData,userError, "",true)

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
    fun postTransfer() = runTest {
        // Arrange
        Mockito.`when`(repository.postTransfer("",transferRequest)).thenReturn(transferResponse)
        // Act
        viewModel.postTransfer("", transferRequest)
        // Assert
        assertEquals(viewModel.transferResponse.value, transferResponse)
        }

    @Test
    fun getBalance() = runTest {
        // Arrange
        Mockito.`when`(repository.getBalance("","")).thenReturn(balanceResponse)
        // Act
        viewModel.getBalance("","")
        // Assert
        assertEquals(viewModel.balanceData.value, balanceResponse)
    }

    @Test
    fun postBalance() = runTest {
        // Arrange
        Mockito.`when`(repository.postBalance("",balanceRequest)).thenReturn(balanceResponse)
        // Act
        viewModel.postBalance("",balanceRequest)
        // Assert
        assertEquals(viewModel.balancePostResponse.value, balanceResponse)
    }

    @Test
    fun getAccountList() = runTest {
        // Arrange
        Mockito.`when`(repository.getAccountList("")).thenReturn(accountResponse)
        // Act
        viewModel.getAccountList("")
        // Assert
        assertEquals(viewModel.accountList.value, accountResponse)
    }

    @Test
    fun postAccount() = runTest {
        // Arrange
        Mockito.`when`(repository.postAccount("", accountRequest)).thenReturn(accountResponse)
        // Act
        viewModel.postAccount("", accountRequest)
        // Assert
        assertEquals(viewModel.accountSaveResponse.value, accountResponse)
    }

    @Test
    fun getUserData() = runTest {
        // Arrange
        Mockito.`when`(repository.getUserData("")).thenReturn(userResponse)
        // Act
        viewModel.getUserData("")
        // Assert
        assertEquals(viewModel.userData.value, userResponse)
    }

    @Test
    fun postTransferSched() = runTest {
        // Arrange
        Mockito.`when`(repository.postTransferSched("", transSchedule)).thenReturn(transSchedData)
        // Act
        viewModel.postTransferSched("", transSchedule)
        // Assert
        assertEquals(viewModel.scheduleResponse.value, transSchedData)
    }

    @Test
    fun getMutation() = runTest {
        // Arrange
        Mockito.`when`(repository.getMutation("", "")).thenReturn(data)
        // Act
        viewModel.getMutation("", "")
        // Assert
        assertEquals(viewModel.mutationData.value, data)
    }
}