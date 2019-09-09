package com.onionsquare.goabase.ui

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer

class LoadingObserver(private val loader: ProgressBar, val view: View) : Observer<Boolean> {

    override fun onChanged(t: Boolean) {
        when (t) {
            false -> {
                loader.visibility = View.GONE
                view.visibility = View.VISIBLE
            }
            true -> {
                loader.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
        }
    }
}