package com.example.hsb.ui.home.fragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.entities.Price;
import com.example.hsb.repository.CategoryRepository;
import com.example.hsb.repository.PriceRepository;

import java.util.List;

import lombok.Getter;

public class HomeFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Category>> mListCategoryLiveData;
    private MutableLiveData<List<Price>> mListPriceLiveData;

    // LiveData for toast messages
    @Getter
    private final MutableLiveData<String> toastMessageLiveData;

    public HomeFragmentViewModel() {
        mListCategoryLiveData = new MutableLiveData<>();
        mListPriceLiveData = new MutableLiveData<>();
        toastMessageLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData() {
        CategoryRepository categoryRepository = CategoryRepository.getInstance();
        PriceRepository priceRepository = PriceRepository.getInstance();

        mListCategoryLiveData = categoryRepository.getCategoryList();
        mListPriceLiveData = priceRepository.getPriceList();

    }

    public MutableLiveData<List<Category>> getListCategoryLiveData() {
        return mListCategoryLiveData;
    }

    public MutableLiveData<List<Price>> getListPriceLiveDataLiveData() {
        return mListPriceLiveData;
    }

}
