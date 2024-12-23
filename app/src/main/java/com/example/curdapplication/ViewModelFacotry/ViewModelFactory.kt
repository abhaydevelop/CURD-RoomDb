package com.example.curdapplication.ViewModelFacotry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.curdapplication.Repository.CourseRepository
import com.example.curdapplication.ViewModelClasses.ViewModal

class ViewModelFactory(private val repository: CourseRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")  // Suppress unchecked cast warning for ViewModel creation
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModal::class.java)) {
            return ViewModal(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}