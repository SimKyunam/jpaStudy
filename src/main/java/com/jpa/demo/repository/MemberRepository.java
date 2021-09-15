package com.jpa.demo.repository;

import com.jpa.demo.domain.ShopMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public void save(ShopMember member) {
        em.persist(member);
    }

    public ShopMember findOne(Long id) {
        return em.find(ShopMember.class, id);
    }

    public List<ShopMember> findAll() {
        return em.createQuery("select m from ShopMember m", ShopMember.class)
                .getResultList();
    }

    public List<ShopMember> findByName(String name) {
        return em.createQuery("select m from ShopMember m where m.name = :name", ShopMember.class)
                .setParameter("name", name)
                .getResultList();
    }
}
