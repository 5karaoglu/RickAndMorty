package com.besirkaraoglu.rickandmorty.data.remote

import com.besirkaraoglu.rickandmorty.application.PAGE_QUERY
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.CharactersResponse
import com.besirkaraoglu.rickandmorty.data.remote.model.episodes.EpisodesResponse
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("character/")
    suspend fun getAllCharacters(
        @Query(PAGE_QUERY) page: Int
    ):Response<CharactersResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ):Response<Character>

    @GET("episode/")
    suspend fun getEpisodes(
        @Query(PAGE_QUERY) page: Int
    ):Response<EpisodesResponse>
}