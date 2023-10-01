package scrm.backend.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import scrm.backend.backend.model.entity.CourseEntity

@Repository
interface CourseRepository : JpaRepository<CourseEntity, Long>