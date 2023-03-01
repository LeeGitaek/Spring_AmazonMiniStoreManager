package com.jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpabook.jpashop.domain.Delivery;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
     * order
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // delivery 
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // create order item 
        OrderItem orderItem = OrderItem.creaOrderItem(item, item.getPrice(), count);

        // create order 
        Order order = Order.createOrder(member, delivery, orderItem);

        // save order
        orderRepository.save(order);
        return order.getId();
    }

    /*
     * cancel order
     */

    @Transactional
    public void cancelOrder(Long orderId) {
        // search 
        Order order = orderRepository.findOne(orderId);
        // cancel  
        order.cancel();
    }

    /*
     * search
     */

     public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
     }
}
