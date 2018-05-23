package com.onionsquare.psyaround.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        val toolbar = provideToolbar()
        toolbar.title = provideToolbarTitle()
        setSupportActionBar(toolbar)
    }

    abstract fun provideToolbarTitle(): String

    abstract fun provideLayout(): Int

    abstract fun provideToolbar() : Toolbar

    fun displayBackArrow(display: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(display)
    }

}