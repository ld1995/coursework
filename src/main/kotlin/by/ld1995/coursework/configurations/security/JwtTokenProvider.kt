package by.ld1995.coursework.configurations.security

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(@Value("\${app.jwtSecret}") private val jwtSecret: String,
                       @Value("\${app.jwtExpirationInMs}") private val jwtExpirationInMs: Int) {

    private val _logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal
        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs)

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()
    }

    fun getUserIdFromJWT(token: String): Long {
        val claims: Claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
        return claims.subject.toLong()
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            _logger.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            _logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            _logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            _logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            _logger.error("JWT claims string is empty.")
        }
        return false
    }
}