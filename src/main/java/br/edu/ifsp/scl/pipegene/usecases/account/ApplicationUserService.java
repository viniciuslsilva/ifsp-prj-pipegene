package br.edu.ifsp.scl.pipegene.usecases.account;

import br.edu.ifsp.scl.pipegene.usecases.account.gateway.UserAuthenticationDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final UserAuthenticationDAO userAuthenticationDAO;

    public ApplicationUserService(UserAuthenticationDAO userAuthenticationDAO) {
        this.userAuthenticationDAO = userAuthenticationDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthenticationDAO.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }
}
