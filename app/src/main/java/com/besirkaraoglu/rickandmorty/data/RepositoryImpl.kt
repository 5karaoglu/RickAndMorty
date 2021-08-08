package com.besirkaraoglu.rickandmorty.data

import android.util.Log
import com.besirkaraoglu.rickandmorty.data.remote.WebService
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.data.remote.model.episodes.Episode
import com.besirkaraoglu.rickandmorty.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class RepositoryImpl
    @Inject constructor(
        private val webService: WebService
    ) : Repository {


    override suspend fun getCharacterById(id: Int): Flow<DataState<Character>> = channelFlow {
        send(DataState.Loading)
        try {
           val response = webService.getCharacterById(id)
           if (response.isSuccessful){
               send(DataState.Success(response.body()!!))
           }
       }catch (e:Exception){
           send(DataState.Failure(e))
       }
    }


    override suspend fun getAllEpisodes(): Flow<DataState<List<Episode>>> = channelFlow {
        send(DataState.Loading)
        try {
            var currentPage = 1
            val list: MutableList<Episode> = mutableListOf()
            val response = webService.getEpisodes(currentPage)
            if (response.isSuccessful){
                list.addAll(response.body()!!.results)
                while (list.size < response.body()!!.info.count){
                    currentPage++
                    val res = webService.getEpisodes(currentPage)
                    list.addAll(res.body()!!.results)
                }
                send(DataState.Success(list))
            }
        }catch (e:Exception){
            send(DataState.Failure(e))
        }
    }
}