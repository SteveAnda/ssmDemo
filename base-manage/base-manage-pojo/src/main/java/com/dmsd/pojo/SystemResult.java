package com.dmsd.pojo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class SystemResult implements Serializable {

    // 定义jackson对象
    public static final ObjectMapper objectMapper = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    //响应消息
    private String msg;

    //响应中的数据
    private Object data;



    public SystemResult() {

    }

    public SystemResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public SystemResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static SystemResult build(Integer status, String msg){
        return new SystemResult(status, msg, null);
    }

    public static SystemResult build(Integer status, String msg, Object data){
        return new SystemResult(status, msg, data);
    }

    public static SystemResult ok() {
        return new SystemResult();
    }

    public static SystemResult ok(Object data){
        return new SystemResult(data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为SystemResult对象
     *
     * @param jsonData json数据
     * @param clazz SystemResult中的object类型
     * @return
     */
    public static SystemResult formatToPojo(String jsonData, Class<?> clazz){

        try{

            if (clazz == null){
                return objectMapper.readValue(jsonData, SystemResult.class);
            }

            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;

            if(clazz != null){

                if(data.isObject()){
                    obj = objectMapper.readValue(data.traverse(), clazz);
                } else if (data.isTextual()){
                    obj = objectMapper.readValue(data.asText(), clazz);
                }
            }

            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 没有object对象的转化
     *
     * @param jsondata
     * @return
     */
    public static SystemResult format(String jsondata){
        try{

            return objectMapper.readValue(jsondata, SystemResult.class);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static SystemResult formatToList(String jsonData, Class<?> clazz){
        try {

            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;

            if (data.isArray() && data.size() > 0){

                obj = objectMapper.readValue(data.traverse(),objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));

            }

            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
