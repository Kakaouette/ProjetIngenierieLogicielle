/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Page;

/**
 * <b>Classe faisant le lien avec la BD pour la table Page</b>
 * <p>
 * Elle hérite de DAO, gérer par JPA et permet de faire des opérations simple sur les tables :
 * <ul>
 * <li>INSERT</li>
 * <li>SELECT</li>
 * <li>UPDATE</li>
 * <li>DELETE</li>
 * </ul>
 * </p>
 * 
 * @see Dao
 * @see Page
 * @author roulonn
 */
public class PageDAO extends Dao {

    public PageDAO(){}

    public Page getById(String idPage) {
        Page unPage = null;
        unPage = em.find(Page.class, idPage);

        return unPage;
    }
    
    public void save(Page unPage) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unPage);
        tx.commit();
    }

    public void update(Page unPage) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unPage);
        tx.commit();
    }
    
    public List<Page> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT P FROM Page P");
            return (List<Page>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
