package com.bookrental.api.security.controller;

import com.bookrental.api.security.model.UserRoleRequestDto;
import com.bookrental.api.security.model.UsersRoles;
import com.bookrental.api.security.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("user-role")
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping("/save")
    public UsersRoles save(@RequestBody UserRoleRequestDto user) {
        return userRoleService.save(user);

    }


    @PostMapping("/{id}")
    public UsersRoles getById(@PathVariable Long id) {
        return userRoleService.getById(id);
    }
}
