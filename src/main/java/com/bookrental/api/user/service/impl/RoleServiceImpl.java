package com.bookrental.api.user.service.impl;

import com.bookrental.api.user.model.Roles;
import com.bookrental.api.user.repository.RoleRepository;
import com.bookrental.api.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Roles save(Roles roles) {
        return roleRepository.save(roles);
    }

    @Override
    public Roles getById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
