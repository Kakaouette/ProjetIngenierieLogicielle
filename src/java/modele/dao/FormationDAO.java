/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Formation;

/**
 * <b>Classe faisant le lien avec la BD pour la table Formation</b>
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
 * @see Formation
 * @author roulonn
 */
public class FormationDAO extends Dao {

    public FormationDAO(){}

    public Formation getById(int idFormation) {
        Formation unFormation = null;
        unFormation = em.find(Formation.class, idFormation);

        return unFormation;
    }
    
    public Formation getFormationByIntitule(String intitule){
        try {
            q = em.createQuery("SELECT F FROM Formation F WHERE F.intitule = :INT");
            q.setParameter("INT", intitule);
            return (Formation) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Formation unFormation) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unFormation);
        tx.commit();
    }

    public void update(Formation unFormation) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unFormation);
        tx.commit();
    }
    
    /**
     * Selection de toutes les formations dans la BD
     * 
     * @return List de formation
     */
    public List<Formation> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT F FROM Formation F ORDER BY F.intitule ASC");
            return (List<Formation>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
