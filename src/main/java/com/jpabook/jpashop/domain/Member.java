package com.jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
    
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @JsonIgnore
    @Embedded // 내장타입
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member") // 매핑된 것.
    private List<Order> orders = new ArrayList<>();
}
