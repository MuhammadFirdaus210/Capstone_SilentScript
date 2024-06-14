package com.example.silentscript.ui.library.data.retrofit

import com.example.silentscript.ui.library.data.response.GameResponse
import com.example.silentscript.ui.library.data.response.HurufResponse
import com.example.silentscript.ui.library.data.response.LibraryResponse
import com.example.silentscript.ui.library.data.response.KataResponse
import com.example.silentscript.ui.library.data.response.StatusResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("libraries/kategori/")
    fun getLibrary(): Call<List<LibraryResponse>>

    @GET("libraries/{slug}")
    fun getLibrarySalam(@Path("slug") slug: String): Call<List<KataResponse>>

    @GET("libraries/{slug}")
    fun getHuruf(@Path("slug") slug: String): Call<List<HurufResponse>>

    @GET("levels")
    fun getGame(): Call<List<GameResponse>>

    @Multipart
    @POST("levels/{levelsId}/prediksi")
    fun postGame(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Path("levelsId") levelsId: String
    ): Call<StatusResponse>
}