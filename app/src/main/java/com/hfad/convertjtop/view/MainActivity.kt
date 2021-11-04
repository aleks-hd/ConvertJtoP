package com.hfad.convertjtop.view

import android.os.Bundle
import com.hfad.convertjtop.R
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }

    fun initFragment(){
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_conteiner, ListFileFragment.newInstance())
            .commit()
    }


}