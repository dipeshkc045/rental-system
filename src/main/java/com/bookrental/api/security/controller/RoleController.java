package com.bookrental.api.security.controller;

import com.bookrental.api.security.model.Roles;
import com.bookrental.api.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("role")
public class RoleController {

private final RoleService roleService;
    @PostMapping("/save")
    public Roles save(@RequestBody Roles roles) {
        return roleService.save(roles);

    }

    @PostMapping("/{id}")
    public Roles getById(@PathVariable Long id) {
        return roleService.getById(id);
    }
}
