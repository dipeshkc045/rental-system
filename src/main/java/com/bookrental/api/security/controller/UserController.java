package com.bookrental.api.security.controller;

import com.bookrental.api.security.model.User;
import com.bookrental.api.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public User save(@RequestBody User user) {
        return userService.save(user);

    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.verify(user);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }


}
