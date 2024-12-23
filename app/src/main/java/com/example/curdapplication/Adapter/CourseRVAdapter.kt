package com.example.curdapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.curdapplication.Model.CourseModal
import com.example.curdapplication.R

class CourseRVAdapter : ListAdapter<CourseModal, CourseRVAdapter.ViewHolder>(DIFF_CALLBACK) {

    // Listener for item clicks
    private var listener: OnItemClickListener? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CourseModal>() {
            override fun areItemsTheSame(oldItem: CourseModal, newItem: CourseModal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CourseModal, newItem: CourseModal): Boolean {
                return oldItem.courseName == newItem.courseName &&
                        oldItem.courseDescription == newItem.courseDescription &&
                        oldItem.courseDuration == newItem.courseDuration
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        holder.courseNameTV.text = model.courseName
        holder.courseDescTV.text = model.courseDescription
        holder.courseDurationTV.text = model.courseDuration
    }

    fun getCourseAt(position: Int): CourseModal = getItem(position)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.idTVCourseName)
        val courseDescTV: TextView = itemView.findViewById(R.id.idTVCourseDescription)
        val courseDurationTV: TextView = itemView.findViewById(R.id.idTVCourseDuration)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(model: CourseModal)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}
