package by.ld1995.coursework.controllers

import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.services.UserService
import org.springframework.web.bind.annotation.*
import by.ld1995.coursework.configurations.security.CurrentUser
import by.ld1995.coursework.configurations.security.UserPrincipal
import by.ld1995.coursework.dto.user.UserIdentityAvailability
import by.ld1995.coursework.dto.user.UserSummary
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/user/me")
    fun getCurrentUser(@CurrentUser currentUser: UserPrincipal): UserSummary {
        return UserSummary(currentUser.getId(), currentUser.username, currentUser.getFullName())
    }

    @GetMapping("/users")
    fun getAllUsers(): List<User> = userService.getAllUsers()

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

    @GetMapping("/users/{username}")
    fun getUserProfile(@PathVariable(value = "username")username: String) {

    }
    //    @GetMapping("/users/{username}")
//    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
//
//        long pollCount = pollRepository.countByCreatedBy(user.getId());
//        long voteCount = voteRepository.countByUserId(user.getId());
//
//        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), pollCount, voteCount);
//
//        return userProfile;
//    }
}