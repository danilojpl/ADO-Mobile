package br.app.main.exercicioroom.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.app.main.R
import br.app.main.databinding.ActivityNewNoteBinding
import br.app.main.exercicioroom.db.AppDB
import br.app.main.exercicioroom.models.Note

class NewNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val sharedPrefs = getSharedPreferences(getString(R.string.shared_users), Context.MODE_PRIVATE)
            val user = sharedPrefs.getString(getString(R.string.shared_username), "") as String

            val note = Note(
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString(),
                user = user
            )

            Thread {
                saveNote(note)
                finish()
            }.start()
        }
    }

    fun saveNote (note: Note) {
        val db = Room.databaseBuilder(this, AppDB::class.java, getString(R.string.room_db)).build()
        val dao = db.noteDAO()
        dao.save(note)
    }
}