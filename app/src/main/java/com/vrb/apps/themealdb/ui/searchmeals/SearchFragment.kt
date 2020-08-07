package com.vrb.apps.themealdb.ui.searchmeals

import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vrb.apps.themealdb.R
import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.ui.base.BaseFragment
import com.vrb.apps.themealdb.ui.base.BaseViewModel
import com.vrb.apps.themealdb.utils.Constants.DATA_MEAL_DETAILS
import com.vrb.apps.themealdb.utils.convertDpToPx
import com.vrb.apps.themealdb.utils.handleException
import com.vrb.apps.themealdb.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class SearchFragment : BaseFragment<SearchMealsViewModel>() {

    companion object {
        const val SHOULD_TRIGGER_RELOAD = "SHOULD_TRIGGER_RELOAD"
    }

    private var searchJob: Job? = null
    private var shouldTriggerTextChanged = true

    private val adapter by lazy {
        MealsAdapter(requireContext()) { meal ->
            showMealDetails(meal)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(SHOULD_TRIGGER_RELOAD, false)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shouldTriggerTextChanged = savedInstanceState?.getBoolean(SHOULD_TRIGGER_RELOAD) ?: true
        viewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchToolbar.title = "The Meals DB"
        searchToolbar.inflateMenu(R.menu.menu_search)

        val menuItem = searchToolbar.menu.getItem(0)

        if (menuItem.itemId == R.id.action_search) {
            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Timber.d("text changed, new text: ${newText ?: "null"}")
                    if (shouldTriggerTextChanged) {
                        searchJob?.cancel()
                        searchJob = lifecycleScope.launch(Dispatchers.Main) {
                            Timber.d("search query value: ${viewModel.searchQuery.value}")
                            if (viewModel.searchQuery.value != newText) {
                                viewModel.searchQuery.value = newText ?: ""
                            }
                            delay(1000)
                            viewModel.search()
                        }
                        if (searchView.query.isNullOrBlank()) {
                            viewModel.getStoredMeals()
                            Timber.d("displaying stored meals")
                        }
                    } else {
                        shouldTriggerTextChanged = true
                    }
                    return false
                }
            })
        }

        val spanCount =
            if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                2
            else
                4

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
        if (viewModel.searchQuery.value.isNullOrBlank())
            viewModel.getStoredMeals()
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

    private fun showMealDetails(meal: Meal) {

        viewModel.storeMeal(meal)
        shouldTriggerTextChanged = false
        requireContext().hideKeyboard()
        val args = bundleOf(DATA_MEAL_DETAILS to Gson().toJson(meal))
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, args)
    }
}