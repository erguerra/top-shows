package com.github.erguerra.topshows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.erguerra.topshows.ui.TvShowDetailsFragment
import com.github.erguerra.topshows.ui.TvShowListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachFirstFragment(savedInstanceState)
    }

    private fun attachFirstFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,
                    TvShowDetailsFragment.newInstance()
                )
                .commit()
        }
    }
}
