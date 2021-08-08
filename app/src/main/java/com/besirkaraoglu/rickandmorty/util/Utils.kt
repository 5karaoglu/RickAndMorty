package com.besirkaraoglu.rickandmorty.util

import android.view.View
import android.widget.ImageView
import com.besirkaraoglu.rickandmorty.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

fun showSnackBar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(view,message,duration).show()

fun showSnackBarWithAction(
    view: View,
    message: String,
    clickListener: View.OnClickListener,
    actionText: String,
    duration: Int = Snackbar.LENGTH_SHORT
) =
    Snackbar.make(view,message,duration).setAction(actionText,clickListener).show()

fun ImageView.loadWithPicasso(uri:String) =
    Picasso.get()
        .load(uri)
        .error(R.drawable.ic_launcher_foreground)
        .fit().centerInside()
        .into(this)


fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}