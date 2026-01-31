package com.andreileal.dev.nutrifit.subscription.infrastructure.auth;

import static com.andreileal.dev.nutrifit.subscription.infrastructure.conts.SecurityWhiteList.PUBLIC_ENDPOINTS;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final AntPathMatcher matcher = new AntPathMatcher();
    public static final String BEARER_ = "Bearer ";

    private final TokenGenerator tokenGenerator;
    private final UserDetailsService userDetailsService;

    public AuthTokenFilter(TokenGenerator tokenGenerator,
            UserDetailsService userDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        if (isWhiteListed(path)) {
            return true;
        }

        return !path.startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isWhiteListed(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = parseJwt(request);

        if (jwt != null && tokenGenerator.validar(jwt)) {
            String email = tokenGenerator.extrairEmail(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext()
                    .setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

            return;
        }

        throw new BadCredentialsException("Invalid token");

    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith(BEARER_)) {
            return headerAuth.replace(BEARER_, "");
        }
        return null;
    }

    private boolean isWhiteListed(String path) {
        for (String white : PUBLIC_ENDPOINTS) {
            if (matcher.match(white, path)) {
                return true;
            }
        }
        return false;
    }
}
