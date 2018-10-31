package by.ld1995.coursework.repositories

import by.ld1995.coursework.models.user.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long>