package br.edu.ifsp.scl.pipegene.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static br.edu.ifsp.scl.pipegene.external.persistence.ProjectDAOImpl.OWNER_ID;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UUID getUserAuthenticatedId() {
        return OWNER_ID;
        // return (UUID) getAuthentication().getPrincipal();
    }
}