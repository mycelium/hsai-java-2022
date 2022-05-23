package ru.spbstu.hsai.userservice.common.dto;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {

    private String login;
    private String email;
    private String name;

    public String getLogin() {
        return login;
    }

    public CreateUserRequest setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateUserRequest setName(String name) {
        this.name = name;
        return this;
    }

}
