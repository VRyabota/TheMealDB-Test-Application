package com.vrb.apps.themealdb.ui.searchmeals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.vrb.apps.themealdb.data.IRepository
import com.vrb.apps.themealdb.data.remote.models.MealsList
import com.vrb.apps.themealdb.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers

class SearchMealsViewModel constructor(
    private val repository: IRepository
) : BaseViewModel() {

    private val searchQuery = MutableLiveData<String>()

    private val _meals = searchQuery.switchMap { name ->
        makeApiCall(Dispatchers.IO) { repository.search(name) }
    }

    val meals: LiveData<Result<MealsList>> = _meals

    fun search(name: String) {
        searchQuery.value = name
    }
}