package com.example.hsb.ui.category.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.repository.CategoryRepository;

import java.util.List;

import lombok.Getter;

public class CategoryFragmentViewModel extends ViewModel {
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<List<Category>> mListCategoryLiveData;
    // LiveData for toast messages
    @Getter
    private final MutableLiveData<String> toastMessageLiveData;

    public CategoryFragmentViewModel() {
        categoryRepository = CategoryRepository.getInstance();
        mListCategoryLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        loadData();
    }

    private void loadData() {
        categoryRepository.getCategoryList().observeForever(categories -> {
            mListCategoryLiveData.postValue(categories);
        });
    }

    public LiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

    public void refreshData() {
        loadData();
    }
}