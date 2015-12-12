/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Menu;

/**
 * <b>Classe faisant le lien avec la BD pour la table Menu</b>
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
 * @see Menu
 * @author roulonn
 */
public class MenuDAO extends Dao {

    public MenuDAO(){}

    public Menu getById(String idMenu) {
        Menu unMenu = null;
        unMenu = em.find(Menu.class, idMenu);

        return unMenu;
    }
    
    public void save(Menu unMenu) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unMenu);
        tx.commit();
    }

    public void update(Menu unMenu) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unMenu);
        tx.commit();
    }
    
    public List<Menu> SelectAllRoot() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT M FROM Menu M WHERE M.menuSuperieur IS NULL");
            return (List<Menu>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Menu> SelectAllSubMenu(Menu menuSup) {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT M FROM Menu M WHERE M.menuSuperieur = :menuSup");
            q.setParameter("menuSup", menuSup);
            return (List<Menu>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Menu> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT M FROM Menu M");
            return (List<Menu>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
