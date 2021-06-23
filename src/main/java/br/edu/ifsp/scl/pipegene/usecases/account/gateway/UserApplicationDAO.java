package br.edu.ifsp.scl.pipegene.usecases.account.gateway;

import br.edu.ifsp.scl.pipegene.usecases.account.model.ApplicationUser;

import java.util.Optional;

public interface UserApplicationDAO {

    Optional<ApplicationUser> findUserByUsername(String username);

    ApplicationUser saveNewUser(ApplicationUser user);
}
