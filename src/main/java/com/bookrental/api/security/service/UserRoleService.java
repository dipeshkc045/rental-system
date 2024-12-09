package com.bookrental.api.security.service;

import com.bookrental.api.security.model.UserRoleRequestDto;
import com.bookrental.api.security.model.UsersRoles;

public interface UserRoleService {
    UsersRoles save (UserRoleRequestDto userRole);
    UsersRoles getById(Long id);
}
