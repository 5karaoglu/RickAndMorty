package com.besirkaraoglu.rickandmorty.presentation.ui.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.besirkaraoglu.rickandmorty.data.Repository
import com.besirkaraoglu.rickandmorty.data.remote.WebService
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Character
import com.besirkaraoglu.rickandmorty.data.remote.model.episodes.Episode
import com.besirkaraoglu.rickandmorty.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel
@Inject constructor(
    private val repository: Repository
):ViewModel(){

    private val _character: MutableLiveData<DataState<Character>> = MutableLiveData()
    val character: LiveData<DataState<Character>> get() = _character

    private val _episodes: MutableLiveData<DataState<List<Episode>>> = MutableLiveData()
    val episodes: LiveData<DataState<List<Episode>>> get() = _episodes


    fun getCharacterById(id: Int) = viewModelScope.launch {
        repository.getCharacterById(id).collect {
            _character.value = it
        }
    }

    fun getAllEpisodes() = viewModelScope.launch {
        repository.getAllEpisodes().collect {
            _episodes.value = it
        }
    }
}