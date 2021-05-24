package br.app.main.exercicioroom.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.app.main.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}