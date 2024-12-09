package com.bookrental.api.user.controller;

import com.bookrental.api.user.model.User;
import com.bookrental.api.user.model.requestDto.UserSignInDto;
import com.bookrental.api.user.service.UserService;
import com.bookrental.endpoints.EndPointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointConstants.USER)
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public User save(@RequestBody User user) {
        return userService.save(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody UserSignInDto userSignInDto) {
        return userService.verify(userSignInDto);
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }


}
