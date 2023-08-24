package com.scrm.backend.controller.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.scrm.backend.model.entity.CourseEntity
import com.scrm.backend.model.entity.StudentEntity
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class CourseDto(
    val courseId: Long,
    @field:Size(min = 2, max = 35, message = "courseName length must be between 2 and 35 characters")
    @field:NotNull(message = "courseName property must be set")
    val courseName: String,
    @field:NotNull(message = "professor property must be set")
    val professor: String,
    @JsonIgnoreProperties(value = ["courses"])
    @field:NotNull(message = "students property must be set")
    var students: MutableSet<CourseDtoStudents>
) {
    fun toDomainModel(): CourseEntity {
        return CourseEntity(
            this.courseId,
            this.courseName,
            this.professor,
            this.students.map { it.toStudentEntity() }.toMutableSet())
    }
}
