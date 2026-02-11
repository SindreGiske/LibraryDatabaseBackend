package no.fintlabs.librarydatabasebackend.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class SessionAuthFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val context = SecurityContextHolder.getContext()
        if (context.authentication == null) {
            val session = request.getSession(false)
            val userId = session?.getAttribute("userId")
            val isAdmin = session.getAttribute("isAdmin") as? Boolean ?: false

            if (userId != null) {
                val authorities =
                    buildList {
                        add(SimpleGrantedAuthority("ROLE_USER"))
                        if (isAdmin) add(SimpleGrantedAuthority("ROLE_ADMIN"))
                    }

                val auth = UsernamePasswordAuthenticationToken(userId.toString(), null, authorities)
                context.authentication = auth
            }
        }
        filterChain.doFilter(request, response)
    }
}
