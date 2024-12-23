package com.example.curdapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class NewCourseActivity : AppCompatActivity() {

    private lateinit var courseNameEdt: EditText
    private lateinit var courseDescEdt: EditText
    private lateinit var courseDurationEdt: EditText
    private lateinit var courseBtn: Button

    companion object {
        const val EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID"
        const val EXTRA_COURSE_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_NAME"
        const val EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION"
        const val EXTRA_DURATION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DURATION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_course)

        courseNameEdt = findViewById(R.id.idEdtCourseName)
        courseDescEdt = findViewById(R.id.idEdtCourseDescription)
        courseDurationEdt = findViewById(R.id.idEdtCourseDuration)
        courseBtn = findViewById(R.id.idBtnSaveCourse)

        intent?.let {
            if (it.hasExtra(EXTRA_ID)) {
                courseNameEdt.setText(it.getStringExtra(EXTRA_COURSE_NAME))
                courseDescEdt.setText(it.getStringExtra(EXTRA_DESCRIPTION))
                courseDurationEdt.setText(it.getStringExtra(EXTRA_DURATION))
            }
        }

        courseBtn.setOnClickListener {
            val courseName = courseNameEdt.text.toString()
            val courseDesc = courseDescEdt.text.toString()
            val courseDuration = courseDurationEdt.text.toString()

            if (courseName.isEmpty() || courseDesc.isEmpty() || courseDuration.isEmpty()) {
                Toast.makeText(this, "Please enter valid course details.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveCourse(courseName, courseDesc, courseDuration)
        }
    }

    private fun saveCourse(courseName: String, courseDescription: String, courseDuration: String) {
        val data = Intent().apply {
            putExtra(EXTRA_COURSE_NAME, courseName)
            putExtra(EXTRA_DESCRIPTION, courseDescription)
            putExtra(EXTRA_DURATION, courseDuration)

            val id = intent.getIntExtra(EXTRA_ID, -1)
            if (id != -1) {
                putExtra(EXTRA_ID, id)
            }
        }

        setResult(RESULT_OK, data)
        Toast.makeText(this, "Course has been saved to Room Database.", Toast.LENGTH_SHORT).show()
    }
}
