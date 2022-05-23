package ru.spbstu.hsai.userservice.common.entity;

import java.io.Serializable;

public class User implements Serializable {
    
    private String id;
    private String login;
    private String email;
    private String name;
    
    public User(String id, String login, String email, String name) {
        super();
        this.id = id;
        this.login = login;
        this.email = email;
        this.name = name;
    }

    public User() {
        super();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
