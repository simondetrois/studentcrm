package scrm.backend.backend.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.SequenceGenerator
import scrm.backend.backend.model.dto.StudentDto

@Entity(name = "student")
class StudentEntity
    (
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_generator")
    @SequenceGenerator(name = "student_generator", sequenceName = "id_sequence_of_students", allocationSize = 1)

    @Column(name = "student_id", updatable = false)
    val studentId: Long,

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    // notblank
    @Column(name = "last_name", nullable = false)
    val lastName: String, // Allem was validiert wird nen Wrapper geben -> Validierung rausziehen (single responsibility)

    @Column(name = "e_mail", nullable = false, unique = true)
    val mail: String, // Wrapper

    @Column(name = "subject", nullable = false)
    val subject: String,

    @ManyToMany(
        cascade = [CascadeType.PERSIST]
    )
    @JoinTable(
        name = "join_student_course",
        joinColumns = [JoinColumn(name = "student_id", referencedColumnName = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "course_id", referencedColumnName = "course_id")]
    )

    var courses: MutableSet<CourseEntity>
) {
    fun toResponseDto(): StudentDto {
        return StudentDto(
            this.studentId,
            this.firstName,
            this.lastName,
            this.mail,
            this.subject,
            this.courses.map { it.toResponseDto() }.toMutableSet()
        )
    }
}