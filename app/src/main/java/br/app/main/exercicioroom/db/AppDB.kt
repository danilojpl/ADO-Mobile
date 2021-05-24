package br.app.main.exercicioroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.app.main.exercicioroom.models.Note

@Database(entities = [Note::class], version = 2)
abstract class AppDB: RoomDatabase() {
    abstract fun noteDAO(): NoteDAO
}