package cn.example.project.module.base;

import java.util.HashMap;
import java.util.Map;

public class Message {
    Map<String, Object> dataMap = new HashMap<>(2);

    private String token;

    private String status;

    private String message;


    public Message(String status,String message,Object dataItem){
        this.status = status;
        this.message = message;
        dataMap.put("data", dataItem);
    }

    public Message(String status,Object data){
        this.status = status;
        dataMap.put("data", data);
    }

    public void put(String key,Object value){
        dataMap.put(key,value);

    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
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
}
