package com.example.hsb.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.hsb.client.RetrofitClient;
import com.example.hsb.entities.Category;
import com.example.hsb.response.ListResponse;
import com.example.hsb.utils.LoggerUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
        private static CategoryRepository instance;
        private MutableLiveData<String> status;
        private static final String SUCCESS = "success";
        private static final String ERROR = "error";

        public static CategoryRepository getInstance() {
            if (instance == null) {
                instance = new CategoryRepository();
            }
            return instance;
        }

        public LiveData<List<Category>> getCategories() {
            status = new MutableLiveData<>();
            MutableLiveData<List<Category>> categories = new MutableLiveData<>();
            Call<ListResponse<Category>> call = RetrofitClient.getInstance().getCategoryServiceApi().getRecords();
            call.enqueue(new Callback<ListResponse<Category>>() {
                @Override
                public void onResponse(@NonNull Call<ListResponse<Category>> call, @NonNull Response<ListResponse<Category>> response) {
                    if (response.isSuccessful()) {
                        ListResponse<Category> categoryResponse = response.body();
                        assert categoryResponse != null;
                        categories.setValue(categoryResponse.getItems());
                        status.setValue(SUCCESS);
                    } else {
                        status.setValue(ERROR);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ListResponse<Category>> call, @NonNull Throwable t) {
                    LoggerUtil.e(t.getMessage());
                    status.setValue(ERROR);
                }
            });
            return categories;
        }
}
