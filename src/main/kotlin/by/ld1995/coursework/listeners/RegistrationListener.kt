package by.ld1995.coursework.listeners

import by.ld1995.coursework.events.OnRegistrationCompleteEvent
import by.ld1995.coursework.models.user.User
import by.ld1995.coursework.services.UserService
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegistrationListener(private val userService: UserService,
                           private val mailSender: JavaMailSender) : ApplicationListener<OnRegistrationCompleteEvent> {

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        this.confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: User = event.user
        val token: String = UUID.randomUUID().toString()
        userService.createVerificationToken(user, token)

        val subject = "Registration Confirmation"
        val confirmationUrl = "?token=$token"

        val email = SimpleMailMessage()
        email.setTo(user.email)
        email.setSubject(subject)
        email.setText("Your token http://coursework.us-east-2.elasticbeanstalk.com/$confirmationUrl")
        mailSender.send(email)
    }
}