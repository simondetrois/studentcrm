package com.scrm.backend.controller

import com.scrm.backend.controller.dto.StudentDto
import com.scrm.backend.services.StudentService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.Positive

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/student-api")
@Validated
class StudentController(private val studentService: StudentService) {

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    fun addStudent(
       @RequestBody
        studentDTO: StudentDto
    ) = studentService.save(studentDTO)

    // todo: schauen mvn ktlint-auto-format ... fertig implementieren und im zweiten commit auto-formatten
    // check complains if violated
    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getStudent(
        @PathVariable @Positive(message = "must be greater than 0")
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
        @Valid @RequestBody
        studentDTO: StudentDto
    ) = studentService.update(studentDTO)

    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteStudentByID( // optionaler param zu zwingenden machen @Pathvariable
        @PathVariable @Positive(message = "must be greater than 0")
        id: Long
    ) = studentService.deleteByID(id)

    @DeleteMapping("/students")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAllStudents() = studentService.deleteAll()
}
