package com.example.studentmanagement.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StudentEntity::class], version = 1)
abstract class StudentDatabase : RoomDatabase(){
    abstract fun dao(): StudentDao
}