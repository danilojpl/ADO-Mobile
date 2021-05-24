package br.app.main.exercicioroom.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.app.main.R
import br.app.main.databinding.ActivityListNotesBinding
import br.app.main.databinding.NotaBinding

class ListNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityListNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            startActivity(Intent(this, NewNoteActivity::class.java))
        }

        addItem("Titulo 1", "Descrição 1")
        addItem("Titulo 2", "Descrição 2")
        addItem("Titulo 3", "Descrição 3")
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

    fun addItem(textoDoTitulo: String, textoDoConteudo: String) {
        val nota = NotaBinding.inflate(layoutInflater)

        nota.txtTitulo.text = textoDoTitulo
        nota.txtDesc.text = textoDoConteudo
        binding.container.addView(nota.root)
    }
}