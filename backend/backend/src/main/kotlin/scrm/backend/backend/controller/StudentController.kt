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
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import scrm.backend.backend.model.dto.StudentDto
import scrm.backend.backend.services.StudentService

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/student-api")
class StudentController(private val studentService: StudentService) {
    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    fun addStudent(
        @RequestBody
        studentDTO: StudentDto
    ) = studentService.save(studentDTO)

    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStudent(
        @PathVariable
        id: Long
    ) = studentService.findById(id)

    @GetMapping("/students/asc/{field}/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllStudentsSortedAscending(
        @PathVariable field: String,
        @PathVariable offset: Int,
        @PathVariable pageSize: Int
    ) = studentService.findPaginatedStudentsSortedAscending(field, offset, pageSize)

    @GetMapping("/students/desc/{field}/{offset}/{pageSize}")
    @ResponseStatus(HttpStatus.OK)
    fun getAllStudentsSortedDescending(
        @PathVariable field: String,
        @PathVariable offset: Int,
        @PathVariable pageSize: Int
    ) = studentService.findAllPaginatedStudentsSortedDescending(field, offset, pageSize)

    @PutMapping("/student")
    @ResponseStatus(HttpStatus.OK)
    fun updateStudent(
        @RequestBody
        studentDTO: StudentDto
    ) = studentService.update(studentDTO)

    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudentByID(
        @PathVariable
        id: Long
    ) = studentService.deleteByID(id)

    @DeleteMapping("/students")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllStudents() = studentService.deleteAll()
}