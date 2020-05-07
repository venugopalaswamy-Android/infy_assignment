package com.android.infyassignment.ui.facts

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.infyassignment.ApplicationLevel
import com.android.infyassignment.R
import com.android.infyassignment.data.model.ClsFacts
import com.android.infyassignment.utilities.CallBackInterFace
import com.android.infyassignment.viewModel.FactsViewModel
import kotlinx.android.synthetic.main.fragment_facts_list.*
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FactsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FactsListFragment : Fragment() {

    private val factsViewModel: FactsViewModel by viewModel()
    private lateinit var callBackInterface: CallBackInterFace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facts_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            callBackInterface = context as CallBackInterFace
        } catch (ex: ClassCastException) {
            throw ClassCastException(context::class.simpleName + " must implement onSomeEventListener");
        }
    }


    override fun onStart() {
        super.onStart()
        recyclerView.layoutManager =
            LinearLayoutManager(view!!.context, RecyclerView.VERTICAL, false)
        progressbar_view.visibility = View.GONE
        error_view.visibility = View.GONE

        setUpListToDisplay()

        setUpTitleInActivity()

        setUpPullToRefresh()

        setUpErrorHandling()
    }

    /**
     * Error Handling from Server Response
     */
    private fun setUpErrorHandling() {
        error_view.visibility = View.VISIBLE
        progressbar_view.visibility = View.GONE
        text_error.text = getString(R.string.txt_went_wrong)
    }


    /**
     * render & update the UI
     */
    private fun setUpListToDisplay() {
        factsViewModel.getTotalFactsObjectFromDB().observe(this, Observer {
            if (it.isEmpty() || it == null) {
                callToServerForFetchingFacts(false)
            } else {
                progressbar_view.visibility = View.GONE
                error_view.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                var factsListAdapter = FactsListAdapter(view!!.context, it)
                recyclerView.adapter = factsListAdapter
                itemsswipetorefresh.isRefreshing = false

            }
        })
    }

    /**
     * render Title in Activity
     */
    private fun setUpTitleInActivity() {
        factsViewModel.getRootFacts().observe(this, Observer {
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
        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                view!!.context,
                R.color.colorPrimary
            )
        )
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        itemsswipetorefresh.setOnRefreshListener {
            callToServerForFetchingFacts(true)
        }
    }


    /**
     * calling to server to fetch the Facts
     */
    fun callToServerForFetchingFacts(isFromPullRefresh: Boolean) {
        if (ApplicationLevel.verifyAvailableNetwork(view!!.context as Activity)) {
            if (isFromPullRefresh)
                progressbar_view.visibility = View.GONE
            else
                progressbar_view.visibility = View.VISIBLE
            factsViewModel.callToGetFactsFromServer()
        } else {
            itemsswipetorefresh.isRefreshing = false
            Toast.makeText(
                view!!.context,
                getString(R.string.no_internetconnection),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
