package com.bookrental.api.member.service;

import com.bookrental.api.member.model.entity.Member;
import com.bookrental.api.member.model.request.MemberRequestDto;

import java.util.List;

public interface MemberService {

    Member saveMember(MemberRequestDto authorRequestDto);


    List<Member> getAllMember();


    Member getMemberById(Long id);

    Member updateMember(Long id, MemberRequestDto authorRequestDto);


    void deleteMember(Long id);
}
