package by.ld1995.coursework.events

import by.ld1995.coursework.models.user.User
import org.springframework.context.ApplicationEvent

data class OnRegistrationCompleteEvent(val user: User) : ApplicationEvent(user)