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
//
//        addItem("Titulo 1", "Descrição 1")
//        addItem("Titulo 2", "Descrição 2")
//        addItem("Titulo 3", "Descrição 3")
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

        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        val color = prefManager.getInt(getString(R.string.shared_color), R.color.noteDefaultColor)

        Thread {
            val db = Room.databaseBuilder(this, AppDB::class.java, getString(R.string.room_db)).build()
            val notes = db.noteDAO().getAll()

            runOnUiThread {
                binding.container.removeAllViews()
    
                notes.forEach {
                    val nota = NotaBinding.inflate(layoutInflater)

                    nota.txtTitulo.text = it.title
                    nota.txtDesc.text = it.desc
                    nota.txtAutor.text = it.user
                    nota.container.setBackgroundColor(color)

                    binding.container.addView(nota.root)
                }
            }
        }.start()
    }
//
//    fun addItem(textoDoTitulo: String, textoDoConteudo: String) {
//        val nota = NotaBinding.inflate(layoutInflater)
//
//        nota.txtTitulo.text = textoDoTitulo
//        nota.txtDesc.text = textoDoConteudo
//        binding.container.addView(nota.root)
//    }
}