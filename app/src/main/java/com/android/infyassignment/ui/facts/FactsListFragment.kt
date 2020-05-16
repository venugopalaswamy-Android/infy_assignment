package com.android.infyassignment.ui.facts

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.infyassignment.R
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.utilities.CallBackInterFace
import com.android.infyassignment.utilities.Common
import com.android.infyassignment.viewmodel.FactsViewModel
import kotlinx.android.synthetic.main.fragment_facts_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple  subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
class FactsListFragment : Fragment() {

    private val factsViewModel: FactsViewModel by viewModel()
    private lateinit var callBackInterface: CallBackInterFace


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facts_list, container, false)
    }

    private lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        try {
            callBackInterface = context as CallBackInterFace
        } catch (ex: ClassCastException) {
            throw ClassCastException(context::class.simpleName + " must implement onSomeEventListener")
        }
    }

    fun getAttachedContext(): Context {
        return mContext
    }


    override fun onResume() {
        super.onResume()
        val isTablet: Boolean = mContext.resources.getBoolean(R.bool.isTablet)
        // condition to set the Different View Style for Tablet & mobile cards.
        if (isTablet) {
            recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        } else {
            recyclerView.layoutManager =
                LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        }

        setUpPullToRefresh() //pull to refresh

        progressbar_view.visibility = View.GONE
        error_view.visibility = View.GONE

        setUpListToDisplay()// list display

        setUpTitleInActivity() // title set


        setUpErrorHandling() // error Handling
    }

    /**
     * Error Handling from Server Response
     */
    private fun setUpErrorHandling() {
        factsViewModel.isErrorRaised.observe(this, Observer {
            if (it) {
                error_view.visibility = View.VISIBLE
                progressbar_view.visibility = View.GONE
                text_error.text = getString(R.string.txt_went_wrong)
                swipeToRefresh.isRefreshing = false
            }
        })

    }


    /**
     * render & update the UI
     */
    private var isCalledForData: Boolean = false
    private fun setUpListToDisplay() {
        factsViewModel.listOfFacts.observe(this, Observer {
            if (it == null || it.isEmpty()) {
                if (!isCalledForData) {
                    progressbar_view.visibility = View.VISIBLE
                    error_view.visibility = View.GONE
                    callToServerForFetchingFacts(false)
                }
            } else {
                progressbar_view.visibility = View.GONE
                error_view.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                var factsListAdapter = FactsListAdapter(mContext, it as MutableList<ClsFacts>)
                recyclerView.adapter = factsListAdapter
                swipeToRefresh.isRefreshing = false

                Handler().postDelayed({ isCalledForData = false }, 3000)


            }
        })
    }

    /**
     * render Title in Activity
     */
    private fun setUpTitleInActivity() {
        factsViewModel.rootFact.observe(this, Observer {
            if (it != null) {
                callBackInterface.setTitle(it.title)
            }
        })
    }

    /**
     * pull to refresh Functionality
     */
    private fun setUpPullToRefresh() {
        //** Set the colors of the Pull To Refresh View
        swipeToRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                mContext as Activity,
                R.color.colorPrimary
            )
        )
        swipeToRefresh.setColorSchemeColors(Color.WHITE)

        swipeToRefresh.setOnRefreshListener {
            callToServerForFetchingFacts(true)
        }
    }


    /**
     * calling to server to fetch the Facts
     */
    private fun callToServerForFetchingFacts(isFromPullRefresh: Boolean) {

        if (Common.verifyAvailableNetwork(mContext as Activity)) {
            if (isFromPullRefresh)
                progressbar_view.visibility = View.GONE
            else
                progressbar_view.visibility = View.VISIBLE
            isCalledForData = true
            factsViewModel.callToGetFactsFromServer()
        } else {
            if (isFromPullRefresh) {
                swipeToRefresh.isRefreshing = false
            }
            progressbar_view.visibility = View.GONE
            Toast.makeText(
                mContext,
                getString(R.string.no_internet_connection),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
