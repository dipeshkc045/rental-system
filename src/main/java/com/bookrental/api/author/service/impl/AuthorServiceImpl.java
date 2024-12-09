package com.bookrental.api.author.service.impl;

import com.bookrental.api.author.model.entity.Author;
import com.bookrental.api.author.model.request.AuthorRequestDto;
import com.bookrental.api.author.repository.AuthorRepository;
import com.bookrental.api.author.service.AuthorService;
import com.bookrental.resourceconverter.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;


    @Override
    public Author saveAuthor(AuthorRequestDto authorRequestDto) {
        Author author = modelMapper.map(authorRequestDto, Author.class);
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author updateAuthor(Long id, AuthorRequestDto bookingRequestDto) {
        Author book = authorRepository.findById(id).orElse(null);
        assert book != null;
        Author updatedBook = Author.builder()
                .id(book.getId())
                .name(bookingRequestDto.getName() != null ? bookingRequestDto.getName() : book.getName())
                .build();
        return authorRepository.save(updatedBook);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author existingBook = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        authorRepository.deleteById(existingBook.getId());

    }
}
