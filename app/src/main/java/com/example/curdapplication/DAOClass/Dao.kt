package com.example.curdapplication.DAOClass

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.curdapplication.Model.CourseModal

@Dao
interface CourseDao {

    // Method to add data to the database
    @Insert
    suspend fun insert(model: CourseModal)

    // Method to update data in the database
    @Update
    suspend fun update(model: CourseModal)

    // Method to delete a specific course from the database
    @Delete
    suspend fun delete(model: CourseModal)

    // Query to delete all courses from the database
    @Query("DELETE FROM course_table")
    suspend fun deleteAllCourses()

    // Query to read all courses from the database, ordered by course name in ascending order
    @Query("SELECT * FROM course_table ORDER BY courseName ASC")
    fun getAllCourses(): LiveData<List<CourseModal>>
}
