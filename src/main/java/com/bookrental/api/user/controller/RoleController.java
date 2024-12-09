package com.bookrental.api.user.controller;

import com.bookrental.api.user.model.Roles;
import com.bookrental.api.user.service.RoleService;
import com.bookrental.endpoints.EndPointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndPointConstants.ROLE)
public class RoleController {

private final RoleService roleService;
    @PostMapping("/save")
    public Roles save(@RequestBody Roles roles) {
        return roleService.save(roles);

    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public Roles getById(@PathVariable Long id) {
        return roleService.getById(id);
    }
}
