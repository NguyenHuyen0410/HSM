package com.example.hsb.ui.customer_history.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.repository.CategoryRepository;

import java.util.List;

import lombok.Getter;

public class CategoryFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Category>> mListCategoryLiveData;
    // LiveData for toast messages
    @Getter
    private MutableLiveData<String> toastMessageLiveData;

    public CategoryFragmentViewModel() {
        mListCategoryLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        mListCategoryLiveData = categoryRepository.getCategoryList();
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

}
