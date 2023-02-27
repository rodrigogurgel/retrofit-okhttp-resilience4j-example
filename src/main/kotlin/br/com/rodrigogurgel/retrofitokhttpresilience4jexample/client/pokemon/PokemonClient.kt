package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.client.pokemon

import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.pokemon.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonClient {

    @GET("pokemon/{id}")
    fun getPokemonById(@Path("id") id: String): Call<Pokemon>
}