package com.test.webservice.listeners;

import com.test.webservice.model.Response;
import com.test.webservice.model.ResponseData;
import com.test.webservice.model.Settings;

import retrofit2.Call;

public interface ApiListeners {

    void onResponseSuccess(ResponseData responseData, Settings settings, int requestCode);

    void onResponseFailed(Call<Response> call, int requestCode);
}
