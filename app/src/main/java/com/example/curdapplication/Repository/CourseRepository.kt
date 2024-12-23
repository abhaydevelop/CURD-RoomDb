package com.example.curdapplication.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.curdapplication.DAOClass.CourseDao
import com.example.curdapplication.Database.CourseDatabase
import com.example.curdapplication.Model.CourseModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseRepository(application: Application) {

    private val dao: CourseDao
    val allCourses: LiveData<List<CourseModal>>

    init {
        val database = CourseDatabase.getInstance(application, CoroutineScope(Dispatchers.IO))
        dao = database.courseDao()
        allCourses = dao.getAllCourses()
    }

    // Method to insert a course
    fun insert(model: CourseModal) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(model)
        }
    }

    // Method to update a course
    fun update(model: CourseModal) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.update(model)
        }
    }

    // Method to delete a specific course
    fun delete(model: CourseModal) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(model)
        }
    }

    // Method to delete all courses
    fun deleteAllCourses() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllCourses()
        }
    }
}
