package com.besirkaraoglu.rickandmorty.presentation.ui.main

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.besirkaraoglu.rickandmorty.data.CharactersPagingSource
import com.besirkaraoglu.rickandmorty.data.remote.WebService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
    private val webService: WebService
): ViewModel(){

    val characters =  Pager(
        PagingConfig(pageSize = 20,prefetchDistance = 3)
    ) {
        CharactersPagingSource(webService)
    }.flow.cachedIn(viewModelScope)

}