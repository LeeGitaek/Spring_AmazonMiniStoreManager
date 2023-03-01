package com.jpabook.jpashop.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.jpabook.jpashop.domain.Item.Item;

import java.util.List;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
            // update 비슷함.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    /**
     * @return
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
