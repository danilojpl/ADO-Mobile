package br.app.main.exercicioroom.views

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import br.app.main.R
import br.app.main.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPrefs = getSharedPreferences(getString(R.string.shared_users), Context.MODE_PRIVATE)
        val user = sharedPrefs.getString(getString(R.string.shared_username), "") as String
        binding.etUsername.setText(user)

        binding.btnSave.setOnClickListener {
            val editor = sharedPrefs.edit()

            editor.putString(getString(R.string.shared_username), binding.etUsername.text.toString())
            editor.commit()

            finish()
        }
    }
}