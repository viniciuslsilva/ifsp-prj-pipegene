package br.edu.ifsp.scl.pipegene.configuration.security.jwt;

import br.edu.ifsp.scl.pipegene.configuration.properties.model.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public JwtTokenVerifier(SecretKey secretKey, JwtProperties jwtProperties) {
        this.secretKey = secretKey;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(jwtProperties.getAuthorizationHeader());

        if (hasInvalidAuthorizationHeader(authorizationHeader)) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtProperties.getTokenPrefix(), "");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();
            String principal = body.getSubject();
            UUID userId = UUID.fromString(principal);
//            var authorities = (List<Map<String, String>>) body.get("authorities");
//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
//                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
//                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    null

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be truest!", token));
        }

        chain.doFilter(request, response);
    }

    private boolean hasInvalidAuthorizationHeader(String authorizationHeader) {
        return Objects.isNull(authorizationHeader) || authorizationHeader.isEmpty() ||
                !authorizationHeader.startsWith(jwtProperties.getTokenPrefix());
    }
}
