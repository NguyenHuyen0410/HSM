package com.example.hsb.utils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;

//lớp base NetworkUtil với các phương thức tiện ích để tạo và gửi yêu cầu HTTP POST với JSON
public class NetworkUtil {
    private static final String BASE_URL = "http://anya-mock.koreacentral.cloudapp.azure.com/api"; // Thay bằng URL của mình nhưng chưa có
    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    // Tạo RequestBody từ Map các tham số
    public static RequestBody createRequestBody(Map<String, Object> params) {
        JSONObject jsonObject = new JSONObject(params);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }

    // Gửi yêu cầu HTTP POST đồng bộ
    public static String postSync(String endpoint, Map<String, Object> params) throws IOException {
        RequestBody requestBody = createRequestBody(params);
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    // Gửi yêu cầu HTTP POST bất đồng bộ
    public static void postAsync(String endpoint, Map<String, Object> params, final NetworkCallback callback) {
        RequestBody requestBody = createRequestBody(params);
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure(new IOException("Unexpected code " + response));
                } else {
                    callback.onSuccess(response.body().string());
                }
            }
        });
    }

    // Interface cho callback bất đồng bộ
    public interface NetworkCallback {
        void onSuccess(String response);
        void onFailure(IOException e);
    }
}
