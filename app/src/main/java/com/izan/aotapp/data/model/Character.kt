package com.izan.aotapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Character(
    val id: Int,
    val name: String,
    val age: String?,
    @SerializedName("img") val image: String?, // Asegúrate de que sea "img"
    @SerializedName("species") val titanType: List<String>?,
    val height: String?,
    val alias: List<String>?,
    val roles: List<String>?
)

