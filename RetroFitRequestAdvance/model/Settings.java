package com.test.webservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Priyesh Bhargava
 * @version 1.0.1
 */
public class Settings {

    private String count;
    private String currPage;
    private String prevPage;

    @SerializedName("next_page")
    private String nextPage;
    private String success;
    private String message;
    private List<String> fields = new ArrayList<String>();

    /**
     * @return The count
     */
    public String getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     * @return The currPage
     */
    public String getCurrPage() {
        return currPage;
    }

    /**
     * @param currPage The curr_page
     */
    public void setCurrPage(String currPage) {
        this.currPage = currPage;
    }

    /**
     * @return The prevPage
     */
    public String getPrevPage() {
        return prevPage;
    }

    /**
     * @param prevPage The prev_page
     */
    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }

    /**
     * @return The nextPage
     */
    public String getNextPage() {
        return nextPage;
    }

    /**
     * @param nextPage The next_page
     */
    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @return The success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The fields
     */
    public List<String> getFields() {
        return fields;
    }

    /**
     * @param fields The fields
     */
    public void setFields(List<String> fields) {
        this.fields = fields;
    }


    /**
     * @return The success
     */
    public boolean isSuccess() {
        if (success != null && (success.equalsIgnoreCase("1") || success.equalsIgnoreCase("2") || success.equalsIgnoreCase("3"))) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @return The nextPage
     */
    public boolean hasNextPage() {
        if (nextPage != null && ((nextPage.equalsIgnoreCase("1")))) {
            return true;
        } else {
            return false;
        }
    }

}

