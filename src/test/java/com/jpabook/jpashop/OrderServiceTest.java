package com.jpabook.jpashop;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    
    @Autowired
    OrderService orderService;

    @Autowired 
    OrderRepository orderRepository;

    @Test 
    public void 상품주문() throws Exception {
        // given 
        Member member = createMember();
        Book book = createBook("시골 jpa", 10000, 10);

        int orderCount = 2;

        // when 
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then 
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 order ");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류수가 정확해야한다. ");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다. ");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다. ");
    }

    @Test
    public void 주문취소() throws Exception {
        // given 
        Member member = createMember();
        Book item = createBook("jpa", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(),item.getId(), orderCount);

        // when 
        orderService.cancelOrder(orderId);

        // then 
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 cancel 이다");
        assertEquals(10, item.getStockQuantity() , "주문이 취소된 상품은 그만큼 재고가 증가해야한다.");
        
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {

        try {
            // given 
            Member member = createMember();
            Item item = createBook("시골 jpa", 10000, 10);

            int orderCount = 11;
            // when 
            orderService.order(member.getId(), item.getId(), orderCount);
        } catch(NotEnoughStockException e) {
            assertTrue(true);
        }
      
    }

    public Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "한강", "16628"));
        em.persist(member);
        return member;
    }

    public Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

}
