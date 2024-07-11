package com.example.hsb.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResponse<T> {
    private int page;
    private int perPage;
    private int totalItems;
    private int totalPages;
    @SerializedName("items")
    private List<T> items;
}
