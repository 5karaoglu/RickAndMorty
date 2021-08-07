package com.besirkaraoglu.rickandmorty.data.remote

import com.besirkaraoglu.rickandmorty.data.remote.model.characters.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val PAGE_QUERY = "page"
interface WebService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query(PAGE_QUERY) page: Int
    ):Response<CharactersResponse>
}