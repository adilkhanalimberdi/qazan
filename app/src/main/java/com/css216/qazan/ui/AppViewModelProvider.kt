package com.css216.qazan.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import com.css216.qazan.QazanApplication

object AppViewModelProvider {
    val Factory = viewModelFactory { }
}

fun CreationExtras.qazanApplication(): QazanApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as QazanApplication
