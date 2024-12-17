package com.bookrental.api.user.controller;

import com.bookrental.api.user.model.UsersRoles;
import com.bookrental.api.user.model.requestDto.UserRoleRequestDto;
import com.bookrental.api.user.service.UserRoleService;
import com.bookrental.endpoints.EndPointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndPointConstants.USER_ROLE)
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping(EndPointConstants.SAVE)
    public ResponseEntity<UsersRoles> save(@RequestBody UserRoleRequestDto userRoleRequestDto) {
        UsersRoles savedRole = userRoleService.save(userRoleRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRole);
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public ResponseEntity<UsersRoles> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userRoleService.getById(id));
    }
}
