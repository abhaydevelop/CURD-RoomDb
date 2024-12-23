package com.example.curdapplication.ViewModelClasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.curdapplication.Model.CourseModal
import com.example.curdapplication.Repository.CourseRepository

class ViewModal(private val repository: CourseRepository) : ViewModel() {

    val allCourses: LiveData<List<CourseModal>> = repository.allCourses

    fun insert(course: CourseModal) {
        repository.insert(course)
    }

    fun update(course: CourseModal) {
        repository.update(course)
    }

    fun delete(course: CourseModal) {
        repository.delete(course)
    }
}
