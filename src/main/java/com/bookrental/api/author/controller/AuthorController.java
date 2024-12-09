package com.bookrental.api.author.controller;

import com.bookrental.api.author.model.request.AuthorRequestDto;
import com.bookrental.api.author.service.AuthorService;
import com.bookrental.endpoints.EndPointConstants;
import com.bookrental.response.ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "AUTHOR APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping(EndPointConstants.AUTHOR)
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(EndPointConstants.SAVE)
    public ResponseEntity<ResponseDto> createAuthor(
            @Valid @RequestBody AuthorRequestDto authorRequestDto
    ) {
        var savedAuthor = authorService.saveAuthor(authorRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("Author", savedAuthor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseData));
    }

    @GetMapping(EndPointConstants.GET_ALL)
    public ResponseEntity<ResponseDto> getAllAuthors() {
        var authors = authorService.getAllAuthors();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("authors", authors);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping(EndPointConstants.GET_BY_ID)
    public ResponseEntity<ResponseDto> getAuthorById(@PathVariable Long id) {
        var author = authorService.getAuthorById(id);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("author", author);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @PutMapping(EndPointConstants.UPDATE)
    public ResponseEntity<ResponseDto> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDto authorRequestDto
    ) {
        var updatedAuthor = authorService.updateAuthor(id, authorRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("author", updatedAuthor);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @DeleteMapping(EndPointConstants.DELETE)
    public ResponseEntity<ResponseDto> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok(ResponseDto.success());
    }
}
