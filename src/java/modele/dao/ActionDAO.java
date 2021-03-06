/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Action;

/**
 * <b>Classe faisant le lien avec la BD pour la table Action</b>
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
 * @see Action
 * @author roulonn
 */
public class ActionDAO extends Dao {

    public ActionDAO(){}

    public Action getById(String idAction) {
        Action unAction = null;
        unAction = em.find(Action.class, idAction);

        return unAction;
    }
    
    public void save(Action unAction) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unAction);
        tx.commit();
    }

    public void update(Action unAction) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unAction);
        tx.commit();
    }
    
    /**
     * Selection de toutes les actions dans la BD
     * 
     * @return List d'action
     */
    public List<Action> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT A FROM Action A");
            return (List<Action>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
