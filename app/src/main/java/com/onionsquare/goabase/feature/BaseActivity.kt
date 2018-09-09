package com.onionsquare.goabase.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayout())
        val toolbar = provideToolbar()
        toolbar?.let {
            toolbar.title = ""
            setSupportActionBar(toolbar)
        }
    }


    abstract fun provideLayout(): Int

    abstract fun provideToolbar() : Toolbar?

    fun displayBackArrow(display: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(display)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}