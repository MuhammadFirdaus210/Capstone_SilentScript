package com.example.silentscript.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import com.example.silentscript.ui.library.data.response.UserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    fun getUserDetails(token: String, uid: String) {
        viewModelScope.launch {
            ApiConfig.getApiService().getUserDetails(token, uid).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        _userResponse.value = response.body()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }
}