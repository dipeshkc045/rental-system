package com.bookrental.api.security.service.impl;

import com.bookrental.api.security.model.Roles;
import com.bookrental.api.security.model.User;
import com.bookrental.api.security.model.UserRoleRequestDto;
import com.bookrental.api.security.model.UsersRoles;
import com.bookrental.api.security.repository.RoleRepository;
import com.bookrental.api.security.repository.UserRepository;
import com.bookrental.api.security.repository.UserRoleRepository;
import com.bookrental.api.security.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UsersRoles save(UserRoleRequestDto userRole) {
        User user = userRepository.findById(userRole.getUserId()).orElse(null);
        Roles role = roleRepository.findById(userRole.getRoleId()).orElse(null);
        return userRoleRepository.save(UsersRoles.builder().isActive(userRole.getIsActive()).role(role).user(user).build());
    }

    @Override
    public UsersRoles getById(Long id) {
        return userRoleRepository.findById(id).orElse(null);
    }
}
