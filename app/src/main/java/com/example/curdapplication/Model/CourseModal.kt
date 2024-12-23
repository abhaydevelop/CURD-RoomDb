package com.example.curdapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Setting the table name
@Entity(tableName = "course_table")
data class CourseModal(

    // Auto-incrementing ID for each course
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Variables for course details
    val courseName: String,
    val courseDescription: String,
    val courseDuration: String
)
