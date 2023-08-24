package com.scrm.backend.controller

import com.scrm.backend.controller.dto.CourseDto
import com.scrm.backend.services.CourseService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@Validated
@RequestMapping("/course-api")
class CourseController(private val courseService: CourseService) {

    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(
        @Valid @RequestBody
        courseDTO: CourseDto
    ) = courseService.save(courseDTO)

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCourse(
        @RequestParam @Positive(message = "must be greater than 0")
        id: Long
    ) = courseService.findById(id)

    @GetMapping("/courses/asc/{field}/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllStudentsSortedAscending(
        @PathVariable field: String,
        @PathVariable offset: Int,
        @PathVariable pageSize: Int
    ) = courseService.findAllPaginatedCoursesSortedAscending(field, offset, pageSize)

    @GetMapping("/courses/desc/{field}/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllStudentsSortedDescending(
        @PathVariable field: String,
        @PathVariable offset: Int,
        @PathVariable pageSize: Int
    ) = courseService.findAllPaginatedCoursesSortedDescending(field, offset, pageSize)

    @PutMapping("/course")
    @ResponseStatus(HttpStatus.OK)
    fun updateCourse(
        @Valid @RequestBody
        courseDTO: CourseDto
    ) = courseService.update(courseDTO)

    @DeleteMapping("/course/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourseByID(
        @RequestParam @Positive(message = "must be greater than 0")
        id: Long
    ) = courseService.deleteById(id)

    @DeleteMapping("/courses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllCourses() = courseService.deleteAll()
}
