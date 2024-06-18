package com.example.silentscript.ui.register

import androidx.lifecycle.ViewModel
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import com.example.silentscript.ui.library.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    fun registerUser(username: String, email: String, password: String, passwordConfirmation: String, onResult: (RegisterResponse?) -> Unit) {
        ApiConfig.getApiService().register(username, email, password, passwordConfirmation).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }
}