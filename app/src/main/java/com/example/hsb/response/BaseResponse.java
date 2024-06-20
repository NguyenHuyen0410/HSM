package com.example.hsb.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private int page;
    private int perPage;
    private int totalItems;
    private int totalPages;
}
