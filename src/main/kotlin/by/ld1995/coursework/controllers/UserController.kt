package by.ld1995.coursework.controllers

import by.ld1995.coursework.models.User
import by.ld1995.coursework.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @PostMapping("/users")
    fun createUser(@Valid @RequestBody user: User): User = userService.createUser(user)

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable(value = "id") id: Long) : ResponseEntity<User> = userService.getUserById(id)
}