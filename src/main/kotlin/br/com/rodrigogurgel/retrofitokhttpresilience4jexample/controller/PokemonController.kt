package br.com.rodrigogurgel.retrofitokhttpresilience4jexample.controller

import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.model.pokemon.Pokemon
import br.com.rodrigogurgel.retrofitokhttpresilience4jexample.service.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/pokemon")
class PokemonController(
    private val pokemonService: PokemonService
) {
    @GetMapping("{id}")
    fun pokemon(@PathVariable("id") id: String): Pokemon {
        return pokemonService.getPokemonById(id)
    }
}