package com.jpabook.jpashop;

import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	// controller, web = 웹 계층
	// service = 비즈니스 로직, 트랜잭션 처리 
	// repository = JPA를 직접 사용하는 계층, 엔티티 매니저 사용 
	// domain = 엔티티가 모여있는 계층, 모든 계층에서 사용 

	// domain
	// exception
	// repository 
	// service
	// web 

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

}
