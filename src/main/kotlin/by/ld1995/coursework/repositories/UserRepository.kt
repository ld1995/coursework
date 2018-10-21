package by.ld1995.coursework.repositories

import by.ld1995.coursework.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>