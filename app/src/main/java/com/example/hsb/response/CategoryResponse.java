package com.example.hsb.response;

import com.example.hsb.record.CategoryRecord;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse extends BaseResponse {
    @SerializedName("items")
    private List<CategoryRecord> items;
}