package com.bookrental.api.user.service.impl;

import com.bookrental.api.user.model.Roles;
import com.bookrental.api.user.model.User;
import com.bookrental.api.user.model.requestDto.UserRoleRequestDto;
import com.bookrental.api.user.model.UsersRoles;
import com.bookrental.api.user.repository.RoleRepository;
import com.bookrental.api.user.repository.UserRepository;
import com.bookrental.api.user.repository.UserRoleRepository;
import com.bookrental.api.user.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
