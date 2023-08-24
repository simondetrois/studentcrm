package com.scrm.backend.controller.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.scrm.backend.model.entity.StudentEntity
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class StudentDto(
        val studentId: Long,

        @field:NotNull(message = "firstName property must be set")
        @field:Size(min = 2, max = 20, message = "firstName length must be between 2 and 20 characters")
        val firstName: String,

        @field:Size(min = 2, max = 20, message = "lastName length must be between 2 and 20 characters")
        @field:NotNull(message = "lastName property must be set") // notblank
        val lastName: String,

        @field:NotNull(message = "mail property must be set")
        @field:Email( // Validierung als String
        regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@campus.com",
        message = "mail format is invalid; valid formats ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@campus.com"
    )
        @field:Size(min = 15, max = 16, message = "mail length must be between 15 and 16 characters")
        val mail: String,

        @field:Size(min = 3, max = 20, message = "subject length must be between 3 and 20 characters")
        val subject: String,

        @JsonIgnoreProperties(value = ["students"])
   // @field:NotNull(message = "courses Property must be set")
        var courses: MutableSet<StudentDtoCourses>
){
    fun toDomainModel(): StudentEntity {
        return StudentEntity(
            this.studentId,
            this.firstName,
            this.lastName,
            this.mail,
            this.subject,
            this.courses.map { it.toCourseEntity() }.toMutableSet()
        )
    }
}