package com.bookrental.api.user.controller;

import com.bookrental.api.user.model.User;
import com.bookrental.api.user.model.requestDto.UserSignInDto;
import com.bookrental.api.user.service.UserService;
import com.bookrental.endpoints.EndPointConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointConstants.USER)
public class UserController {


    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> save(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserSignInDto userSignInDto) {
        String token = userService.verify(userSignInDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public ResponseEntity<User> getById(@PathVariable Long id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getById(id));

    }

}
