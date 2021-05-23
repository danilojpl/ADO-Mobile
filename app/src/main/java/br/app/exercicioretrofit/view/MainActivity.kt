package br.app.exercicioretrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.app.exercicioretrofit.databinding.ActivityMainBinding
import br.app.exercicioretrofit.model.Local
import br.app.exercicioretrofit.services.CepService
import com.google.android.material.snackbar.Snackbar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBuscar.setOnClickListener {
            atualizarEndereco()
        }
    }

    fun atualizarEndereco() {
        val http = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .client (http)
                .build()

        val service = retrofit.create(CepService::class.java)

        val call = service.get(binding.editTextCep.text.toString())

        val callback = object  : Callback<Local>{
            override fun onResponse(call: Call<Local>, response: Response<Local>) {
                if (response.isSuccessful) {
                    val local = response.body()
                    atualizarTela(local)
                } else {
                    Snackbar.make(
                            binding.root,
                            "Não foi possível localizar o cep",
                            Snackbar.LENGTH_LONG
                    ).show()
                    Log.e("ERRO-Retrofit", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<Local>, t: Throwable) {
                Snackbar.make(
                        binding.root,
                        "Não foi possível conectar-se ao servidor",
                        Snackbar.LENGTH_LONG
                ).show()

                Log.e("ERRO-Retrofit", "Falha de conexão", t )
            }

        }

        call.enqueue(callback)

    }

    fun atualizarTela (endereco: Local?){
        val msg = """Logradouro: ${endereco?.logradouro}
                |Bairro: ${endereco?.bairro}
                |Complemento: ${endereco?.complemento}
                |Cidade: ${endereco?.localidade}
                |Estado: ${endereco?.uf}
                |CEP: ${endereco?.cep}
                |ddd: ${endereco?.ddd}
                |gia: ${endereco?.gia}
                |ibge: ${endereco?.ibge}
                |siafi: ${endereco?.siafi}
                 """.trimMargin()

        binding.txtMsg.text = msg
    }

}