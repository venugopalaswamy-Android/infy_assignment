package com.android.infyassignment.ui.facts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.infyassignment.R
import com.android.infyassignment.utilities.CallBackInterFace
import kotlinx.android.synthetic.main.activity_facts_list_screen.*


class FactsListScreen : AppCompatActivity(), CallBackInterFace {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts_list_screen)
        setUI()
    }

    private fun setUI() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, FactsListFragment()).commit()

    }

    override fun setTitle(value: String) {
        txt_title.text = value
    }

}
