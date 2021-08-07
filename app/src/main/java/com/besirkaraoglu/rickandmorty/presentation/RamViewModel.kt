package com.besirkaraoglu.rickandmorty.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.besirkaraoglu.rickandmorty.data.remote.CharactersPagingSource
import com.besirkaraoglu.rickandmorty.data.remote.WebService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RamViewModel
@Inject constructor(
    private val webService: WebService
): ViewModel(){


    val characters = Pager(
        PagingConfig(pageSize = 20,prefetchDistance = 3)
    ) {
        CharactersPagingSource(webService)
    }.flow
        .cachedIn(viewModelScope)
}