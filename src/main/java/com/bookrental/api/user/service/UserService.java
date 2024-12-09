package com.bookrental.api.user.service;

import com.bookrental.api.user.model.User;
import com.bookrental.api.user.model.requestDto.UserSignInDto;

public interface UserService {

    User save(User user);
    User getById(Long id);
    User getByUsername(String username);
    String verify(UserSignInDto user);

}
