package com.jpabook.jpashop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.service.MemberService;

@SpringBootTest
@Transactional
class MemberServiceTest {
  

    @Autowired
    MemberService memberService;

    @Autowired 
    MemberRepository memberRepository;

    @Test
    void 회원가입() throws Exception {
        // given 
        Member member = new Member(); 
        member.setName("kim");

        // when 
        Long savedId = memberService.join(member);

        // then 
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    void 중복_회원_예외() throws Exception {
        String name = "kim";

		Member memberA = new Member();
		memberA.setName(name);

		Member memberB = new Member();
		memberB.setName(name);

		//when
		memberService.join(memberA);

		//then
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(memberB));
		assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
    }
}
