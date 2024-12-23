package com.example.curdapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.curdapplication.Adapter.CourseRVAdapter
import com.example.curdapplication.Model.CourseModal
import com.example.curdapplication.ViewModelClasses.ViewModal
import com.example.curdapplication.Repository.CourseRepository
import com.example.curdapplication.ViewModelFacotry.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var coursesRV: RecyclerView
    private lateinit var viewModel: ViewModal
    private lateinit var model: CourseModal

    private val addCourseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val courseName = data?.getStringExtra(NewCourseActivity.EXTRA_COURSE_NAME) ?: ""
            val courseDescription = data?.getStringExtra(NewCourseActivity.EXTRA_DESCRIPTION) ?: ""
            val courseDuration = data?.getStringExtra(NewCourseActivity.EXTRA_DURATION) ?: ""

            model = CourseModal(0,courseName, courseDescription, courseDuration)
            viewModel.insert(model)
            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show()
        }
    }

    private val editCourseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            var id = data?.getIntExtra(NewCourseActivity.EXTRA_ID, -1) ?: -1
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }
            val courseName = data!!.getStringExtra(NewCourseActivity.EXTRA_COURSE_NAME) ?: ""
            val courseDescription = data.getStringExtra(NewCourseActivity.EXTRA_DESCRIPTION) ?: ""
            val courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_DURATION) ?: ""

            model = CourseModal(id,courseName, courseDescription, courseDuration).apply { id = id }
            viewModel.update(model)
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coursesRV = findViewById(R.id.idRVCourses)
        val fab: FloatingActionButton = findViewById(R.id.idFABAdd)

        val repository = CourseRepository(application) // Assuming you have a CourseRepository that handles data
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ViewModal::class.java)

        fab.setOnClickListener {
            val intent = Intent(this, NewCourseActivity::class.java)
            addCourseLauncher.launch(intent)
        }

        coursesRV.layoutManager = LinearLayoutManager(this)
        coursesRV.setHasFixedSize(true)

        val adapter = CourseRVAdapter()
        coursesRV.adapter = adapter

        viewModel.allCourses.observe(this) { models ->
            adapter.submitList(models)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.delete(adapter.getCourseAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Course deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(coursesRV)

        adapter.setOnItemClickListener(object : CourseRVAdapter.OnItemClickListener {
            override fun onItemClick(model: CourseModal) {
                val intent = Intent(this@MainActivity, NewCourseActivity::class.java).apply {
                    putExtra(NewCourseActivity.EXTRA_ID, model.id)
                    putExtra(NewCourseActivity.EXTRA_COURSE_NAME, model.courseName)
                    putExtra(NewCourseActivity.EXTRA_DESCRIPTION, model.courseDescription)
                    putExtra(NewCourseActivity.EXTRA_DURATION, model.courseDuration)
                }
                editCourseLauncher.launch(intent)
            }
        })
    }
}
