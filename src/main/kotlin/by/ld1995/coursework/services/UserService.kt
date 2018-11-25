package by.ld1995.coursework.services

import by.ld1995.coursework.configurations.security.UserPrincipal
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.exception.UserNotFoundException
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.models.user.VerificationToken
import by.ld1995.coursework.repositories.UserRepository
import by.ld1995.coursework.repositories.VerificationTokenRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository,
                  private val tokenRepository: VerificationTokenRepository) {

    fun getAllUsersBesidesSelf(currentUser: UserPrincipal): List<UserSummary> =
            userRepository.findAll()
                    .filter { user -> user.id != currentUser.getId() }
                    .map { user -> UserSummary(user.id, user.username, user.fullName) }
                    .toList()

    fun existsByUsername(username: String): Boolean = userRepository.existsByUsername(username)

    fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)

    fun isActiveUser(id: Long): Boolean = this.findUserById(id).active

    fun save(user: User): User = userRepository.save(user)

    fun findUserById(id: Long): User =
            userRepository.findById(id).orElseThrow { UserNotFoundException(id) }

    fun findUserByUsername(username: String): User =
            userRepository.findByUsername(username).orElseThrow { UserNotFoundException(username) }

    fun findAllById(ids: List<Long>): MutableList<User> =
            userRepository.findAllById(ids)

    fun createVerificationToken(user: User, token: String): VerificationToken =
            tokenRepository.save(VerificationToken(token, user))

    fun getVerificationToken(token: String): VerificationToken? =
            tokenRepository.findByToken(token)
}