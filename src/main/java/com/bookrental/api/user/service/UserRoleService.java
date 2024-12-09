package com.bookrental.api.user.service;

import com.bookrental.api.user.model.requestDto.UserRoleRequestDto;
import com.bookrental.api.user.model.UsersRoles;

public interface UserRoleService {
    UsersRoles save (UserRoleRequestDto userRole);
    UsersRoles getById(Long id);
}
