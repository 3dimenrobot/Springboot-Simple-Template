package cn.example.project.module.base;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private String token;

    private String status;

    private String message;

    private Object data;

    public Message(String status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Message(String status,Object data){
        this.status = status;
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
