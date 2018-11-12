package by.ld1995.coursework.repositories

import by.ld1995.coursework.models.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    fun findByName(name: String): Optional<Group>
}