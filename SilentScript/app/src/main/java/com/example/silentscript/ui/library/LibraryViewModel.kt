package com.example.silentscript.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silentscript.ui.library.data.response.LibraryResponse
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryViewModel : ViewModel() {

    private val _library = MutableLiveData<List<LibraryResponse>>()
    val library: LiveData<List<LibraryResponse>> = _library

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchLibrary() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getLibrary().enqueue(object : Callback<List<LibraryResponse>> {
            override fun onResponse(call: Call<List<LibraryResponse>>, response: Response<List<LibraryResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _library.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<LibraryResponse>>, t: Throwable) {
                _isLoading.value = false
                // Handle failure
            }
        })
    }
}