package com.bookrental.api.author.service;

import com.bookrental.api.author.model.entity.Author;
import com.bookrental.api.author.model.request.AuthorRequestDto;

import java.util.List;

public interface AuthorService {

    Author saveAuthor(AuthorRequestDto authorRequestDto);


    List<Author> getAllAuthors();


    Author getAuthorById(Long id);

    Author updateAuthor(Long id, AuthorRequestDto authorRequestDto);


    void deleteAuthor(Long id);
}
