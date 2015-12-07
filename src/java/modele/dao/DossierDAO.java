/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Dossier;
import modele.entite.Historique;

/**
 * <b>Classe faisant le lien avec la BD pour la table Dossier</b>
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
 * @see Dossier
 * @author roulonn
 */
public class DossierDAO extends Dao {

    public DossierDAO(){}

    public Dossier getById(String idDossier) {
        Dossier unDossier = null;
        unDossier = em.find(Dossier.class, idDossier);

        return unDossier;
    }

    public void save(Dossier unDossier) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unDossier);
        tx.commit();
    }

    public void update(Dossier unDossier) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unDossier);
        tx.commit();
    }
    
    /**
     * Selection de tous les dossiers dans la BD
     * 
     * @return List de dossier
     */
    public List<Dossier> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT D FROM Dossier D");
            return (List<Dossier>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Dossier getByHistorique(Historique h) {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT D FROM Dossier D WHERE :HIS MEMBER OF D.historique");
            q.setParameter("HIS", h);
            return (Dossier) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
