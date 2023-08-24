package com.scrm.backend.controller

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.* // ktlint-disable no-wildcard-imports
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.* // ktlint-disable no-wildcard-imports
import org.springframework.web.bind.MethodArgumentNotValidException
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.validation.ConstraintViolationException

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ControllerIntegrationstests @Autowired constructor(
    var mockMvc: MockMvc
) {

    companion object {
        @Container var postgresSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15.1") // version nachschaeuen
            .withDatabaseName("integration_test_db")
            .withUsername("integrationtester")
            .withPassword("integration")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgresSQLContainer::getPassword)
            registry.add("spring.datasource.username", postgresSQLContainer::getUsername)
        }
    }

    @Nested
    @DisplayName("/student")
    inner class StudentIntegrationTests {

        @AfterEach
        fun deleteDatabaseEntries() {
            mockMvc.delete("/student/deleteAllStudents")
        }

        @Nested

        inner class AddStudent {
            @Test
            fun `valid student - should return student`() { // nicht getestet wird hier die db
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                // when/act (key action of system):
                val response = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                //  then/assert (observe and check systems output):
                response.andExpect {
                    status { isCreated() } // isCreated => HTTP StatusCode 200
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.firstName") { value("Jon") }
                    jsonPath("$.lastName") { value("Doe") }
                    jsonPath("$.mail") { value("JoDo@campus.com") }
                }
            }

            @Test
            fun `firstName length is 0 - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["firstName length must be between 2 and 20 characters"]}""") }
                    }
                    .andReturn().resolvedException

                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `firstName is null - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["firstName property must be set"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `lastName length is 0 - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"","mail":"JoDo@campus.com","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["lastName length must be between 2 and 20 characters"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `lastName is null - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","mail":"JoDo@campus.com","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["lastName property must be set"]}""") }
                    }
                    .andReturn().resolvedException

                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `mail length is 0 - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["mail length must be between 15 and 16 characters","mail format is invalid; valid formats ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@campus.com"]}""".trimMargin()) }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `mail is null - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["mail property must be set"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `mail format is invalid - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.de","courses":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["mail format is invalid; valid formats ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@campus.com","mail length must be between 15 and 16 characters"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `courses is null - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com"}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["courses Property must be set"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }
        }

        @Nested
        @DisplayName("/getStudentById")
        inner class GetStudentById {
            @Test
            fun `valid id - should return student`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                val id = JSONObject(
                    mockMvc.post("/student/addStudent") {
                        contentType = MediaType.APPLICATION_JSON
                        content = studentJson
                    }.andReturn().response.contentAsString
                ).getString("studentId")

                // when/act (key action of system):
                mockMvc.get("/student/getStudentById?id=$id")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isOk() } // isCreated => HTTP StatusCode 200
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.studentId") { value(id) }
                        jsonPath("$.firstName") { value("Jon") }
                        jsonPath("$.lastName") { value("Doe") }
                        jsonPath("$.mail") { value("JoDo@campus.com") }
                    }
            }

            @Test
            fun `invalid id - should throw ConstraintViolationException`() {
                // given/arrange (define data/ create state): list of students defined above
                // when/act (key action of system):
                val exception = mockMvc.get("/student/getStudentById?id=0")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isBadRequest() }
                    }.andReturn().resolvedException
                assertThat(exception).isInstanceOf(ConstraintViolationException::class.java)
                    .hasMessageContaining("getStudent.id: must be greater than 0")
            }
        }

        @Nested
        @DisplayName("/getAllStudents")
        inner class GetAllStudents {
            @Test
            fun `should return all students`() {
                // given/arrange (define data/ create state): list of students defined above
                val responseJon = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                }
                val responseMax = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"firstName":"Max","lastName":"Mustermann","mail":"MaMu@campus.com","courses":[]}"""
                }

                mockMvc.get("/student/getAllStudents")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].firstName") { value("Jon") }
                        jsonPath("$[0].lastName") { value("Doe") }
                        jsonPath("$[0].mail") { value("JoDo@campus.com") }
                        jsonPath("$[1].firstName") { value("Max") }
                        jsonPath("$[1].lastName") { value("Mustermann") }
                        jsonPath("$[1].mail") { value("MaMu@campus.com") }
                    }
            }
        }

        @Nested
        @DisplayName("/updateStudent")
        inner class UpdateStudent {
            @Test
            fun `should return updated student`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                val id = JSONObject(
                    mockMvc.post("/student/addStudent") {
                        contentType = MediaType.APPLICATION_JSON
                        content = studentJson
                    }.andReturn().response.contentAsString
                ).getString("studentId")
                val studentJsonEdited =
                    """{"studentId":$id,"firstName":"Max","lastName":"Mustermann","mail":"MaMu@campus.com","courses":[]}"""
                // when/act (key action of system):
                val response = mockMvc.put("/student/updateStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJsonEdited
                }
                //  then/assert (observe and check systems output):
                response.andExpect {
                    status { isResetContent() } // isCreated => HTTP StatusCode 200
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.studentId") { value(id) }
                    jsonPath("$.firstName") { value("Max") }
                    jsonPath("$.lastName") { value("Mustermann") }
                    jsonPath("$.mail") { value("MaMu@campus.com") }
                }
            }
        }

        @Nested
        @DisplayName("/deleteStudentById")
        inner class DeleteStudentById {
            @Test
            fun `should return empty student list`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                val id = JSONObject(
                    mockMvc.post("/student/addStudent") {
                        contentType = MediaType.APPLICATION_JSON
                        content = studentJson
                    }.andReturn().response.contentAsString
                ).getString("studentId")

                mockMvc.get("/student/getAllStudents")
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.size()") { value(1) }
                    }
                // when/act (key action of system):
                val response = mockMvc.delete("/student/deleteStudentById?id=$id") {
                    contentType = MediaType.APPLICATION_JSON
                }
                //  then/assert (observe and check systems output):
                response.andExpect {
                    status { isOk() } // isCreated => HTTP StatusCode 200
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.size()") { value(0) }
                }
            }

            @Test
            fun `invalid id - should throw ConstraintViolationException`() {
                // given/arrange (define data/ create state): list of students defined above
                // when/act (key action of system):
                val exception = mockMvc.delete("/student/deleteStudentById?id=0")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isBadRequest() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                    }.andReturn().resolvedException
                assertThat(exception).isInstanceOf(ConstraintViolationException::class.java)
                    .hasMessageContaining("deleteStudentByID.id: must be greater than 0")
            }
        }

        @Nested
        @DisplayName("/deleteAllStudents")
        inner class DeleteAllStudents {
            @Test
            fun `should return empty student list`() {
                // given/arrange (define data/ create state): list of students defined above
                val responseJon = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"firstName":"Jon","lastName":"Doe","mail":"JoDo@campus.com","courses":[]}"""
                }
                val responseMax = mockMvc.post("/student/addStudent") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"firstName":"Max","lastName":"Mustermann","mail":"MaMu@campus.com","courses":[]}"""
                }

                mockMvc.get("/student/getAllStudents")
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.size()") { value(2) }
                    }

                // when/act (key action of system):
                mockMvc.delete("/student/deleteAllStudents")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.size()") { value(0) }
                    }
            }
        }
    }

    @Nested
    @DisplayName("/course")
    inner class CourseIntegrationTests {
        @AfterEach
        fun deleteDatabaseEntries() {
            mockMvc.delete("/course/deleteAllCourses")
        }

        @Nested
        @DisplayName("/addCourse")
        inner class AddCouse {
            @Test
            fun `valid course - should return course`() {
                // given/arrange (define data/ create state): list of students defined above
                val courseJson = """{"courseName":"Programmieren","students":[]}"""
                // when/act (key action of system):
                val response = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = courseJson
                }
                //  then/assert (observe and check systems output):
                response.andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.courseName") { value("Programmieren") }
                }
            }

            @Test
            fun `courseName length is 0 - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val courseJson = """{"courseName":"","students":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = courseJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["courseName length must be between 2 and 35 characters"]}""") }
                    }
                    .andReturn().resolvedException

                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `courseName is null - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val courseJson = """{"students":[]}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = courseJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["courseName property must be set"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }

            @Test
            fun `students is null - should throw MethodArgumentNotValidException`() {
                // given/arrange (define data/ create state): list of students defined above
                val courseJson = """{"courseName":"Programmieren"}"""
                // when/act (key action of system):
                val exception = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = courseJson
                }
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isUnprocessableEntity() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        content { json("""{"errors":["students property must be set"]}""") }
                    }
                    .andReturn().resolvedException
                assertThat(exception).isInstanceOf(MethodArgumentNotValidException::class.java)
            }
        }

        @Nested
        @DisplayName("/getCourseById")
        inner class GetCourseById {
            @Test
            fun `valid id - should return course`() {
                // given/arrange (define data/ create state): list of students defined above
                val courseJson = """{"courseName":"Programmieren","students":[]}"""
                val id = JSONObject(
                    mockMvc.post("/course/addCourse") {
                        contentType = MediaType.APPLICATION_JSON
                        content = courseJson
                    }.andReturn().response.contentAsString
                ).getString("courseId")

                // when/act (key action of system):
                mockMvc.get("/course/getCourseById?id=$id")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isOk() } // isCreated => HTTP StatusCode 200
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.courseId") { value(id) }
                        jsonPath("$.courseName") { value("Programmieren") }
                    }
            }

            @Test
            fun `invalid id - should throw ConstraintViolationException`() {
                // given/arrange (define data/ create state): list of students defined above
                // when/act (key action of system):
                val exception = mockMvc.get("/course/getCourseById?id=0")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isBadRequest() }
                    }.andReturn().resolvedException
                assertThat(exception).isInstanceOf(ConstraintViolationException::class.java)
                    .hasMessageContaining("getCourse.id: must be greater than 0")
            }
        }

        @Nested
        @DisplayName("/getAllCourses")
        inner class GetAllCourses {
            @Test
            fun `should return all courses`() {
                // given/arrange (define data/ create state): list of students defined above
                val responseProgrammieren1 = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"courseName":"Programmieren 1","students":[]}"""
                }
                val responseProgrammieren2 = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"courseName":"Programmieren 2","students":[]}"""
                }

                mockMvc.get("/course/getAllCourses")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$[0].courseName") { value("Programmieren 1") }
                        jsonPath("$[1].courseName") { value("Programmieren 2") }
                    }
            }
        }

        @Nested
        @DisplayName("/updateCourse")
        inner class UpdateCourse {
            @Test
            fun `should return updated course`() {
                // given/arrange (define data/ create state): list of students defined above
                val studentJson = """{"courseName":"Programmieren 1","students":[]}"""
                val id = JSONObject(
                    mockMvc.post("/course/addCourse") {
                        contentType = MediaType.APPLICATION_JSON
                        content = studentJson
                    }.andReturn().response.contentAsString
                ).getString("courseId")
                val studentJsonEdited = """{"courseId":$id,"courseName":"Programmieren 2","students":[]}"""
                // when/act (key action of system):
                val response = mockMvc.put("/course/updateCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = studentJsonEdited
                }
                //  then/assert (observe and check systems output):
                response.andExpect {
                    status { isResetContent() } // isCreated => HTTP StatusCode 200
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.courseId") { value(id) }
                    jsonPath("$.courseName") { value("Programmieren 2") }
                }
            }
        }

        @Nested
        @DisplayName("/deleteCourseById")
        inner class DeleteCourseById {
            @Test
            fun `should return empty course list`() {
                // given/arrange (define data/ create state): list of students defined above
                val courseJson = """{"courseName":"Programmieren","students":[]}"""
                val id = JSONObject(
                    mockMvc.post("/course/addCourse") {
                        contentType = MediaType.APPLICATION_JSON
                        content = courseJson
                    }.andReturn().response.contentAsString
                ).getString("courseId")

                mockMvc.get("/course/getAllCourses")
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.size()") { value(1) }
                    }
                // when/act (key action of system):
                val response = mockMvc.delete("/course/deleteCourseById?id=$id") {
                    contentType = MediaType.APPLICATION_JSON
                }
                //  then/assert (observe and check systems output):
                response.andExpect {
                    status { isOk() } // isCreated => HTTP StatusCode 200
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.size()") { value(0) }
                }
            }

            @Test
            fun `invalid id - should throw ConstraintViolationException`() {
                // given/arrange (define data/ create state): list of students defined above
                // when/act (key action of system):
                val exception = mockMvc.delete("/course/deleteCourseById?id=0")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isBadRequest() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                    }.andReturn().resolvedException
                assertThat(exception).isInstanceOf(ConstraintViolationException::class.java)
                    .hasMessageContaining("deleteCourseByID.id: must be greater than 0")
            }
        }

        @Nested
        @DisplayName("/deleteAllCourses")
        inner class DeleteAllCourses {
            @Test
            fun `should return empty course list`() {
                // given/arrange (define data/ create state): list of students defined above
                val responseProgrammieren1 = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"courseName":"Programmieren 1","students":[]}"""
                }
                val responseProgrammieren2 = mockMvc.post("/course/addCourse") {
                    contentType = MediaType.APPLICATION_JSON
                    content = """{"courseName":"Programmieren 2","students":[]}"""
                }

                mockMvc.get("/course/getAllCourses")
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.size()") { value(2) }
                    }

                // when/act (key action of system):
                mockMvc.delete("/course/deleteAllCourses")
                    //  then/assert (observe and check systems output):
                    .andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                        jsonPath("$.size()") { value(0) }
                    }
            }
        }
    }
}
