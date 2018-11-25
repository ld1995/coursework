package by.ld1995.coursework.repositories

import by.ld1995.coursework.models.user.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository

interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {

    fun findByToken(token: String): VerificationToken
}