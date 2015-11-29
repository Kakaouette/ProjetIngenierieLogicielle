/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Page;

/**
 *
 * @author Arthur
 */
public class PageDAO extends Dao{
    public PageDAO(){}
    
    public void save(Page page) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(page);
        tx.commit();
    }

    public void update(Page page) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(page);
        tx.commit();
    }
    
    public void delete(Page page) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(page);
        tx.commit();
    }
    
    public Page getByJsp(String jsp){
        try {
            q = em.createQuery("SELECT P FROM Page P WHERE P.jsp = :JSP");
            q.setParameter("JSP", jsp);
            return (Page) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
