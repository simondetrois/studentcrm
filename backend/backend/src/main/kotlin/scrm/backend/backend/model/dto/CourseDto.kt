package scrm.backend.backend.model.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import scrm.backend.backend.model.entity.CourseEntity

class CourseDto(
    val courseId: Long,
    val courseName: String,
    val professor: String,
    @JsonIgnoreProperties(value = ["courses"])
    var students: MutableSet<StudentDto>
) {
    fun toDomainModel(): CourseEntity {
        return CourseEntity(
            this.courseId,
            this.courseName,
            this.professor,
            this.students.map { it.toDomainModel() }.toMutableSet()
        )
    }
}
