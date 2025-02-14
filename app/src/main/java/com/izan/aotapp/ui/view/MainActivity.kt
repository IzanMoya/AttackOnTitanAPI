package com.izan.aotapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.izan.aotapp.data.database.AOTDatabase
import com.izan.aotapp.databinding.ActivityMainBinding
import com.izan.aotapp.repository.CharacterRepository
import com.izan.aotapp.ui.adapter.CharacterAdapter
import com.izan.aotapp.ui.viewModel.CharacterViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CharacterAdapter
    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { CharacterRepository(AOTDatabase.getDatabase(this)) }
    private val viewModel: CharacterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CharacterViewModel(repository) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CharacterAdapter(mutableListOf())
        binding.recyclerViewCharacters.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCharacters.adapter = adapter

        // Configurar opciones del Spinner
        val filterOptions = arrayOf("Todos", "Human", "Intelligent Titan")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilter.adapter = spinnerAdapter

        // Manejar cambios en el Spinner
        binding.spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedFilter = filterOptions[position]
                viewModel.filtrarPersonajes(selectedFilter)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 🔥 **AGREGAR BÚSQUEDA EN EL EDITTEXT**
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.buscarPersonaje(s.toString()) // Llama a la función para filtrar por nombre
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Observar cambios en la lista de personajes
        viewModel.characters.observe(this) { characters ->
            adapter.updateData(characters)
        }

        // Observar estado de carga
        viewModel.isLoading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // 🔥 **Agregar el listener del botón para recargar datos**
        binding.btnReload.setOnClickListener {
            viewModel.obtenerPersonajes()
        }

        // Obtener personajes al iniciar la app
        viewModel.obtenerPersonajes()
    }
}
