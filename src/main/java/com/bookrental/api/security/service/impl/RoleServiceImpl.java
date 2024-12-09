package com.bookrental.api.security.service.impl;

import com.bookrental.api.security.model.Roles;
import com.bookrental.api.security.repository.RoleRepository;
import com.bookrental.api.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
