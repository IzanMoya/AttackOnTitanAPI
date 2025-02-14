package com.izan.aotapp.ui.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.izan.aotapp.R
import com.izan.aotapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener los datos del Intent
        val name = intent.getStringExtra("name") ?: "Desconocido"
        val age = intent.getStringExtra("age") ?: "Desconocida"
        val image = intent.getStringExtra("image") ?: ""
        val titanType = intent.getStringExtra("titanType") ?: "No aplica"
        val height = intent.getStringExtra("height") ?: "Desconocida"
        val alias = intent.getStringExtra("alias") ?: "No tiene"
        val roles = intent.getStringExtra("roles") ?: "No aplica"

        // Asignar los datos a la UI
        binding.textViewName.text = name
        binding.textViewAge.text = "Edad: $age"
        binding.textViewTitanType.text = "Tipo Titán: $titanType"
        binding.textViewHeight.text = "Altura: $height"
        binding.textViewAlias.text = "Alias: $alias"
        binding.textViewRoles.text = "Roles: $roles"

        // Cargar imagen con Glide
        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error_image)
            .into(binding.imageViewCharacter)
    }
}

