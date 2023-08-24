package com.scrm.backend.services

import com.scrm.backend.exceptionhandling.exceptions.NoElementUnderThisIdException
import com.scrm.backend.model.entity.CourseEntity
import com.scrm.backend.repository.CourseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals


class CourseServiceUnitTests {

    private val courseRepositoryMock = mockk<CourseRepository>(relaxed = true)

    private val service = CourseService(courseRepositoryMock)

    private val courses = mutableListOf<CourseEntity>(
        CourseEntity(1,"Programmieren 1", mutableSetOf()),
        CourseEntity(2,"Programmieren 2", mutableSetOf()),
        CourseEntity(3,"Softwareprojekt 1", mutableSetOf()),
        CourseEntity(4,"Mathematik", mutableSetOf()),
        CourseEntity(5,"Statistik", mutableSetOf()),
    )

    @Test
    fun `save() - should return Course entity`() {
        // given/arrange (define data/ create state):
        every { courseRepositoryMock.save(courses[0]) } returns courses[0]
        // when/act (key action of system):
        val response = service.save(courses[0])
        val savedCourse = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { courseRepositoryMock.save(courses[0]) }
        assertEquals(courses[0].courseName, savedCourse?.courseName)
        assertEquals(HttpStatus.CREATED, status)
    }

    @Test
    fun `findById() - should return Course entity`() {
        // given/arrange (define data/ create state):
        every { courseRepositoryMock.findByIdOrNull(1) } returns courses[0]
        // when/act (key action of system):
        val response = service.findById(1)
        val savedCourse = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { courseRepositoryMock.findById(1) }
        assertEquals(courses[0].courseName, savedCourse?.courseName)
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `findById() - should throw exception`() {
        // given/arrange (define data/ create state):
        every { courseRepositoryMock.findByIdOrNull(6) } returns null
        // when/act (key action of system):
        val catchedException = org.assertj.core.api.Assertions.catchThrowable { service.findById(6) }
        //  then/assert (observe and check systems output):
        verify { courseRepositoryMock.findByIdOrNull(6) }
        assertThat(catchedException)
            .isInstanceOf(NoElementUnderThisIdException::class.java)
            .hasMessageContaining("No courseelement with Id 6 found")
    }

    @Test
    fun `findAll() - should return courseList`() {
        // given/arrange (define data/ create state):
        every { courseRepositoryMock.findAll() } returns courses
        // when/act (key action of system):
        val response = service.findAll()
        val savedCourses = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { courseRepositoryMock.findAll() }
        assertArrayEquals(courses.toTypedArray(), savedCourses?.toTypedArray())
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `update() - should return course`() {
        // given/arrange (define data/ create state):
        val changedStudentValues = CourseEntity(courses[0].courseId,"Einführung Programmieren", mutableSetOf())
        every { courseRepositoryMock.save(changedStudentValues) } returns changedStudentValues
        // when/act (key action of system):
        val response = service.update(changedStudentValues)
        val savedCourse = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        assertEquals(changedStudentValues.courseName, savedCourse?.courseName)
        assertEquals(HttpStatus.RESET_CONTENT, status)
    }

    @Test
    fun `deleteById() - should return course list`() {
        // given/arrange (define data/ create state):
        val filteredCourseList = courses.filter { it.courseId != 1L }
        every { courseRepositoryMock.deleteById(1) } returns Unit
        every { courseRepositoryMock.findAll() } returns filteredCourseList
        // when/act (key action of system):
        val response = service.deleteById(1)
        val savedCourses = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { courseRepositoryMock.deleteById(1) }
        verify { courseRepositoryMock.findAll() }
        assertArrayEquals(filteredCourseList.toTypedArray(), savedCourses?.toTypedArray())
        assertEquals(HttpStatus.OK, status)
    }

    @Test
    fun `deleteAll() - should return empty course list`() {
        // given/arrange (define data/ create state):
        val filteredCourseList = mutableListOf<CourseEntity>()
        every { courseRepositoryMock.deleteAll() } returns Unit
        every { courseRepositoryMock.findAll() } returns filteredCourseList
        // when/act (key action of system):
        val response = service.deleteAll()
        val savedCourses = response.body
        val status = response.statusCode
        //  then/assert (observe and check systems output):
        verify { courseRepositoryMock.deleteAll() }
        verify { courseRepositoryMock.findAll() }
        assertArrayEquals(filteredCourseList.toTypedArray(), savedCourses?.toTypedArray())
        assertEquals(HttpStatus.OK, status)
    }
}