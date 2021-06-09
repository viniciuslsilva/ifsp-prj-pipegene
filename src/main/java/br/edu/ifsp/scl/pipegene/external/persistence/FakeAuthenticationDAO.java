package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.usecases.account.ApplicationUser;
import br.edu.ifsp.scl.pipegene.usecases.account.gateway.UserAuthenticationDAO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakeAuthenticationDAO implements UserAuthenticationDAO {

    private final PasswordEncoder passwordEncoder;

    public FakeAuthenticationDAO(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> findUserByUsername(String username) {
        return getApplicationUsers().stream()
                .filter(applicationUser -> applicationUser.getUsername().equals(username))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        UserDetails annaSmithUser = User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
                .authorities(Collections.emptyList())
                .build();

        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password123"))
                .authorities(Collections.emptyList())
                .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
                .authorities(Collections.emptyList())
                .build();

        return Arrays.asList(
                new ApplicationUser(annaSmithUser),
                new ApplicationUser(lindaUser),
                new ApplicationUser(tomUser)
        );
    }
}
