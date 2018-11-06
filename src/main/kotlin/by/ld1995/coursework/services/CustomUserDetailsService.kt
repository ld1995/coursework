package by.ld1995.coursework.services

import by.ld1995.coursework.configurations.security.UserPrincipal
import by.ld1995.coursework.exception.ResourceNotFoundException
import by.ld1995.coursework.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        val user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow { UsernameNotFoundException("User not found with username or email : $usernameOrEmail") }
        return UserPrincipal.create(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id)
                .orElseThrow { UsernameNotFoundException("User not found with id : $id") }
        return UserPrincipal.create(user)
    }
}