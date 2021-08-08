package com.besirkaraoglu.rickandmorty.data


import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.data.remote.model.episodes.Episode
import com.besirkaraoglu.rickandmorty.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCharacterById(
        id: Int
    ):Flow<DataState<Character>>

    suspend fun getAllEpisodes(
    ):Flow<DataState<List<Episode>>>
}