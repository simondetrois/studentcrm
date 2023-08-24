package com.scrm.backend.repository

import com.scrm.backend.model.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository // Responsible for retrieving and storing Data
interface CourseRepository : JpaRepository<CourseEntity, Long> {
    @Query("select course from Course course where course.courseId IN (:courseIds)")
    fun getCoursesByIds(courseIds: MutableSet<Long>): List<CourseEntity>
}
