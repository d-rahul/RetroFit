package com.test.wsutils;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.test.R;
import com.test.utils.AppLogger;
import com.test.utils.DeviceUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WSClient {
    private static WSClient wsClient;

    public static WSClient getInstance() {
        if (wsClient == null) {
            wsClient = new WSClient();
            return wsClient;
        } else {
            return wsClient;
        }
    }

    public static WSClientListener getRetroFitAPIListener() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        //.addHeader("access-token", UserDetails.getInstance(getInstance().getApplicationContext()).getAccessToken())
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        /*Uncomment following line to enable logging of WS*/
        httpClient.addInterceptor(logging);

        httpClient.readTimeout(WSUtils.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(WSUtils.CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.writeTimeout(WSUtils.CONNECTION_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WSUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(WSClientListener.class);
    }


    public void request(final Context context, final int requestCode, Observable<JsonElement> observable, final IParserListener iParserListener) {
        try {
            if (!DeviceUtils.checkInternetConnection(context)) {
                if (iParserListener != null) {
                    AppLogger.e(requestCode + " " + context.getString(R.string.no_internet_message));
                    iParserListener.noInternetConnection(context.getString(R.string.no_internet_message));
                }
            } else {
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<JsonElement>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonElement jsonElement) {
                                try {
                                    AppLogger.e(requestCode + " " + jsonElement.getAsJsonObject().toString());
                                    onSuccessResponse(context, jsonElement.getAsJsonObject(), iParserListener, requestCode);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    iParserListener.errorResponse(e.getMessage(), requestCode);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppLogger.e(requestCode + " " + e.getMessage());
                                iParserListener.errorResponse(e.getMessage(), requestCode);
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSuccessResponse(Context context, JsonObject jsonObject, IParserListener mParseListener, int reqCode) {
        JsonObject settingObject = jsonObject.get(WSUtils.KEY_SETTINGS).getAsJsonObject();
        String success = settingObject.get(WSUtils.KEY_SUCCESS).getAsString();
        String message = settingObject.get(WSUtils.KEY_MESSAGE).getAsString();
        if (success.equalsIgnoreCase("2")) {
            //showSessionExpireDialog(context, message);
            mParseListener.successResponse(jsonObject, message, reqCode);
        } else if (success.equalsIgnoreCase("0")) {
            mParseListener.unSuccessResponse(message, reqCode);
        } else if (success.equalsIgnoreCase("1") || success.equalsIgnoreCase("3")) {
            mParseListener.successResponse(jsonObject, message, reqCode);
        } else {
            mParseListener.errorResponse(message, reqCode);
        }
    }

   /*private void showSessionExpireDialog(final Context context, String message) {
        PopUtils.showSingleBtnMessageDialog(context, message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLoginActivity(context);
            }
        });
    }*/

    public static RequestBody getStringRequestBody(String value) {
        MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, value);
        return requestBody;
    }

    public static RequestBody getImageRequestBody(File value) {
        if (value != null && value.exists()) {
            MediaType MEDIA_TYPE_IMG = MediaType.parse("image/*");
            RequestBody requestBodyImage = RequestBody.create(MEDIA_TYPE_IMG, value);
            return requestBodyImage;
        }
        return null;
    }
}
