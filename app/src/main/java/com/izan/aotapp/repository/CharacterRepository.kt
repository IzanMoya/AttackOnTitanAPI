package com.izan.aotapp.repository

import android.util.Log
import com.izan.aotapp.data.database.AOTDatabase
import com.izan.aotapp.data.database.CharacterEntity
import com.izan.aotapp.data.network.RetrofitInstance
import com.izan.aotapp.data.model.Character
import java.net.HttpURLConnection
import java.net.URL


class CharacterRepository(private val database: AOTDatabase) : CharacterRepositoryInterface {

    override suspend fun getCharacters(): List<Character> {
        return try {
            val response = RetrofitInstance.api.getCharacters()

            if (response.isSuccessful) {
                val apiCharacters = response.body()?.results ?: emptyList() // ✅ Usamos `results`

                // ✅ Convertir `CharacterResponse` a `Character`
                val characterList = apiCharacters.map { character ->
                    Character(
                        id = character.id,
                        name = character.name,
                        age = character.age,
                        image = character.image ?: "",
                        titanType = character.titanType?.joinToString(", ")?.split(", "),
                        height = character.height,
                        alias = character.alias?.joinToString(", ")?.split(", "),
                        roles = character.roles?.joinToString(", ")?.split(", ")
                    )
                }

                val entities = characterList.map { character ->
                    CharacterEntity(
                        id = character.id,
                        name = character.name,
                        age = character.age,
                        imageUrl = character.image,
                        imageData = null, // Se almacenará más adelante
                        titanType = character.titanType?.joinToString(", "),
                        height = character.height,
                        alias = character.alias?.joinToString(", "),
                        roles = character.roles?.joinToString(", ")
                    )
                }

                database.characterDao().clearCharacters()
                database.characterDao().insertCharacters(entities)

                characterList
            } else {
                database.characterDao().getAllCharacters().map { it.toCharacter() }
            }
        } catch (e: Exception) {
            database.characterDao().getAllCharacters().map { it.toCharacter() }
        }
    }


}


private fun downloadImage(url: String?): ByteArray? {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            inputStream.readBytes() // Convertir imagen en ByteArray
        } catch (e: Exception) {
            null
        }
    }






// ✅ Convierte una entidad de Room a un objeto Character
    private fun CharacterEntity.toCharacter(): Character {
        return Character(
            id = id,
            name = name,
            age = age,
            image = imageUrl ?: "",
            titanType = titanType?.split(", "),
            height = height,
            alias = alias?.split(", "),
            roles = roles?.split(", ")
        )
    }


