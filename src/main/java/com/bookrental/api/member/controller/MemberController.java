package com.bookrental.api.member.controller;

import com.bookrental.api.member.model.request.MemberRequestDto;
import com.bookrental.api.member.service.MemberService;
import com.bookrental.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<ResponseDto> createMember(
            @Valid @RequestBody MemberRequestDto memberRequestDto
    ) {
        var savedMember = memberService.saveMember(memberRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("memberId", savedMember.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(responseData));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<ResponseDto> getAllMembers() {
        var members = memberService.getAllMember();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("members", members);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getMemberById(@PathVariable Long id) {
        var member = memberService.getMemberById(id);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("member", member);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public ResponseEntity<ResponseDto> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto memberRequestDto
    ) {
        var updatedMember = memberService.updateMember(id, memberRequestDto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("member", updatedMember);

        return ResponseEntity.ok(ResponseDto.success(responseData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(ResponseDto.success());
    }

}
