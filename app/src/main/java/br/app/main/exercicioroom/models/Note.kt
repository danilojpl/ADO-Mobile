package br.app.main.exercicioroom.models

import android.app.Activity
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var title: String,

    var desc: String,

    @ColumnInfo(defaultValue = "Desconhecido")
    var user: String,

    var color: Int
) : Serializable
