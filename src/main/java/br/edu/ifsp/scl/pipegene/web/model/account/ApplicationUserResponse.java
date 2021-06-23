package br.edu.ifsp.scl.pipegene.web.model.account;

import br.edu.ifsp.scl.pipegene.usecases.account.model.ApplicationUser;

import java.util.UUID;

public class ApplicationUserResponse {

    private UUID id;
    private String username;
    private String name;
    private String orcid;
    private String github;

    public static ApplicationUserResponse createFromApplicationUser(ApplicationUser user) {
        return new ApplicationUserResponse(
               user.getId(),
                user.getUsername(),
                user.getName(),
                user.getOrcid(),
                user.getGithub()
        );
    }

    public ApplicationUserResponse() {
    }

    private ApplicationUserResponse(UUID id, String username, String name, String orcid, String github) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.orcid = orcid;
        this.github = github;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getOrcid() {
        return orcid;
    }

    public String getGithub() {
        return github;
    }
}
