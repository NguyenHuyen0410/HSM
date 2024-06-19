package com.example.hsb.client.response;
import com.example.hsb.client.record.AccountRecord;

import java.util.List;

public class AccountResponse {
    private int page;
    private int perPage;
    private int totalItems;
    private int totalPages;
    private List<AccountRecord> items;

    // Getters and setters

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AccountRecord> getItems() {
        return items;
    }

    public void setItems(List<AccountRecord> items) {
        this.items = items;
    }
}


