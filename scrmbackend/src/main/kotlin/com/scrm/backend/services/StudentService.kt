package com.scrm.backend.services

import com.scrm.backend.exceptionhandling.exceptions.NoElementUnderThisIdException
import com.scrm.backend.controller.dto.StudentDto
import com.scrm.backend.model.entity.StudentEntity
import com.scrm.backend.repository.StudentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {

    fun save(studentDTO: StudentDto) = studentRepository.save(studentDTO.toDomainModel()).toResponseDto()


    fun findById(id: Long) = studentRepository.findByIdOrNull(id)?.toResponseDto()
            ?: throw NoElementUnderThisIdException("No studentelement with Id $id found")


    fun findPaginatedStudentsSortedAscending(field: String, offset: Int, pageSize: Int) = studentRepository.findAll(
            PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.ASC, field))
        ).map { it.toResponseDto() }.toMutableList()


    fun findAllPaginatedStudentsSortedDescending(field: String, offset: Int, pageSize: Int) =
        studentRepository.findAll(
            PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.DESC, field))
        ).toMutableList()


    fun update(studentDTO: StudentDto): StudentDto{

      return   studentRepository.save(studentDTO.toDomainModel()).toResponseDto()
    }



    fun deleteByID(id: Long) = studentRepository.deleteById(id)

    fun deleteAll() = studentRepository.deleteAll()

}
