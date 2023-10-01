package scrm.backend.backend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.SequenceGenerator
import scrm.backend.backend.model.dto.CourseDto

@Entity(name = "course")
class CourseEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
    @SequenceGenerator(name = "course_generator", sequenceName = "id_sequence_of_courses", allocationSize = 1)
    @Column(name = "course_id", updatable = false)
    val courseId: Long,

    @Column(name = "course_name", nullable = false)
    val courseName: String,

    @Column(name = "professor", nullable = false)
    val professor: String,

    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    var students: MutableSet<StudentEntity>
) {
    fun toResponseDto(): CourseDto {
        return CourseDto(
            this.courseId,
            this.courseName,
            this.professor,
            this.students.map { it.toResponseDto() }.toMutableSet()
        )
    }
}