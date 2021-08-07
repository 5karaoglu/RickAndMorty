package com.besirkaraoglu.rickandmorty.data.remote.model.characters


data class CharactersResponse(
    val info: Info,
    val characters: List<Character>
)