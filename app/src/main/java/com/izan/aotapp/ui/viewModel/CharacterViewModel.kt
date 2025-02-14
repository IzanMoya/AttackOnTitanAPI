package com.izan.aotapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.izan.aotapp.data.model.CharacterResponse
import com.izan.aotapp.repository.CharacterRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterViewModel(private val repository: CharacterRepositoryInterface) : ViewModel() {

    private val _characters = MutableLiveData<List<com.izan.aotapp.data.model.Character>>()
    val characters: LiveData<List<com.izan.aotapp.data.model.Character>> get() = _characters


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading



    private var allCharacters: List<com.izan.aotapp.data.model.Character> = listOf()

    init {
        obtenerPersonajes()
    }


    fun obtenerPersonajes() {
        viewModelScope.launch(Dispatchers.IO) {
            val personajes = repository.getCharacters()
            allCharacters = personajes
            withContext(Dispatchers.Main) {
                _characters.value = personajes
            }
        }
    }

    // 🔹 NUEVA FUNCIÓN PARA FILTRAR POR NOMBRE
    fun buscarPersonaje(nombre: String) {
        val filteredList = if (nombre.isEmpty()) {
            allCharacters // Si el campo está vacío, mostrar todos
        } else {
            allCharacters.filter { it.name.contains(nombre, ignoreCase = true) }
        }

        Log.d("AOT_SEARCH", "Personajes encontrados para '$nombre': ${filteredList.size}")
        _characters.value = filteredList
    }


    // FUNCIÓN PARA FILTRAR PERSONAJES SEGÚN EL SPINNER
    fun filtrarPersonajes(filter: String) {
        val filteredList = when (filter) {
            "Human" -> allCharacters.filter { it.titanType?.contains("Human") == true }
            "Intelligent Titan" -> allCharacters.filter { it.titanType?.contains("Intelligent Titan") == true }
            else -> allCharacters
        }

        Log.d("AOT_FILTER", "Personajes después de filtrar ($filter): ${filteredList.size}")
        _characters.value = filteredList
    }
}

