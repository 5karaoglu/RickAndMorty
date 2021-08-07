package com.besirkaraoglu.rickandmorty.data.remote

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.CharactersResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersPagingSource
    @Inject constructor(
        private val webService: WebService
    ): PagingSource<Int,Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val currentPage = params.key ?: 1
        return try {
            val response = webService.getAllCharacters(currentPage)
            Log.d("TAG", "load: ${response.body()}")
            Log.d("TAG", "load: ${response.raw()}")

            val body = response.body()
            val info = body?.info
            val characters = body?.results
            Log.d("TAG", "load: $body")

            var nextPage : Int? = null
            if (info?.next != null){
                val uri = Uri.parse(info.next)
                val query = uri.getQueryParameter("page")
                nextPage = query?.toInt()!!
            }

            LoadResult.Page(
                data = characters.orEmpty(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextPage
            )
        }catch (e:IOException){
            LoadResult.Error(e)
        }catch (e:HttpException){
            LoadResult.Error(e)
        }
    }
}