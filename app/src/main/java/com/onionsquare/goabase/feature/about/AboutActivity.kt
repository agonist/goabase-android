package com.onionsquare.goabase.feature.about

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import kotlinx.android.synthetic.main.about.*

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun provideLayout(): Int = R.layout.about

    override fun provideToolbar(): Toolbar? = custom_toolbar as Toolbar
}