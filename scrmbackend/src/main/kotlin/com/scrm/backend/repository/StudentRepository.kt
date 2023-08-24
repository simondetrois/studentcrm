package com.scrm.backend.repository

import com.scrm.backend.model.entity.StudentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository // Responsible for retrieving and storing Data
interface StudentRepository : JpaRepository<StudentEntity, Long>
