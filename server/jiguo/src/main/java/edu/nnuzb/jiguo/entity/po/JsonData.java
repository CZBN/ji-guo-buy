package edu.nnuzb.jiguo.entity.po;

import lombok.Data;
@Data
public class JsonData {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    private Object data;
    public static JsonData ok(Object data){
        JsonData jsonData = new JsonData();
        jsonData.setCode(0);
        jsonData.setMessage(null);
        jsonData.setData(data);
        return jsonData;
    }

    public static JsonData fail(Integer code, String message){
        JsonData jsonData = new JsonData();
        jsonData.setCode(code);
        jsonData.setMessage(message);
        jsonData.setData(null);
        return jsonData;
    }
}



