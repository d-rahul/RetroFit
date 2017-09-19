package com.test.wsutils;

import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface WSClientListener {

    /*//Field data request
    private void requestLogin() {
        //hide keyboard before request
        CommonUtils.hideKeyboard(this, binding.getRoot());

        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put(WSUtils.KEY_PHONE_CODE, "+965");
        params.put(WSUtils.KEY_PHONE_NUMBER, CommonUtils.arabicToDecimal(binding.edtNumber.getText().toString()));
        params.put(WSUtils.KEY_LANG_ID, getString(R.string.lang_id));
        if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstance(this).getString(SharedPreferencesUtils.SP_KEY_USER_ID, ""))) {
            params.put(WSUtils.KEY_USER_ACCESS_TOKEN, SharedPreferencesUtils.getInstance(this).getString(SharedPreferencesUtils.SP_KEY_USER_ACCESS_TOKEN, ""));
            params.put(WSUtils.KEY_LOGGED_USER_ID, SharedPreferencesUtils.getInstance(this).getString(SharedPreferencesUtils.SP_KEY_USER_ID, ""));
        }
        params.put(WSUtils.KEY_WS_TOKEN, SharedPreferencesUtils.getInstance(this).getString(SharedPreferencesUtils.SP_KEY_APP_TOKEN, ""));
        Observable<JsonElement> call = wsClientListener.login(params);
        new WSClient().request(this, WSUtils.REQ_LOGIN, call, this);
    }*/

    /* //Multipart request format
    private void requestChangeKidPhoto() {
        showProgress();
        try {
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
            requestBodyBuilder.setType(MultipartBody.FORM);
            requestBodyBuilder.addFormDataPart(WSUtils.KEY_KID_ID, null, WSClient.getStringRequestBody(myKidsModel.kidId));
            if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.SP_KEY_USER_ID, ""))) {
                requestBodyBuilder.addFormDataPart(WSUtils.KEY_USER_ACCESS_TOKEN, null, WSClient.getStringRequestBody(SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.SP_KEY_USER_ACCESS_TOKEN, "")));
                requestBodyBuilder.addFormDataPart(WSUtils.KEY_LOGGED_USER_ID, null, WSClient.getStringRequestBody(SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.SP_KEY_USER_ID, "")));
            }
            requestBodyBuilder.addFormDataPart(WSUtils.KEY_WS_TOKEN, null, WSClient.getStringRequestBody(SharedPreferencesUtils.getInstance(mActivity).getString(SharedPreferencesUtils.SP_KEY_APP_TOKEN, "")));

            if (selectedImageFile != null) {
                requestBodyBuilder.addFormDataPart(WSUtils.KEY_PROFILE_IMAGE, selectedImageFile.getName(), WSClient.getImageRequestBody(selectedImageFile));
            }
            Observable<JsonElement> call = wsClientListener.changeKidPhoto(requestBodyBuilder.build());
            new WSClient().request(mActivity, WSUtils.REQ_CHANGE_KID_PHOTO, call, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @FormUrlEncoded
    @POST("feedback_types_list")
    Observable<JsonElement> feedbackTypeList(@FieldMap Map<String, String> params);

    @GET("get_feature_data")
    Observable<JsonElement> getFeatureData(@QueryMap() Map<String, String> params);

    @POST("promote_request")
    Observable<JsonElement> promoteRequest(@Body MultipartBody filePart);
}
