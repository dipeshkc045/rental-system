package com.bookrental.api.security.service;

import com.bookrental.api.security.model.User;
import com.bookrental.api.security.model.UserRegistrationDto;

public interface UserService {

    User save(User user);

    User getById(Long id);

    User registerUser(UserRegistrationDto registrationDto);

    String verify(User user);

}
