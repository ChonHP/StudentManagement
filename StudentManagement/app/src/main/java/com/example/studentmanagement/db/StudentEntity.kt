package com.example.studentmanagement.db
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.studentmanagement.utils.Constants.STUDENT_TABLE
import java.util.Date

@Entity(tableName = STUDENT_TABLE)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val studentId: Int,
    @ColumnInfo(name = "student_identity")
    val studentIdentity: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String,
    @ColumnInfo(name = "country")
    val country: String,
)
