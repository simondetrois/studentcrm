package scrm.backend.backend.model.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import scrm.backend.backend.model.entity.StudentEntity

class StudentDto (
    val studentId: Long,
    val firstName: String,
    val lastName: String,
    val mail: String,
    val subject: String,
    @JsonIgnoreProperties(value = ["students"])
    var courses: MutableSet<CourseDto>
  ){
    fun toDomainModel(): StudentEntity {
        return StudentEntity(
            this.studentId,
            this.firstName,
            this.lastName,
            this.mail,
            this.subject,
            this.courses.map { it.toDomainModel() }.toMutableSet()
        )
    }

}