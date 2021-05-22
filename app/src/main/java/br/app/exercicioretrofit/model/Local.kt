package br.app.exercicioretrofit.model

data class Local(
    var cep: String,
    var logradouro: String,
    var complemento: String,
    var bairro: String,
    var localidade: String,
    var uf: String,
    var ibge: Int,
    var gia: Int,
    var ddd: Int,
    var siafi: Int

)
