package com.pmesa48.pablomesa_challenge.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pmesa48.pablomesa_challenge.R
import com.pmesa48.pablomesa_challenge.framework.entrylist.EntryListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, EntryListFragment.newInstance())
            .commitNow()
    }

}