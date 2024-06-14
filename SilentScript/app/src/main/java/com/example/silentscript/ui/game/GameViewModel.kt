package com.example.silentscript.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silentscript.ui.library.data.response.GameResponse
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameViewModel : ViewModel() {

    private val _game = MutableLiveData<List<GameResponse>>()
    val game: LiveData<List<GameResponse>> = _game

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchGame() {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getGame().enqueue(object : Callback<List<GameResponse>> {
            override fun onResponse(call: Call<List<GameResponse>>, response: Response<List<GameResponse>>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _game.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<GameResponse>>, t: Throwable) {
                _isLoading.value = false
                // Handle failure
            }
        })
    }
}