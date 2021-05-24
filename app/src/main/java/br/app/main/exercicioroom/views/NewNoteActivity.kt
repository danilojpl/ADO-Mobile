package br.app.main.exercicioroom.views

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.room.Room
import br.app.main.R
import br.app.main.databinding.ActivityNewNoteBinding
import br.app.main.exercicioroom.db.AppDB
import br.app.main.exercicioroom.models.Note
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import kotlin.properties.Delegates

class NewNoteActivity : AppCompatActivity(), ColorPickerDialogListener {
    lateinit var binding: ActivityNewNoteBinding
    var color by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        this.color = prefManager.getInt(getString(R.string.shared_color), R.color.noteDefaultColor)

        val note = intent.getSerializableExtra("note") as? Note

        note?.let {
            binding.etTitle.setText(note.title)
            binding.etDesc.setText(note.desc)
            binding.btnAdd.text = "Atualizar"
            color = note.color
        }

        binding.selectedColor.setCardBackgroundColor(color)
        binding.colorPicker.setOnClickListener {
            ColorPickerDialog
                .newBuilder()
                .setColor(color)
                .show(this)
        }

        binding.btnAdd.setOnClickListener {
            val sharedPrefs = getSharedPreferences(getString(R.string.shared_users), Context.MODE_PRIVATE)
            val user = sharedPrefs.getString(getString(R.string.shared_username), "") as String

            val note = Note(
                id = note?.id,
                title = binding.etTitle.text.toString(),
                desc = binding.etDesc.text.toString(),
                user = user,
                color = this.color
            )

            Thread {
                saveNote(note)
                finish()
            }.start()
        }
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        this.color = color
        binding.selectedColor.setCardBackgroundColor(color)
    }

    override fun onDialogDismissed(dialogId: Int) {
        //
    }

    fun saveNote (note: Note) {
        val db = Room.databaseBuilder(this, AppDB::class.java, getString(R.string.room_db)).build()
        val dao = db.noteDAO()
        dao.save(note)
    }
}