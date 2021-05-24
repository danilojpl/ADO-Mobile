package br.app.main.exercicioroom.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.app.main.exercicioroom.models.Note

@Dao
interface NoteDAO {
    @Insert
    fun save(note: Note)

    @Query("SELECT * from Note")
    fun getAll(): List<Note>
}