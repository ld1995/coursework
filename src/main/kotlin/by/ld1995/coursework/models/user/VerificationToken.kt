package by.ld1995.coursework.models.user

import by.ld1995.coursework.models.PersistentObject
import java.time.Duration
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "coursework_verification_user")
data class VerificationToken(

        val token: String,

        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "user_id")
        val user: User,

        val expiryDate: Instant = Instant.now().plus(Duration.ofDays(1))

) : PersistentObject()
