package br.app.main.exercicioroom.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var title: String,

    var desc: String,

    @ColumnInfo(defaultValue = "Desconhecido")
    var user: String
)
