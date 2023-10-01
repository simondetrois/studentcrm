package scrm.backend.backend.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import scrm.backend.backend.model.dto.CourseDto
import scrm.backend.backend.services.CourseService

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/course-api")
class CourseController (private val courseService: CourseService) {
    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(
         @RequestBody
        courseDTO: CourseDto
    ) = courseService.save(courseDTO)

    @GetMapping("/course/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getCourse(
        @RequestParam
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
         @RequestBody
        courseDTO: CourseDto
    ) = courseService.update(courseDTO)

    @DeleteMapping("/course/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCourseByID(
        @RequestParam
        id: Long
    ) = courseService.deleteById(id)

    @DeleteMapping("/courses")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllCourses() = courseService.deleteAll()
}