package by.ld1995.coursework.services

import by.ld1995.coursework.configurations.security.UserPrincipal
import by.ld1995.coursework.dto.user.Profile
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.exception.UserNotFoundException
import by.ld1995.coursework.repositories.UserRepository
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsersBesidesSelf(currentUser: UserPrincipal): List<UserSummary> =
            userRepository.findAll()
                    .filter { user -> user.id != currentUser.getId() }
                    .map { user -> UserSummary(user.id, user.username, user.fullName) }
                    .toList()

    fun existsByUsername(username: String): Boolean = userRepository.existsByUsername(username)

    fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)

    fun getProfile(currentUser: Principal) = userRepository.findByUsername(currentUser.name)
            .map { user -> Profile(user.username, user.fullName, user.phoneNumber, user.email, "") }
            .orElseThrow { UserNotFoundException(currentUser) }

}