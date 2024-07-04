package com.example.hsb.ui.category.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.repository.CategoryRepository;

import java.util.List;

public class CategoryFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Category>> mListCategoryLiveData;
    private CategoryRepository categoryRepository;
    // LiveData for toast messages
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();

    public CategoryFragmentViewModel() {
        mListCategoryLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        categoryRepository = CategoryRepository.getInstance();
        mListCategoryLiveData = categoryRepository.getCategoryList();
    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }
}
