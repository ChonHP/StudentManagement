package com.example.studentmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.studentmanagement.adapter.StudentAdapter
import com.example.studentmanagement.databinding.ActivityMainBinding
import com.example.studentmanagement.db.StudentDatabase
import com.example.studentmanagement.utils.Constants

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val studentDatabase: StudentDatabase by lazy {
        Room.databaseBuilder(this, StudentDatabase::class.java, Constants.STUDENT_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private val studentAdapter: StudentAdapter by lazy { StudentAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkItem()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add -> {
                val intent: Intent = Intent(this, AddStudentActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }
    private fun checkItem(){
        binding.apply {
            if (studentDatabase.dao().getAllStudents().isNotEmpty()){
                studentAdapter.differ.submitList(studentDatabase.dao().getAllStudents())
                setUpRecyclerView()
            }
        }
    }
    private fun setUpRecyclerView(){
        binding.recyclerViewStudentList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            studentAdapter.onItemClick = { student ->
                val intent = Intent(this@MainActivity, EditStudentActivity::class.java).apply {
                    putExtra("student_id", student.studentId)
                }
                startActivity(intent)
            }
            adapter=studentAdapter
        }
    }
}