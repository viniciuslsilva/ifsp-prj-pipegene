package br.edu.ifsp.scl.pipegene.usecases.account;

import br.edu.ifsp.scl.pipegene.usecases.account.model.ApplicationUser;
import br.edu.ifsp.scl.pipegene.usecases.account.model.CreateApplicationUser;

public interface ApplicationUserCRUD {

    ApplicationUser registerNewUser(CreateApplicationUser user);
}
