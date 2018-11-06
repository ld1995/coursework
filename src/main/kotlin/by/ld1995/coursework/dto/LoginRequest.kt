package by.ld1995.coursework.dto

import javax.validation.constraints.NotBlank

class LoginRequest {

    @NotBlank
    val usernameOrEmail: String = ""

    @NotBlank
    val password: String = ""
}