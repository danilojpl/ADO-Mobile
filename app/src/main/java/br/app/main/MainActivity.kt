package br.app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.app.main.databinding.ActivityMainBinding
import br.app.main.exercicioretrofit.view.RetrofitActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRetrofit.setOnClickListener {
            this.startActivity(Intent(this, RetrofitActivity::class.java))
        }
    }
}