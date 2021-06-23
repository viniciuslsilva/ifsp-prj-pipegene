package br.edu.ifsp.scl.pipegene.web.model.account;

import br.edu.ifsp.scl.pipegene.usecases.account.model.CreateApplicationUser;

import javax.validation.constraints.NotNull;

public class CreateUserRequest {

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String password;

    private String orcid;

    private String github;

    public CreateUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getOrcid() {
        return orcid;
    }

    public String getGithub() {
        return github;
    }

    public CreateApplicationUser toCreateUserApplication() {
        return new CreateApplicationUser(username, name, password, orcid, github);
    }
}
