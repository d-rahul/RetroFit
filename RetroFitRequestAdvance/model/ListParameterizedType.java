package com.test.webservice.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Priyesh Bhargava
 * @version 1.0.1
 */
public class ListParameterizedType<T> implements ParameterizedType {

    private Class<?> wrapped;

    public ListParameterizedType(Class<T> wrapped) {
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[] {wrapped};
    }

    public Type getRawType() {
        return List.class;
    }

    public Type getOwnerType() {
        return null;
    }

}