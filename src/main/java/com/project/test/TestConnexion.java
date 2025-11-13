package com.project.test;

import com.project.utils.JPAUtil;
import jakarta.persistence.EntityManager;

public class TestConnexion {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Connexion JPA OK !");
        em.close();
        JPAUtil.close();
    }
}
