package com.scrm.backend.controller.dto

import com.scrm.backend.controller.CourseController
import com.scrm.backend.model.entity.CourseEntity

class StudentDtoCourses(
    val courseId: Long,
    val courseName: String,
    val professor: String
)
{
    fun toCourseEntity(): CourseEntity{
        return CourseEntity(
            this.courseId,
            this.courseName,
            this.professor,
            mutableSetOf()
        )
    }
}