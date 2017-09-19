package com.test.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hb on 29/3/17.
 */

public class Response<T> {
    @SerializedName("settings")
    @Expose
    private Settings settings;
    @SerializedName("data")
    @Expose
    private T data = null;

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
