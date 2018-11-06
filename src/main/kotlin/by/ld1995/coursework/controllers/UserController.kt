package by.ld1995.coursework.controllers

import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable(value = "username") username: String) = userService.getUserByUsername(username)

    @GetMapping("/{id}")
    fun getUserById(@PathVariable(value = "id") id: Long) : ResponseEntity<User> = userService.getUserById(id)
}