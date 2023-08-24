package com.scrm.backend.controller.dto

import com.scrm.backend.model.entity.StudentEntity

class CourseDtoStudents(
    val studentId: Long,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val subject: String
)
{
    fun toStudentEntity(): StudentEntity{
        return StudentEntity(
            this.studentId,
            this.firstName,
            this.lastName,
            this.mail,
            this.subject,
            mutableSetOf()
        )
    }
}
