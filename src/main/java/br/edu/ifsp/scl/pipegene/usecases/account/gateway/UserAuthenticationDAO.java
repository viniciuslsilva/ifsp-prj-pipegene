package br.edu.ifsp.scl.pipegene.usecases.account.gateway;

import br.edu.ifsp.scl.pipegene.usecases.account.ApplicationUser;

import java.util.Optional;

public interface UserAuthenticationDAO {

    Optional<ApplicationUser> findUserByUsername(String username);
}
