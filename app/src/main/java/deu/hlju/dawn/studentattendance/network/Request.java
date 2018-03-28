package deu.hlju.dawn.studentattendance.network;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import deu.hlju.dawn.studentattendance.config.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request {
    private Api api;

    private Request() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.FACE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }


    public static Request getSingle() {
        return RequestHolder.request;
    }

    public static class RequestHolder {
        static Request request = new Request();
    }

    private Map<String, String> getBaseParames() {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", Constants.FACE_API_KEY);
        params.put("api_secret", Constants.FACE_API_SECRET);
        return params;
    }

    public Map<String, String> getDetactPatames(String imageUrl) {
        Map<String, String> parames = getBaseParames();
        parames.put("image_url", imageUrl);
        return parames;
    }

    public Map<String, String> getCreateFacesetPatames() {
        Map<String, String> parames = getBaseParames();
        parames.put("outer_id", Constants.OUTER_ID);
        return parames;
    }

    public Map<String, String> getDetailFacesetPatames() {
        Map<String, String> parames = getBaseParames();
        parames.put("outer_id", Constants.OUTER_ID);
        return parames;
    }

    public Map<String, String> getRemoveFacesetPatames(String face_token) {
        Map<String, String> parames = getBaseParames();
        parames.put("outer_id", Constants.OUTER_ID);
        parames.put("face_tokens", face_token);
        return parames;
    }

    public Map<String, String> getAddFacesetPatames(String face_token) {
        Map<String, String> parames = getBaseParames();
        parames.put("outer_id", Constants.OUTER_ID);
        parames.put("face_tokens", face_token);
        return parames;
    }

    public Map<String, String> getSearchPatames(String url) {
        Map<String, String> parames = getBaseParames();
        parames.put("outer_id", Constants.OUTER_ID);
        parames.put("image_url", url);
        return parames;
    }
}
