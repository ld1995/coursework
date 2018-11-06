package by.ld1995.coursework.controllers

import by.ld1995.coursework.configurations.security.JwtTokenProvider
import by.ld1995.coursework.dto.ApiResponse
import by.ld1995.coursework.dto.JwtAuthenticationResponse
import by.ld1995.coursework.dto.LoginRequest
import by.ld1995.coursework.dto.SignUpRequest
import by.ld1995.coursework.exception.AppException
import by.ld1995.coursework.models.user.RoleName
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.repositories.RoleRepository
import by.ld1995.coursework.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authenticationManager: AuthenticationManager,
                     private val userRepository: UserRepository,
                     private val roleRepository: RoleRepository,
                     private val passwordEncoder: PasswordEncoder,
                     private val tokenProvider: JwtTokenProvider) {

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.usernameOrEmail, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Any> {
        if (userRepository.existsByUsername(signUpRequest.username))
            return ResponseEntity(
                    ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)

        if (userRepository.existsByEmail(signUpRequest.email))
            return ResponseEntity(
                    ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)

        val userRole = roleRepository.findByName(RoleName.ROLE_STUDENT.name)
                .orElseThrow { AppException("User Role not set.") }

        val password = passwordEncoder.encode(signUpRequest.password)

        val user = User(signUpRequest.username, signUpRequest.fullName, signUpRequest.phoneNumber,
                signUpRequest.email, password, 0, Collections.singleton(userRole))

        userRepository.save(user)

        return ResponseEntity.ok(ApiResponse(true, "User registered successfully"))
    }
}