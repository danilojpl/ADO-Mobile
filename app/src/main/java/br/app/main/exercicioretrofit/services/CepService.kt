package br.app.main.exercicioretrofit.services

import br.app.main.exercicioretrofit.model.Local
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {
    @GET("{CEP}/json/")
    fun get(@Path("CEP") id: String): Call<Local>
}