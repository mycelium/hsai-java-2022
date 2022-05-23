package ru.spbstu.hsai.userservice.common.dto;

import java.io.Serializable;

public class CreateUserResponse implements Serializable{
    
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
}
