package br.app.main.exercicioroom.db

import androidx.room.*
import br.app.main.exercicioroom.models.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(note: Note)

    @Query("SELECT * from Note")
    fun getAll(): List<Note>

    @Delete
    fun remove(note: Note)
}