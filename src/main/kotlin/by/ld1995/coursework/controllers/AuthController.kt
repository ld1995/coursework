package by.ld1995.coursework.controllers

import by.ld1995.coursework.configurations.security.JwtTokenProvider
import by.ld1995.coursework.configurations.security.UserPrincipal
import by.ld1995.coursework.dto.ApiResponse
import by.ld1995.coursework.dto.JwtAuthenticationResponse
import by.ld1995.coursework.dto.LoginRequest
import by.ld1995.coursework.dto.SignUpRequest
import by.ld1995.coursework.events.OnRegistrationCompleteEvent
import by.ld1995.coursework.exception.AppException
import by.ld1995.coursework.models.user.Role
import by.ld1995.coursework.models.user.RoleName
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.models.user.VerificationToken
import by.ld1995.coursework.repositories.RoleRepository
import by.ld1995.coursework.services.UserService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authenticationManager: AuthenticationManager,
                     private val userService: UserService,
                     private val roleRepository: RoleRepository,
                     private val passwordEncoder: PasswordEncoder,
                     private val tokenProvider: JwtTokenProvider,
                     private val eventPublisher: ApplicationEventPublisher) {

    private val _logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.usernameOrEmail, loginRequest.password)
        )
        val user = authentication.principal as UserPrincipal
        if (!userService.isActiveUser(user.getId())) {
            return ResponseEntity(
                    ApiResponse(false, "Go to the email address specified" +
                            " during registration and click on the links to activate your account!"),
                    HttpStatus.BAD_REQUEST)
        }

        SecurityContextHolder.getContext().authentication = authentication

        val jwt = tokenProvider.generateToken(authentication)
        return ResponseEntity.ok(JwtAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<Any> {
        if (userService.existsByUsername(signUpRequest.username))
            return ResponseEntity(
                    ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)

        if (userService.existsByEmail(signUpRequest.email))
            return ResponseEntity(
                    ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)

        val userRole: Role = roleRepository.findByName(RoleName.ROLE_STUDENT.name)
                .orElseThrow { AppException("User Role not set.") }

        val password: String = passwordEncoder.encode(signUpRequest.password)

        val user = User(signUpRequest.username, signUpRequest.fullName, signUpRequest.phoneNumber,
                signUpRequest.email, password, false, Collections.singleton(userRole))

        userService.save(user)

        try {
            eventPublisher.publishEvent(OnRegistrationCompleteEvent(user))
        } catch (error: Exception) {
            _logger.error(error.message, error)
            return ResponseEntity(
                    ApiResponse(false, "Error of sending email with verification code!"),
                    HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity.ok(ApiResponse(true, "User registered successfully!"))
    }

    @GetMapping("/registrationConfirm")
    fun confirmRegistration(@RequestParam("token") token: String): ResponseEntity<Any> {
        val verificationToken: VerificationToken = userService.getVerificationToken(token)
                ?: return ResponseEntity(
                        ApiResponse(false, "Wrong token!"), HttpStatus.BAD_REQUEST)
        val user: User = verificationToken.user
        if (verificationToken.expiryDate.isAfter(Instant.now()))
            return ResponseEntity(
                    ApiResponse(false, "Outdated token!"),
                    HttpStatus.BAD_REQUEST)
        user.active = true
        userService.save(user)
        return ResponseEntity.ok(ApiResponse(true, "Account successfully activated!"))
    }
}