package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.service.impl

import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.client.pokemon.PokemonClient
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.pokemon.Pokemon
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.web.HttpErrorException
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.service.PokemonService
import org.springframework.stereotype.Service

@Service
class PokemonServiceImpl(
    private val pokemonClient: PokemonClient
) : PokemonService {

    override fun getPokemonById(id: String): Pokemon {
        val response = pokemonClient.getPokemonById(id).execute()
        if(response.isSuccessful) {
            return response.body()!!
        } else throw HttpErrorException.decode(response)
    }
}