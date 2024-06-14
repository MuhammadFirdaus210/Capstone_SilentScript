package com.example.silentscript.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silentscript.ui.library.data.response.KataResponse
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KataViewModel : ViewModel() {

    private val _library = MutableLiveData<List<KataResponse>>()
    val library: LiveData<List<KataResponse>> = _library

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchLibrary(slug: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getLibrarySalam(slug).enqueue(object : Callback<List<KataResponse>> {
            override fun onResponse(call: Call<List<KataResponse>>, response: Response<List<KataResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _library.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<KataResponse>>, t: Throwable) {
                _isLoading.value = false
                // Handle failure
            }
        })
    }
}