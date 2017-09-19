package com.test.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.MimeTypeMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.test.BuildConfig;
import com.test.base.LetsTalkAcademyApplication;
import com.test.utils.CommonUtils;
import com.test.webservice.listeners.ApiListeners;
import com.test.webservice.model.Response;
import com.test.webservice.model.ResponseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitHelper implements Callback<Response> {
    ProgressDialog progressDialog;
    private Context mContext;
    private ApiListeners mApiListeners;
    private int requetsCode;
    private boolean showProgressbar;

    private static final int CACHE_TIME_IN_SECONDS = 2 * 60;
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder().addHeader("Content-Type", "application/json").header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", CACHE_TIME_IN_SECONDS, 0)).build();
        }
    };

    public static OkHttpClient initOkHttpClient(Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(logging);  // <-- this is the important line!        }
        }
        Cache cache = new Cache(context.getCacheDir(), 1024 * 1024 * 10);
        httpClientBuilder.cache(cache);
        httpClientBuilder.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        httpClientBuilder
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        return okHttpClient;
    }

    public static Retrofit.Builder initBaseRetroFit(Context context) {
        OkHttpClient okHttpClient = initOkHttpClient(context);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ApiUtil.Config.ENDPOINT_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        // builderFeed = new Retrofit.Builder().baseUrl(ApiRequestUtil.FEED_URL)
        // .client(okHttpClient).addConverterFactory(GsonConverterFactory.create());
        return builder;
    }

    public static Retrofit.Builder initOtherRetroFit(Context context) {
        OkHttpClient okHttpClient = initOkHttpClient(context);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(/*ApiUtil.Config.ENDPOINT_URL*/"Other endpoint url")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        return builder;
    }

    public RetroFitHelper(Context mContext, boolean showProgressbar, int requetsCode, ApiListeners mApiListeners) {
        this.mContext = mContext;
        this.mApiListeners = mApiListeners;
        this.requetsCode = requetsCode;
        this.showProgressbar = showProgressbar;
        if (showProgressbar) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public RetroFitHelper(Context mContext, String message, boolean showProgressbar, int requetsCode, ApiListeners mApiListeners) {
        this.mContext = mContext;
        this.mApiListeners = mApiListeners;
        this.requetsCode = requetsCode;
        this.showProgressbar = showProgressbar;
        if (showProgressbar) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }
    }

    public <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = LetsTalkAcademyApplication.builder.build();
        return retrofit.create(serviceClass);
    }

    public static RequestBody getStringRequestBody(String value) {
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
        return RequestBody.create(MEDIA_TYPE_TEXT, value == null ? "" : value);
    }

    public static String getFileUploadKey(String key, File file) {
        return "" + key + "\"; filename=\"" + file.getName();
    }

    public static RequestBody getFileRequestBody(String path) {
        System.out.println("path:" + path);
        File file = new File(path);
        MediaType MEDIA_TYPE = MediaType.parse(getMimeType(file.getAbsolutePath()));
        return RequestBody.create(MEDIA_TYPE, file);
    }

    private static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public ProgressRequestBody getFileProgressRequestBody(File file, ProgressRequestBody.UploadCallbacks uploadCallbacks) {
        return new ProgressRequestBody(file, uploadCallbacks);
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        ResponseData mResponseData = new ResponseData();
        Gson gson = new Gson();
        if (response.body() != null) {
            //JsonElement jsonElement = response.body();
            JsonElement jsonElement = gson.toJsonTree(response.body().getData());
            System.out.println("jsonObject:" + jsonElement);
            try {
                if (jsonElement.isJsonArray()) {
                    mResponseData.setResponse(jsonElement.toString());

                } else if (jsonElement.isJsonObject()) {
                    JSONObject mJson = new JSONObject(jsonElement.toString());
                    JSONArray mJsonArray = new JSONArray();
                    mJsonArray.put(mJson);
                    mResponseData.setResponse(mJsonArray.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mApiListeners != null)
                mApiListeners.onResponseSuccess(mResponseData, response.body().getSettings(), requetsCode);
        }
        try {
            if (progressDialog != null && progressDialog.getWindow() != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        System.out.println("onFailure:" + call);
        t.printStackTrace();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (t instanceof SocketTimeoutException) {
            CommonUtils.showToast(mContext, "Server connection timeout.");
        } else {
            if (t.getMessage() != null) {
                CommonUtils.showToast(mContext, "Failed to connect with server.");
            } else {
                CommonUtils.showToast(mContext, "Make sure that the device is connected to the Internet and try again.");
            }
        }

        if (mApiListeners != null)
            mApiListeners.onResponseFailed(call, requetsCode);
    }
}
