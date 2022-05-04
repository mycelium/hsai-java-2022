package ru.spbstu.hsai.userservice.common.dto;

import java.io.Serializable;

public class GeneralResponse<T> implements Serializable {
    
    private T data;
    
    private String message;
    private int code;
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

}
