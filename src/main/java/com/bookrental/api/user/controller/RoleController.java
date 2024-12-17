package com.bookrental.api.user.controller;

import com.bookrental.api.user.model.Roles;
import com.bookrental.api.user.service.RoleService;
import com.bookrental.endpoints.EndPointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndPointConstants.ROLE)
public class RoleController {

private final RoleService roleService;
    @PostMapping("/save")
    public ResponseEntity<Roles> save(@RequestBody Roles roles) {
        Roles savedRoles = roleService.save(roles);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRoles);
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public ResponseEntity<Roles> getById(@PathVariable Long id) {
        Roles roles = roleService.getById(id);
        return ResponseEntity
                .ok()
                .body(roles);
    }
}
