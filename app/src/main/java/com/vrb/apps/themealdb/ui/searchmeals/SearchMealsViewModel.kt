package com.vrb.apps.themealdb.ui.searchmeals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.vrb.apps.themealdb.data.IRepository
import com.vrb.apps.themealdb.data.models.Meal
import com.vrb.apps.themealdb.data.models.MealsList
import com.vrb.apps.themealdb.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchMealsViewModel constructor(
    private val repository: IRepository
) : BaseViewModel() {
    private val getQuery = MutableLiveData<GetOperation<MealsList?>>()

    private val _meals = getQuery.switchMap { query ->
        performGetOperation(Dispatchers.IO, query.accessStrategy, { query.localCall?.invoke() }, { query.apiCall?.invoke() })
    }

    val meals: LiveData<Result<MealsList?>> = _meals
    val searchQuery = MutableLiveData<String>()

    fun search() {
        val searchText = searchQuery.value
        if (!searchText.isNullOrBlank())
            getQuery.value = GetOperation(DataAccessStrategy.REMOTE, null, { repository.search(searchText) })
    }

    fun getStoredMeals() {
        getQuery.value = GetOperation(DataAccessStrategy.LOCAL, { repository.getStoredMeals() }, null)
    }

    fun storeMeal(meal: Meal) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.storeMeal(meal)
        }
    }
}