package com.besirkaraoglu.rickandmorty.data.remote.model.characters

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharactersResponse(
    @Expose
    @SerializedName("info") var info : Info,
    @Expose
    @SerializedName("results") var results : List<Character>
):Parcelable