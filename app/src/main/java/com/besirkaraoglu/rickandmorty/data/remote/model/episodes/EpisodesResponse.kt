package com.besirkaraoglu.rickandmorty.data.remote.model.episodes


import android.os.Parcelable
import com.besirkaraoglu.rickandmorty.data.remote.model.characters.Info
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodesResponse(
    @Expose
    @SerializedName("info") var info : Info,
    @Expose
    @SerializedName("results") var results : List<Episode>
):Parcelable