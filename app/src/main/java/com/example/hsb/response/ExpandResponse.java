package com.example.hsb.response;

import com.example.hsb.entities.Role;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpandResponse {
    @SerializedName("role")
    private Role role;
}
