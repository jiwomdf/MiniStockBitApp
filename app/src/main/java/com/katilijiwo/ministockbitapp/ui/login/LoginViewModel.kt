package com.katilijiwo.ministockbitapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katilijiwo.ministockbitapp.data.Repository
import java.util.regex.Pattern

class LoginViewModel: ViewModel() {

    private var errorMessage = ""
    fun getErrorMessage() : String {
        return errorMessage
    }

    private val userName : MutableLiveData<String> = MutableLiveData("")
    fun setUserName(userName : String){
        this.userName.value = userName
    }
    fun getUserName() : MutableLiveData<String> {
        return userName
    }

    private val userPassword : MutableLiveData<String> = MutableLiveData("")
    fun setUserPassword(userPassword : String){
        this.userPassword.value = userPassword
    }
    fun getUserPassword() : MutableLiveData<String> {
        return userPassword
    }

    fun validateLogin(): Boolean {
        if(userName.value!!.length < 2 || userPassword.value!!.length > 15){
            errorMessage = "Your username must be between 2 and 15 characters"
            return false
        }
        if(userPassword.value!!.length < 2 || userName.value!!.length > 15 ){
            errorMessage = "Your username must be between 2 and 15 characters"
            return false
        }
        return true
    }


}