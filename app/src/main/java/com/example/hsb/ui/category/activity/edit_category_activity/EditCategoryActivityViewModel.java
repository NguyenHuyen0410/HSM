package com.example.hsb.ui.category.activity.edit_category_activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Category;
import com.example.hsb.repository.CategoryRepository;

import java.util.List;

public class EditCategoryActivityViewModel extends ViewModel {
    private MutableLiveData<Category> mCategory = new MutableLiveData<>();
    private MutableLiveData<String> toastMessageLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteStatusLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Category>> mListCategoryLiveData;
    private CategoryRepository categoryRepository;

    public EditCategoryActivityViewModel() {
        categoryRepository = CategoryRepository.getInstance();
        mListCategoryLiveData = categoryRepository.getCategoryList();
    }

    public MutableLiveData<Category> getCategoryLiveData() {
        return mCategory;
    }

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessageLiveData;
    }

    public MutableLiveData<Boolean> getDeleteStatusLiveData() {
        return deleteStatusLiveData;
    }

    public MutableLiveData<List<Category>> getCategoryListLiveData() {
        return mListCategoryLiveData;
    }

    public void editCategory(Category category) {
        categoryRepository.editCategory(category, new CategoryRepository.EditCategoryCallback() {
            @Override
            public void onEditSuccess(Category updatedCategory) {
                mCategory.postValue(updatedCategory);
                toastMessageLiveData.postValue("Category updated successfully.");
            }

            @Override
            public void onEditFailure(String errorMessage) {
                toastMessageLiveData.postValue("Update category failed: " + errorMessage);
            }
        });
    }

    public void createCategory(Category category) {
        categoryRepository.createCategory(category, new CategoryRepository.CreateCategoryCallback() {
            @Override
            public void onCreateSuccess(Category newCategory) {
                mCategory.postValue(newCategory);
                toastMessageLiveData.postValue("Category created successfully.");
            }

            @Override
            public void onCreateFailure(String errorMessage) {
                toastMessageLiveData.postValue("Create category failed: " + errorMessage);
            }
        });
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteCategory(categoryId, new CategoryRepository.DeleteCategoryCallback() {
            @Override
            public void onDeleteSuccess() {
                deleteStatusLiveData.postValue(true);
            }

            @Override
            public void onDeleteFailure(String errorMessage) {
                toastMessageLiveData.postValue("Delete category failed: " + errorMessage);
            }
        });
    }
}