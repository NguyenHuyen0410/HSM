package com.example.hsb.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResponse <T> {
    @SerializedName("page")
    private int page;
    @SerializedName("perPage")
    private int perPage;
    @SerializedName("totalItems")
    private int totalItems;
    @SerializedName("totalPages")
    private int totalPages;
    @SerializedName("items")
    private List<T> items;
}
