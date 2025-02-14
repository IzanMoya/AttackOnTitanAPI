package com.izan.aotapp.repository

interface CharacterRepositoryInterface {
    suspend fun getCharacters(): List<com.izan.aotapp.data.model.Character> // ✅ Asegúrate de que sea este tipo
}
