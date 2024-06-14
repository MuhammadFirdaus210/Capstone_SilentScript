package com.example.silentscript.ui.library.huruf

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silentscript.ui.library.data.response.HurufResponse
import com.example.silentscript.ui.library.data.response.KataResponse
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HurufViewModel : ViewModel() {

    private val _library = MutableLiveData<List<HurufResponse>>()
    val library: LiveData<List<HurufResponse>> = _library

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchLibrary(slug: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getHuruf(slug).enqueue(object : Callback<List<HurufResponse>> {
            override fun onResponse(call: Call<List<HurufResponse>>, response: Response<List<HurufResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val sortedResponse = response.body()?.sortedBy { it.penjelasan } ?: emptyList()
                    Log.d("HurufViewModel", "onResponse: $sortedResponse")
                    _library.postValue(sortedResponse)
                }
            }

            override fun onFailure(call: Call<List<HurufResponse>>, t: Throwable) {
                _isLoading.value = false
                // Handle failure
            }
        })
    }
}