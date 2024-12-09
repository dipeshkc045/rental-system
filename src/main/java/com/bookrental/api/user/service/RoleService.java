package com.bookrental.api.user.service;


import com.bookrental.api.user.model.Roles;

public interface RoleService {
    Roles save (Roles roles);
    Roles getById(Long id);
}
