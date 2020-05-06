package com.android.infyassignment.ui.facts

import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.infyassignment.ApplicationLevel
import com.android.infyassignment.R
import com.android.infyassignment.data.model.ClsFactsResponse
import com.android.infyassignment.viewModel.FactsViewModel
import kotlinx.android.synthetic.main.activity_facts_list_screen.*
import org.koin.android.viewmodel.ext.android.viewModel

class FactsListScreen : AppCompatActivity(), View.OnClickListener {
    private val factsViewModel: FactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facts_list_screen)
        setUI()
        //observer for list success
        factsViewModel.clsFactsResponse.observe(this, Observer {
            renderUI(it)
        })
        // observer for Failure

        factsViewModel.isErrorRaised.observe(this, Observer {
            handleError(it)
        })

    }

    /**
     * function to render all Views
     */
    private fun setUI() {
        btn_retry.setOnClickListener(this)
        //adding a layoutmanager
        progressbar_view.visibility = View.VISIBLE
        facts_recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        if (ApplicationLevel.verifyAvailableNetwork(this)) {
            factsViewModel.getFacts()
        } else {
            progressbar_view.visibility = View.GONE
            content_view.visibility = View.GONE
            no_internet_view.visibility = View.VISIBLE
            internet_txt.text = getString(R.string.no_internetconnection)
        }
        //** Set the colors of the Pull To Refresh View
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        itemsswipetorefresh.setOnRefreshListener {
            if (ApplicationLevel.verifyAvailableNetwork(this)) {
                //progressbar_view.visibility = View.VISIBLE
                factsViewModel.getFacts()
            } else {
                itemsswipetorefresh.isRefreshing = false
                Toast.makeText(this,getString(R.string.no_internetconnection),Toast.LENGTH_LONG).show()
            }
        }
    }


    /**
     * function to handle the Error from Server
     */
    private fun handleError(isError: Boolean) {
        if(isError){
            content_view.visibility = View.GONE;
            no_internet_view.visibility = View.VISIBLE
            internet_txt.text = getString(R.string.txt_went_wrong)
            progressbar_view.visibility = View.GONE
            itemsswipetorefresh.isRefreshing = false
        }
    }

    /**
     * Rendering the UI once the LiveData changes by the Response from server.
     */
    private fun renderUI(clsFactsResponse: ClsFactsResponse) {
        val factsList = clsFactsResponse.clsFacts
        if (!ApplicationLevel.isNullOrEmpty(clsFactsResponse.title)) {
            content_view.visibility = View.VISIBLE
            no_internet_view.visibility = View.GONE
            txt_title.text = clsFactsResponse.title
            if (factsList.isNotEmpty()) {
                val factsListAdapter = FactsListAdapter(this, clsFactsResponse.clsFacts)
                facts_recyclerView.adapter = factsListAdapter
            } else {
                content_view.visibility = View.GONE;
                no_internet_view.visibility = View.VISIBLE
                internet_txt.text = getString(R.string.txt_nodata)
            }
            progressbar_view.visibility = View.GONE
            itemsswipetorefresh.isRefreshing = false
        }
    }


    /**
     * View Click Listener to perform Click on the re-try button
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_retry -> {
                if (ApplicationLevel.verifyAvailableNetwork(this)) {
                    progressbar_view.visibility = View.VISIBLE
                    factsViewModel.getFacts()
                } else {
                    Toast.makeText(this,getString(R.string.no_internetconnection),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
