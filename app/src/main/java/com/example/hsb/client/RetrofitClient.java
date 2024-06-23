package com.example.hsb.client;

import com.example.hsb.service.AccountServiceApi;
import com.example.hsb.service.CategoryServiceApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// được thiết kế để quản lý và cung cấp một đối tượng Retrofit duy nhất, giúp thực hiện các yêu cầu
// mạng trong ứng dụng Android. Retrofit là một thư viện HTTP phổ biến cho Android và Java,
// được sử dụng để tạo ra các yêu cầu mạng mạnh mẽ và dễ dàng quản lý.
public class RetrofitClient {
    private static Retrofit retrofit;
    private static RetrofitClient instance;
    private static final String BASE_URL = "https://hotel-service-manage.pockethost.io/api/collections/";

    private RetrofitClient() {
        // Khởi tạo Retrofit
        // Kiểm tra nếu Retrofit đã được khởi tạo
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Phương thức để lấy đối tượng Retrofit
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public AccountServiceApi getAccountServiceApi() {
        return retrofit.create(AccountServiceApi.class);
    }

    public CategoryServiceApi getCategoryServiceApi() {
        return retrofit.create(CategoryServiceApi.class);
    }
}
