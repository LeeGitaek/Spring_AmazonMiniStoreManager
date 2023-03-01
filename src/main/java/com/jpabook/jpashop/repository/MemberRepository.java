package com.jpabook.jpashop.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Member;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) { em.persist(member); }

    public Member findOne(Long id) { return em.find(Member.class, id); }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
