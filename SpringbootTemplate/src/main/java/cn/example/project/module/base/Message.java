package cn.example.project.module.base;

import java.util.HashMap;
import java.util.Map;

public class Message {
    Map<String, Object> dataMap = new HashMap<>(2);

    public Message(String status,String message,Object data){
        dataMap.put("status",status);
        dataMap.put("message",message);
        dataMap.put("data",data);
    }

    public Message(String status,Object data){
        dataMap.put("status",status);
        dataMap.put("data",data);
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
}
