package com.test.wsutils;

import com.google.gson.JsonObject;

public interface IParserListener {
    void errorResponse(String error, int requestCode);

    void unSuccessResponse(String message, int requestCode);

    void successResponse(JsonObject response, String message, int requestCode);

    void noInternetConnection(String message);
}

