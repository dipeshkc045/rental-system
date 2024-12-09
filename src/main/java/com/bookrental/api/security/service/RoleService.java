package com.bookrental.api.security.service;


import com.bookrental.api.security.model.Roles;

public interface RoleService {
    Roles save (Roles roles);
    Roles getById(Long id);
}
