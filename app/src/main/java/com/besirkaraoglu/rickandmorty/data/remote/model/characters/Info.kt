package com.besirkaraoglu.rickandmorty.data.remote.model.characters


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @SerializedName("count") val count : Int,
    @SerializedName("pages") val pages : Int,
    @SerializedName("next") val next : String?,
    @SerializedName("prev") val prev : String?
):Parcelable