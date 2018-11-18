package by.ld1995.coursework.exception

import java.security.Principal

class UserNotFoundException(principal: Principal) :
        RuntimeException("${principal.name} not found")