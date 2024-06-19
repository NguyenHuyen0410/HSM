package com.example.hsb.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// được thiết kế để quản lý và cung cấp một đối tượng Retrofit duy nhất, giúp thực hiện các yêu cầu
// mạng trong ứng dụng Android. Retrofit là một thư viện HTTP phổ biến cho Android và Java,
// được sử dụng để tạo ra các yêu cầu mạng mạnh mẽ và dễ dàng quản lý.
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://hotel-service-manage.pockethost.io";

    private RetrofitClient() {
        // Khởi tạo Retrofit
    }

    // Phương thức để lấy đối tượng Retrofit
    public static Retrofit getInstance() {
        // Kiểm tra nếu Retrofit đã được khởi tạo
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
