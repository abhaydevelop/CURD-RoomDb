package com.example.curdapplication.Database

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.curdapplication.DAOClass.CourseDao
import com.example.curdapplication.Model.CourseModal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Adding annotation for our database entities and DB version
@Database(entities = [CourseModal::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    // Abstract method to get DAO
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile
        private var INSTANCE: CourseDatabase? = null

        // Singleton instance of the database
        fun getInstance(context: Context, scope: CoroutineScope): CourseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CourseDatabase::class.java,
                    "course_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(CourseDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Database callback to populate data
    private class CourseDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(@NonNull db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.courseDao())
                }
            }
        }

        suspend fun populateDatabase(dao: CourseDao) {
            // Pre-populate the database
        }
    }
}
