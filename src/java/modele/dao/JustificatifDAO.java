/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Justificatif;

/**
 * <b>Classe faisant le lien avec la BD pour la table Justificatif</b>
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
 * @see Justificatif
 * @author roulonn
 */
public class JustificatifDAO extends Dao {

    public JustificatifDAO(){}

    public Justificatif getById(int idJustificatif) {
        Justificatif unJustificatif = null;
        unJustificatif = em.find(Justificatif.class, idJustificatif);

        return unJustificatif;
    }
    
    public Justificatif getJustificatifbyTitre(String titre){
        try {
            q = em.createQuery("SELECT J FROM Justificatif J WHERE J.titre = :TITRE");
            q.setParameter("TITRE", titre);
            return (Justificatif) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Justificatif unJustificatif) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unJustificatif);
        tx.commit();
    }

    public void update(Justificatif unJustificatif) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unJustificatif);
        tx.commit();
    }
    
    /**
     * Selection de tous les justificatifs dans la BDD
     * 
     * @return List de formation
     */
    public List<Justificatif> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT J FROM Justificatif J");
            return (List<Justificatif>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
