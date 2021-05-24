package br.app.main.exercicioroom.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.room.Room
import br.app.main.R
import br.app.main.databinding.ActivityListNotesBinding
import br.app.main.databinding.NotaBinding
import br.app.main.exercicioroom.db.AppDB
import br.app.main.exercicioroom.models.Note

class ListNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityListNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            startActivity(Intent(this, NewNoteActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user -> {
                startActivity(Intent(this, UserActivity::class.java))
            }

            R.id.config -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        refreshNotes()
    }

    fun refreshNotes () {
        Thread {
            val db = Room.databaseBuilder(this, AppDB::class.java, getString(R.string.room_db)).build()
            val notes = db.noteDAO().getAll()

            runOnUiThread {
                updateUI(notes)
            }
        }.start()
    }

    fun removeNote (note: Note) {
        Thread {
            val db = Room.databaseBuilder(this, AppDB::class.java, getString(R.string.room_db)).build()
            db.noteDAO().remove(note)

            refreshNotes()
        }.start()
    }

    fun updateNote (note: Note) {
        val intent = Intent(this, NewNoteActivity::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    fun updateUI (notes: List<Note>) {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)

        val color = prefManager.getInt(getString(R.string.shared_color), R.color.noteDefaultColor)
        val fontColor = prefManager.getInt(getString(R.string.font_color), R.color.noteTextDefaultColor)

        val titleSize = prefManager.getString(getString(R.string.title_size), getString(R.string.title_default_size)) as String
        val descSize = prefManager.getString(getString(R.string.desc_size), getString(R.string.desc_default_size)) as String

        binding.container.removeAllViews()

        notes.forEach {
            val nota = NotaBinding.inflate(layoutInflater)
            val note = it

            nota.txtTitulo.text = note.title
            nota.txtTitulo.textSize = titleSize.toFloat()
            nota.txtTitulo.setTextColor(fontColor)

            nota.txtDesc.text = note.desc
            nota.txtDesc.textSize = descSize.toFloat()
            nota.txtDesc.setTextColor(fontColor)

            nota.txtAutor.text = note.user
            nota.txtAutor.setTextColor(fontColor)

            nota.container.setBackgroundColor(color)

            nota.remove.setOnClickListener {
                removeNote(note)
            }

            nota.container.setOnClickListener {
                updateNote(note)
            }

            binding.container.addView(nota.root)
        }
    }
}