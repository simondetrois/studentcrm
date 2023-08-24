package com.scrm.backend.services

import com.scrm.backend.exceptionhandling.exceptions.NoElementUnderThisIdException
import com.scrm.backend.model.entity.StudentEntity
import com.scrm.backend.repository.CourseRepository
import com.scrm.backend.repository.StudentRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus

class StudentServiceUnitTest {

    private val studentRepositoryMock = mockk<StudentRepository>(relaxed = true)

    private val courseRepositoryMock = mockk<CourseRepository>(relaxed = true)

    private val service = StudentService(studentRepositoryMock, courseRepositoryMock)

    private val students = mutableListOf<StudentEntity>(
        StudentEntity(1, "Max", "Mustermann", "MaMu@campus.com", mutableSetOf()),
        StudentEntity(2, "Erika", "Mustermann", "ErMu@campus.com", mutableSetOf()),
        StudentEntity(3, "Jon", "Doe", "JoDo@campus.com", mutableSetOf()),
        StudentEntity(4, "Jane", "Doe", "JaDo@campus.com", mutableSetOf()),
        StudentEntity(5, "Max", "Maier", "MaMa@campus.com", mutableSetOf())
    )

    @Test
    fun `save() - should return Student entity`() {
        // given/arrange (define data/ create state):
        every { studentRepositoryMock.save(students[0]) } returns students[0]
        // when/act (key action of system):
        val response = service.save(students[0])
        val savedStudent = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { studentRepositoryMock.save(students[0]) }
        assertEquals(students[0].firstName, savedStudent?.firstName)
        assertEquals(students[0].lastName, savedStudent?.lastName)
        assertEquals(students[0].mail, savedStudent?.mail)
        assertEquals(students[0].courses, savedStudent?.courses)
        assertEquals(HttpStatus.CREATED, status)
    }

    @Test
    fun `findById() - should return Student entity`() {
        // given/arrange (define data/ create state):
        every { studentRepositoryMock.findByIdOrNull(1) } returns students[0]
        // when/act (key action of system):
        val response = service.findById(1)
        val savedStudent = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { studentRepositoryMock.findById(1) }
        assertEquals(students[0].firstName, savedStudent?.firstName)
        assertEquals(students[0].lastName, savedStudent?.lastName)
        assertEquals(students[0].mail, savedStudent?.mail)
        assertEquals(students[0].courses, savedStudent?.courses)
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `findById() - should throw exception`() {
        // given/arrange (define data/ create state):
        every { studentRepositoryMock.findByIdOrNull(6) } returns null
        // when/act (key action of system):
        val catchedException = catchThrowable { service.findById(6) }
        //  then/assert (observe and check systems output):
        verify { studentRepositoryMock.findByIdOrNull(6) }
        assertThat(catchedException)
            .isInstanceOf(NoElementUnderThisIdException::class.java)
            .hasMessageContaining("No studentelement with Id 6 found")
    }

    @Test
    fun `findAll() - should return studentList`() {
        // given/arrange (define data/ create state):
        every { studentRepositoryMock.findAll() } returns students
        // when/act (key action of system):
        val response = service.findAll()
        val savedStudents = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { studentRepositoryMock.findAll() }
        assertArrayEquals(students.toTypedArray(), savedStudents?.toTypedArray())
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `update() - should return student`() {
        // given/arrange (define data/ create state):
        val changedStudentValues = StudentEntity(students[0].studentId, "Max", "Doe", "MaDo@campus.com", mutableSetOf())
        every { studentRepositoryMock.save(changedStudentValues) } returns changedStudentValues
        // when/act (key action of system):
        val response = service.update(changedStudentValues)
        val savedStudent = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        assertEquals(changedStudentValues.firstName, savedStudent?.firstName)
        assertEquals(changedStudentValues.lastName, savedStudent?.lastName)
        assertEquals(changedStudentValues.mail, savedStudent?.mail)
        assertEquals(changedStudentValues.courses, savedStudent?.courses)
        assertEquals(HttpStatus.RESET_CONTENT, status)
    }

    @Test
    fun `deleteById() - should return student list`() {
        // given/arrange (define data/ create state):
        val filteredStudentList = students.filter { it.studentId != 1L }
        every { studentRepositoryMock.deleteById(1) } returns Unit
        every { studentRepositoryMock.findAll() } returns filteredStudentList
        // when/act (key action of system):
        val response = service.deleteByID(1)
        val savedStudents = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { studentRepositoryMock.deleteById(1) }
        verify { studentRepositoryMock.findAll() }
        assertArrayEquals(filteredStudentList.toTypedArray(), savedStudents?.toTypedArray())
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `deleteAll() - should return empty student list`() {
        // given/arrange (define data/ create state):
        val filteredStudentList = mutableListOf<StudentEntity>()
        every { studentRepositoryMock.deleteAll() } returns Unit
        every { studentRepositoryMock.findAll() } returns filteredStudentList
        // when/act (key action of system):
        val response = service.deleteAll()
        val savedStudents = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { studentRepositoryMock.deleteAll() }
        verify { studentRepositoryMock.findAll() }
        assertArrayEquals(filteredStudentList.toTypedArray(), savedStudents?.toTypedArray())
        assertEquals(HttpStatus.OK, status)
    }
}
