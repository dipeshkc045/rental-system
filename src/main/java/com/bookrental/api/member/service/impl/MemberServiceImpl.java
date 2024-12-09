package com.bookrental.api.member.service.impl;

import com.bookrental.api.member.model.entity.Member;
import com.bookrental.api.member.model.request.MemberRequestDto;
import com.bookrental.api.member.repository.MemberRepository;
import com.bookrental.api.member.service.MemberService;
import com.bookrental.resourceconverter.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final GenericMapper genericMapper;


    @Override
    public Member saveMember(MemberRequestDto memberRequestDto) {
        Member member = genericMapper.convert(memberRequestDto, Member.class);
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public Member updateMember(Long id, MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(id).orElse(null);
        assert member != null;
        Member updatedMember = Member.builder()
                .id(member.getId())
                .name(memberRequestDto.getName() != null ? memberRequestDto.getName() : member.getName())
                .build();
        return memberRepository.save(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member existingBook = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        memberRepository.deleteById(existingBook.getId());

    }
}
