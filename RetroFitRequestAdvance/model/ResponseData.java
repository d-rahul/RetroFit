package com.test.webservice.model;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Priyesh Bhargava
 * @version 1.0.1
 */

public class ResponseData {

    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public <T> List<T> getData(Class<T> c){
        Type type = new ListParameterizedType<T>(c);
        return new Gson().fromJson(response, type);
    }

    public static <T> List<T> getData(Class<T> c,String response){
        Type type = new ListParameterizedType<T>(c);
        return new Gson().fromJson(response, type);
    }

}
