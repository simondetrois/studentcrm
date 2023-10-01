package com.scrm.backend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.scrm.backend.controller.dto.CourseDto
import com.scrm.backend.controller.dto.StudentDtoCourses
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.SequenceGenerator

@Entity(name = "Course")
class CourseEntity(

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
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
){
    fun toResponseDto(): CourseDto {
        return CourseDto(
                this.courseId,
                this.courseName,
                this.professor,
                this.students.map { it.toResponseDtoForCourseDto() }.toMutableSet()
        )
    }

    fun toResponseDtoForStudentDto(): StudentDtoCourses{
        return StudentDtoCourses(
            this.courseId,
            this.courseName,
            this.professor
        )
    }
}
