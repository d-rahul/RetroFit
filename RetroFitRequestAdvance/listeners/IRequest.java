package com.test.webservice.listeners;

public interface IRequest {

    /*@GET(ApiRequestUtil.API_COUNTRY_CODE)
    Call<ApiUtil> getCountryCode(@QueryMap Map<String, String> mParam);

    @Multipart
    @POST(ApiRequestUtil.API_USER_REGISTRATION)
    Call<ApiUtil> postUserRegistration(@PartMap Map<String, RequestBody> fileUploadParams);

    @POST(ApiRequestUtil.API_USER_REGISTRATION_VERIFY_OTP)
    Call<ApiUtil> getUserRegistrationVerifyOTP(@QueryMap Map<String, String> mParam);

    @FormUrlEncoded
    @POST(ApiRequestUtil.API_GET_APP_USER)
    Call<ApiUtil> getAppUser(@FieldMap Map<String, String> mParam);*/

    //Request samples
    /*private void requestFormType() {
        HashMap mParams = new HashMap<>();
        mParams.put("API input param key", "API input param value");
        RetroFitHelper retroFitHelper = new RetroFitHelper(this, false, REQUEST_CODE_USER_LOGIN, this);
        IRequest iRequest = retroFitHelper.createService(IRequest.class);
        Call call = iRequest.getUserLogin(mParams);
        call.enqueue(retroFitHelper);
    }

    private void requestMultipart() {
        RetroFitHelper retroFitHelper = new RetroFitHelper(this, true, REQUEST_CODE_USER_REGISTRATION, this);
        HashMap mParams = new HashMap<>();
        mParams.put(RetroFitHelper.getFileUploadKey("API input param key", new File("File path")), RetroFitHelper.getFileRequestBody("File path"));
        mParams.put("API input param key", RetroFitHelper.getStringRequestBody("Value"));
        IRequest iRequest = retroFitHelper.createService(IRequest.class);
        Call call = iRequest.postUserRegistration(mParams);
        call.enqueue(retroFitHelper);
    }*/

}