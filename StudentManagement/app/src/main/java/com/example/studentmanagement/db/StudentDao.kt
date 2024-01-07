package com.example.studentmanagement.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studentmanagement.utils.Constants.STUDENT_TABLE

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(studentEntity: StudentEntity)

    @Update
    fun updateStudent(studentEntity: StudentEntity)

    @Delete
    fun deleteStudent(studentEntity: StudentEntity)

    @Query("SELECT * from $STUDENT_TABLE ORDER BY studentId DESC")
    fun getAllStudents(): MutableList<StudentEntity>

    @Query("SELECT * FROM $STUDENT_TABLE WHERE studentId LIKE :id")
    fun getStudent(id: Int) : StudentEntity
}