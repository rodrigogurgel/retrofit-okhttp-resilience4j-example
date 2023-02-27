package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.service

import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.pokemon.Pokemon

interface PokemonService {
    fun getPokemonById(id: String): Pokemon
}