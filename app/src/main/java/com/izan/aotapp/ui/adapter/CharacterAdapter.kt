package com.izan.aotapp.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izan.aotapp.R
import com.izan.aotapp.data.model.Character
import com.izan.aotapp.ui.view.DetailActivity


class CharacterAdapter(private var characterList: MutableList<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewCharacter)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewAge: TextView = view.findViewById(R.id.textViewAge)
        val textViewTitanType: TextView = view.findViewById(R.id.textViewTitanType)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characterList[position]

        Log.d("AOT_ADAPTER", "Mostrando personaje: ${character.name}, Imagen: ${character.image}")

        holder.textViewName.text = "Nombre: ${character.name}"
        holder.textViewAge.text = "Edad: ${character.age ?: "Desconocida"}"
        holder.textViewTitanType.text = "Tipo Titán: ${character.titanType?.joinToString(", ") ?: "No aplica"}"

        // Cargar imagen con Glide
        Glide.with(holder.itemView.context)
            .load(character.image) // Usa la URL de la imagen
            .placeholder(R.drawable.placeholder) // Imagen temporal mientras carga
            .error(R.drawable.error_image) // Imagen si hay error
            .into(holder.imageView) // Asigna al ImageView

        // Manejar clic en el personaje
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java).apply {
                putExtra("name", character.name)
                putExtra("age", character.age)
                putExtra("image", character.image)
                putExtra("titanType", character.titanType?.joinToString(", "))
                putExtra("height", character.height)
                putExtra("alias", character.alias?.joinToString(", "))
                putExtra("roles", character.roles?.joinToString(", "))
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = characterList.size

    fun updateData(newList: List<Character>) {
        characterList = newList.toMutableList()
        notifyDataSetChanged()
    }
}

