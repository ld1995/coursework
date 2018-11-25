package by.ld1995.coursework.controllers

import by.ld1995.coursework.configurations.security.CurrentUser
import by.ld1995.coursework.configurations.security.UserPrincipal
import by.ld1995.coursework.dto.user.Profile
import by.ld1995.coursework.dto.user.UserIdentityAvailability
import by.ld1995.coursework.dto.user.UserSummary
import by.ld1995.coursework.services.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/user/me")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): UserSummary {
        return UserSummary(currentUser.getId(), currentUser.username, currentUser.getFullName())
    }

    @GetMapping("/users")
    fun getAllUsers(@CurrentUser currentUser: UserPrincipal): List<UserSummary> =
            userService.getAllUsersBesidesSelf(currentUser)

    @GetMapping("/user/checkUsernameAvailability")
    fun checkUsernameAvailability(@RequestParam(value = "username") username: String) : UserIdentityAvailability {
        val isAvailable = !userService.existsByUsername(username)
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/user/checkEmailAvailability")
    fun checkEmailAvailability(@RequestParam(value = "email") email: String) : UserIdentityAvailability {
        val isAvailable = !userService.existsByEmail(email)
        return UserIdentityAvailability(isAvailable)
    }

    @GetMapping("/user/profile")
    fun getUserProfile(currentUser: Principal): Profile {
        val user = userService.findUserByUsername(currentUser.name)
        return Profile(user.username, user.fullName, user.phoneNumber, user.email, "")
    }
}