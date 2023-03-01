package com.jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    
    private final EntityManager em; 

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    /**
     * @param orderSearch
     * @return
     */
    public List<Order> findAll(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m where o.status = :status or m.name = :name";

        return em.createQuery(jpql, Order.class)
                                .setParameter("status", orderSearch.getOrderStatus())
                                .setParameter("name", orderSearch.getMemberName())
                                .setMaxResults(1000)
                                .getResultList();
    }
}
