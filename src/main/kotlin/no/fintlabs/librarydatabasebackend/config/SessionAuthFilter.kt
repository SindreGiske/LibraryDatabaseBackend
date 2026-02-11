package no.fintlabs.librarydatabasebackend.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class SessionAuthFilter : OncePerRequestFilter() {
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath

        return request.method == "OPTIONS" ||
            path.startsWith("/login") ||
            path.startsWith("/books")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val session = request.getSession(false)
        val userId = session?.getAttribute("userId")
        val isAdmin = session.getAttribute("isAdmin") as? Boolean ?: false

        if (userId != null && SecurityContextHolder.getContext().authentication == null) {
            val authorities =
                if (isAdmin) {
                    listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
                } else {
                    emptyList()
                }
            val auth = UsernamePasswordAuthenticationToken(userId.toString(), null, authorities)

            SecurityContextHolder.getContext().authentication = auth
        }
        filterChain.doFilter(request, response)
    }
}
