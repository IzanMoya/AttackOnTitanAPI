package com.izan.aotapp.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    val results: List<Character> // ✅ Debe ser List<Character> en vez de List<CharacterResponse>
)

