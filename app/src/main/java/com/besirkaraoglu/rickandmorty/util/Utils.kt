package com.besirkaraoglu.rickandmorty.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.besirkaraoglu.rickandmorty.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

fun Context.showSnackBar(view:View,message: String,duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(view,message,duration).show()

fun ImageView.loadWithPicasso(uri:String) =
    Picasso.get()
        .load(uri)
        .error(R.drawable.ic_launcher_foreground)
        .fit().centerInside()
        .into(this)