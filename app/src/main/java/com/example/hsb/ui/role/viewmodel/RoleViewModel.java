package com.example.hsb.ui.role.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.hsb.entities.Role;
import com.example.hsb.repository.RoleRepository;

import java.util.List;

import lombok.Getter;

@Getter
public class RoleViewModel extends ViewModel {
    private final LiveData<List<Role>> roles;

    public RoleViewModel() {
        RoleRepository roleRepository = RoleRepository.getInstance();
        roles = roleRepository.getRoles();
    }

}