package by.ld1995.coursework.services

import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.repositories.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserByUsername(username : String) = userRepository.findByUsername(username)

    fun getUserById(id: Long): ResponseEntity<User> = userRepository.findById(id)
            .map { user -> ResponseEntity.ok(user) }
            .orElse(ResponseEntity.notFound().build())

    fun existsByUsername(username: String) : Boolean = userRepository.existsByUsername(username)

    fun existsByEmail(email: String) : Boolean = userRepository.existsByEmail(email)

}