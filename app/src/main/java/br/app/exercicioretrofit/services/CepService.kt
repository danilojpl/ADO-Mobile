package br.app.exercicioretrofit.services

import br.app.exercicioretrofit.model.Local
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {
    @GET("/ws/{cep}/json/")
    fun get(@Path("cep") id: String): Call<List<Local>>
}