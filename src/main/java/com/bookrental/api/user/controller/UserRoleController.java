package com.bookrental.api.user.controller;

import com.bookrental.api.user.model.requestDto.UserRoleRequestDto;
import com.bookrental.api.user.model.UsersRoles;
import com.bookrental.api.user.service.UserRoleService;
import com.bookrental.endpoints.EndPointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(EndPointConstants.USER_ROLE)
public class UserRoleController {
    private final UserRoleService userRoleService;

    @PostMapping(EndPointConstants.SAVE)
    public UsersRoles save(@RequestBody UserRoleRequestDto user) {
        return userRoleService.save(user);

    }


    @GetMapping(EndPointConstants.GET_BY_ID)
    public UsersRoles getById(@PathVariable Long id) {
        return userRoleService.getById(id);
    }
}
