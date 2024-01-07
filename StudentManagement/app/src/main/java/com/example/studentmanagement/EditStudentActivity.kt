package com.example.studentmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.example.studentmanagement.db.StudentDatabase
import com.example.studentmanagement.db.StudentEntity
import com.example.studentmanagement.utils.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date

class EditStudentActivity : AppCompatActivity() {
    private lateinit var student: StudentEntity
    private lateinit var txtStudentIdentity: TextInputLayout
    private lateinit var txtFullName: TextInputLayout
    private lateinit var txtDateOfBirth: TextView
    private lateinit var autoCompleteTextViewCountry: AutoCompleteTextView
    private lateinit var btnSelectDate: MaterialButton
    private lateinit var btnEditStudent: MaterialButton
    private lateinit var btnDeleteStudent: MaterialButton
    private val studentDatabase: StudentDatabase by lazy {
        Room.databaseBuilder(this, StudentDatabase::class.java, Constants.STUDENT_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var currentDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)
        txtStudentIdentity = findViewById(R.id.txtStudentIdentity)
        txtFullName = findViewById(R.id.txtName)
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth)
        autoCompleteTextViewCountry = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        btnSelectDate = findViewById(R.id.btnDateOfBirth)
        btnDeleteStudent = findViewById(R.id.btnDelete)
        btnEditStudent = findViewById(R.id.btnEdit)
        val country = resources.getStringArray(R.array.country)
        val arrayAdapter = ArrayAdapter(this, R.layout.list_item, country)
        autoCompleteTextViewCountry.setAdapter(arrayAdapter)
        val id = intent.extras?.getInt("student_id") ?: 2
        Toast.makeText(this@EditStudentActivity, id.toString(), Toast.LENGTH_LONG).show()
        student = studentDatabase.dao().getStudent(id)
        txtStudentIdentity.editText?.setText("Hello")
        txtStudentIdentity.editText?.setText(student.studentIdentity)
        txtFullName.editText?.setText(student.fullName)

        txtDateOfBirth?.setText(student.dateOfBirth)
        btnDeleteStudent.setOnClickListener {
            studentDatabase.dao().deleteStudent(student)
            finish()
        }
        btnEditStudent.setOnClickListener {
            studentDatabase.dao().deleteStudent(student)
            val updateStudent = StudentEntity(student.studentId, txtStudentIdentity?.editText?.text.toString(), txtFullName?.editText?.text.toString(), txtDateOfBirth?.text.toString(), autoCompleteTextViewCountry.text.toString() )
            studentDatabase.dao().insertStudent(updateStudent)
            finish()
        }
        btnSelectDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")

            // Setting up the event for when ok is clicked
            datePicker.addOnPositiveButtonClickListener {
                // formatting date in dd-mm-yyyy format.
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))
                currentDate = date
                txtDateOfBirth.text = date
            }
            datePicker.addOnNegativeButtonClickListener {
                Toast.makeText(this, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }
}