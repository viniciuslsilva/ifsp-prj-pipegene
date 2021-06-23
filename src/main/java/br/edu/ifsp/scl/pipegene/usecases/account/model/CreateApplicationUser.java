package br.edu.ifsp.scl.pipegene.usecases.account.model;

public class CreateApplicationUser {

    private String username;
    private String name;
    private String password;
    private String orcid;
    private String github;

    public CreateApplicationUser(String username, String name, String password, String orcid, String github) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.orcid = orcid;
        this.github = github;
    }

    public ApplicationUser toApplicationUser() {
        return new ApplicationUser(username, name, orcid, github, password);
    }
}
