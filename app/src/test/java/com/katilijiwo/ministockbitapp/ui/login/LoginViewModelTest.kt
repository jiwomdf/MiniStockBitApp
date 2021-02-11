package com.katilijiwo.ministockbitapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.katilijiwo.ministockbitapp.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule: CoroutinesTestRule = CoroutinesTestRule()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun before(){
        viewModel = LoginViewModel()
    }

    @Test
    fun `login with incorrect input, validateLogin function return false`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

        val errMsgUsername = "Your username must be between 2 and 15 characters"
        val errMsgPassword = "Your username must be between 2 and 15 characters"

        // Username empty
        viewModel.setUserName("")
        viewModel.setUserPassword("test")
        assertSame(viewModel.validateLogin(), false)
        assertSame(viewModel.getErrorMessage(), errMsgUsername)

        // Password empty
        viewModel.setUserName("test")
        viewModel.setUserPassword("")
        assertSame(viewModel.validateLogin(), false)
        assertSame(viewModel.getErrorMessage(), errMsgPassword)

        // Username length less than two
        viewModel.setUserName("j")
        viewModel.setUserPassword("test")
        assertSame(viewModel.validateLogin(), false)
        assertSame(viewModel.getErrorMessage(), errMsgUsername)

        // Password length less than two
        viewModel.setUserName("test")
        viewModel.setUserPassword("j")
        assertSame(viewModel.validateLogin(), false)
        assertSame(viewModel.getErrorMessage(), errMsgPassword)

        // Username length more than 15
        viewModel.setUserName("qwertyuiopasdfgh")
        viewModel.setUserPassword("test")
        assertSame(viewModel.validateLogin(), false)
        assertSame(viewModel.getErrorMessage(), errMsgUsername)

        // Password length more than 15
        viewModel.setUserName("test")
        viewModel.setUserPassword("qwertyuiopasdfgh")
        assertSame(viewModel.validateLogin(), false)
        assertSame(viewModel.getErrorMessage(), errMsgPassword)
    }

    @Test
    fun `login with correct input, validateLogin function return false and navigate to WatchListFragment`()
    = coroutinesTestRule.testDispatcher.runBlockingTest {
        viewModel.setUserName("test")
        viewModel.setUserPassword("test")
        assertSame(viewModel.validateLogin(), true)
        assertSame(viewModel.getErrorMessage(), "")
    }

}