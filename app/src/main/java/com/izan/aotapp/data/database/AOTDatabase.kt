package com.izan.aotapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CharacterEntity::class], version = 3, exportSchema = false)
abstract class AOTDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: AOTDatabase? = null

        // 🔹 Agregar la migración de versión 1 a 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Asegúrate de que character_entity es el nombre real de la tabla
                database.execSQL("ALTER TABLE character_entity ADD COLUMN alias TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): AOTDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AOTDatabase::class.java,
                    "aot_database"
                )
                    .addMigrations(MIGRATION_1_2) // 🔹 Si necesitas migrar datos
                    //.fallbackToDestructiveMigration() // 🔹 Si NO necesitas migración y quieres recrear la BD
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

