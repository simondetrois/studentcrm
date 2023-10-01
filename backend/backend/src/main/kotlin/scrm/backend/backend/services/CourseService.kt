package scrm.backend.backend.services

import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import scrm.backend.backend.exceptionhandling.exceptions.NoElementUnderThisIdException
import scrm.backend.backend.model.dto.CourseDto
import scrm.backend.backend.repository.CourseRepository
import org.springframework.data.domain.Sort

@Service
class CourseService (private val courseRepository: CourseRepository) {

    fun save(courseDTO: CourseDto) = courseRepository.save(courseDTO.toDomainModel()).toResponseDto()


    fun findById(id: Long) = courseRepository.findByIdOrNull(id)?.toResponseDto()
        ?: throw NoElementUnderThisIdException("No courseelement with Id $id found")


    fun findAllPaginatedCoursesSortedAscending(
        field: String,
        offset: Int,
        pageSize: Int
    ) = courseRepository.findAll(
        PageRequest.of(offset, pageSize)
            .withSort(Sort.by(Sort.Direction.ASC, field))
    ).map { it.toResponseDto() }.toMutableList()


    fun findAllPaginatedCoursesSortedDescending(
        field: String,
        offset: Int,
        pageSize: Int
    ) =
        courseRepository.findAll(
            PageRequest.of(offset, pageSize)
                .withSort(Sort.by(Sort.Direction.DESC, field))
        ).toMutableList()


    fun update(courseDTO: CourseDto) = courseRepository.save(courseDTO.toDomainModel()).toResponseDto()


    fun deleteById(id: Long) = courseRepository.deleteById(id)

    fun deleteAll() = courseRepository.deleteAll()

}