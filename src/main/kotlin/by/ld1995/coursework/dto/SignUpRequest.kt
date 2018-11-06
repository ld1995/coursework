package by.ld1995.coursework.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    val username: String = ""

    @NotBlank
    @Size(min = 4, max = 100)
    val fullName: String = ""

    @NotBlank
    @Size(min = 9, max = 9)
    val phoneNumber: String = ""

    @NotBlank
    @Size(max = 40)
    @Email
    val email: String = ""

    @NotBlank
    @Size(min = 6, max = 20)
    val password: String = ""
}