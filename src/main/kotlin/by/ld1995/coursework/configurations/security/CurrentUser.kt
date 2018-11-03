package by.ld1995.coursework.configurations.security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.Documented

@Target(AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Documented
@AuthenticationPrincipal
annotation class CurrentUser