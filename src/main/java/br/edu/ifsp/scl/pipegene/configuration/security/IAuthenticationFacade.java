package br.edu.ifsp.scl.pipegene.configuration.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}