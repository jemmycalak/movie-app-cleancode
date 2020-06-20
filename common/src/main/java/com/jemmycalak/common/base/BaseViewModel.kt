package com.jemmycalak.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.jemmycalak.common.utils.ErrorHandler
import com.jemmycalak.common.utils.Event
import com.jemmycalak.navigation.NavigationCommand

abstract class BaseViewModel : ViewModel() {

    protected val _errorHandler = MutableLiveData<Event<ErrorHandler>>()
    val errorHandler: LiveData<Event<ErrorHandler>> get() = _errorHandler

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    fun navigateTo(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.To(directions))
    }
}