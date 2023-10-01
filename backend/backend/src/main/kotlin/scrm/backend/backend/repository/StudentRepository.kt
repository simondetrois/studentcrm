package scrm.backend.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import scrm.backend.backend.model.entity.StudentEntity

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long>