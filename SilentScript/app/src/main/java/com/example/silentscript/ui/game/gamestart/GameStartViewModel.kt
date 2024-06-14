import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silentscript.ui.library.data.response.StatusResponse
import com.example.silentscript.ui.library.data.retrofit.ApiConfig
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameStartViewModel(private val cameraAndFileHandler: CameraAndFileHandler) : ViewModel() {

    private val _uploadResponse = MutableLiveData<StatusResponse>()
    val uploadResponse: LiveData<StatusResponse> = _uploadResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun allPermissionsGranted() = cameraAndFileHandler.allPermissionsGranted()

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
        cameraAndFileHandler.handleActivityResult(requestCode, resultCode, data)

    fun postGame(
        token: String,
        image: MultipartBody.Part,
        levelsId: String
    ) {
        _isLoading.value = true // Show loading
        val apiService = ApiConfig.getApiService()
        val call = apiService.postGame(token,image,levelsId)
        call.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(call: Call<StatusResponse>, response: Response<StatusResponse>) {
                _isLoading.value = false // Hide loading
                if (response.isSuccessful) {
                    // Handle the successful response
                    _uploadResponse.value = response.body()
                    Log.d("GameStartViewModel", "Successful response: ${response.body()}")

                    // Show a success message
                    _message.postValue("Berhasil post")
                } else {
                    // Handle the unsuccessful response
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("GameStartViewModel", "Response is unsuccessful. Error: $errorMessage")
                    _message.postValue(errorMessage)
                }
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                _isLoading.value = false // Hide loading
                // Handle the failure
                val failureMessage = t.message ?: "Unknown error"
                Log.e("GameStartViewModel", "Request failed. Error: $failureMessage")
                _message.postValue(failureMessage)
            }
        })
    }
}
