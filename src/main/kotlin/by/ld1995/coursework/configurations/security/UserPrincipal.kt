package by.ld1995.coursework.configurations.security

import by.ld1995.coursework.models.user.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserPrincipal(private val id: Long,
                    private val name: String,
                    private val fullName: String,
                    @JsonIgnore private val email: String,
                    @JsonIgnore private val pass: String,
                    private val authorities: MutableCollection<out GrantedAuthority>) : UserDetails {

    fun getId() = id

    fun getFullName() = fullName

    override fun getAuthorities() = authorities

    override fun isEnabled() = true

    override fun getPassword() = pass

    override fun getUsername() = name

    override fun isCredentialsNonExpired() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    companion object {
        fun create(user: User): UserPrincipal {
            val authorities = user.roles.stream().map { role ->
                SimpleGrantedAuthority(role.name)
            }.collect(Collectors.toList())
            return UserPrincipal(user.id, user.username, user.fullName, user.email, user.password, authorities)
        }
    }
}