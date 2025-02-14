package com.izan.aotapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val age: String?,
    val imageUrl: String?, // Guardamos la URL
    val imageData: ByteArray?, // Guardamos la imagen en bytes
    val titanType: String?,
    val height: String?,
    val alias: String?,
    val roles: String?
)
