package com.izan.aotapp.data.network

import com.izan.aotapp.data.model.CharacterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface AOTApiService {
    @GET("characters")
    suspend fun getCharacters(): Response<CharacterResponse>
}

