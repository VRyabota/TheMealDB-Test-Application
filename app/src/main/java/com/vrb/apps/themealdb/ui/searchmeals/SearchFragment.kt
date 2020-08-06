package com.vrb.apps.themealdb.ui.searchmeals

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vrb.apps.themealdb.R
import com.vrb.apps.themealdb.ui.base.BaseViewModel
import com.vrb.apps.themealdb.utils.convertDpToPx
import com.vrb.apps.themealdb.utils.handleException
import com.vrb.apps.themealdb.utils.hideKeyboard
import com.vrb.apps.themealdb.utils.showToast
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchMealsViewModel

    private var searchJob: Job? = null
    private val adapter by lazy {
        MealsAdapter(requireContext()) {
            requireContext().showToast(it.name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchToolbar.title = "The Meals DB"
        searchToolbar.inflateMenu(R.menu.menu_search)
        searchToolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.action_search) {
                val searchView = it.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        searchJob?.cancel()
                        if (newText.isNullOrBlank()) {
                            adapter.clear()
                            //TODO display previously opened meals
                        } else {
                            searchJob = lifecycleScope.launch(Dispatchers.Main) {
                                delay(600)
                                viewModel.search(newText)
                            }
                        }
                        return false
                    }
                })
            }

            return@setOnMenuItemClickListener false
        }

        val spanCount = 2
        val spacing = requireContext().convertDpToPx(10f).toInt()

        val layoutManager = GridLayoutManager(context, spanCount)
        searchRecycler.setHasFixedSize(true)
        searchRecycler.isVerticalScrollBarEnabled = false
        searchRecycler.layoutManager = layoutManager
        searchRecycler.adapter = adapter
        searchRecycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                val column = position % spanCount

                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }

                outRect.bottom = spacing
            }
        })

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.meals.observe(viewLifecycleOwner, Observer {
            when (it.status) {

                BaseViewModel.Status.LOADING -> {
                    displayLoading(true)
                }

                BaseViewModel.Status.SUCCESS -> {
                    it.data?.let { meals ->
                        adapter.setData(meals.meals)
                    }
                    displayLoading(false)
                }

                BaseViewModel.Status.ERROR -> {
                    requireContext().hideKeyboard()
                    displayLoading(false)
                    it.error?.let { error ->
                        requireContext().handleException(error)
                    }
                }
            }
        })
    }

    private fun displayLoading(state: Boolean) {

        val loader = activity?.findViewById<ConstraintLayout>(R.id.baseLoader)

        loader?.let {
            if (state) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }
}