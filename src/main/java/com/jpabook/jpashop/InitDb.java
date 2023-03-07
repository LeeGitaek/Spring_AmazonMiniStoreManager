package com.jpabook.jpashop;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.hibernate.sql.ordering.antlr.OrderingSpecification.Ordering;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.Item.Book;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {
            Member member = new Member();
            member.setName("userA");
            member.setAddress(new Address("서울", "1", "1111"));
            em.persist(member);

            Book book1 = new Book();
            book1.setName("JPA1 BOOK");
            book1.setPrice(10000);
            book1.setStockQuantity(100);
            em.persist(book1);

            Book book2 = new Book();
            book2.setName("JPA2 BOOK");
            book2.setPrice(20000);
            book2.setStockQuantity(100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.creaOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.creaOrderItem(book2, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = new Member();
            member.setName("userB");
            member.setAddress(new Address("전주", "2", "114241"));
            em.persist(member);

            Book book3 = new Book();
            book3.setName("JPA3 BOOK");
            book3.setPrice(10000);
            book3.setStockQuantity(10);
            em.persist(book3);

            Book book4 = new Book();
            book4.setName("JPA4 BOOK");
            book4.setPrice(20000);
            book4.setStockQuantity(10);
            em.persist(book4);

            OrderItem orderItem3 = OrderItem.creaOrderItem(book3, 10000, 1);
            OrderItem orderItem4 = OrderItem.creaOrderItem(book4, 20000, 2);

            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem3, orderItem4);
            em.persist(order);
        }
    }
    
}
