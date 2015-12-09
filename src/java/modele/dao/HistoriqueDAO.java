/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Historique;

/**
 * <b>Classe faisant le lien avec la BD pour la table Historique</b>
 * <p>
 * Elle hérite de DAO, gérer par JPA et permet de faire des opérations simple
 * sur les tables :
 * <ul>
 * <li>INSERT</li>
 * <li>SELECT</li>
 * <li>UPDATE</li>
 * <li>DELETE</li>
 * </ul>
 * </p>
 *
 * @see Dao
 * @see Historique
 * @author roulonn
 */
public class HistoriqueDAO extends Dao {

    public HistoriqueDAO() {
    }

    public Historique getById(int idHistorique) {
        Historique unHistorique = null;
        unHistorique = em.find(Historique.class, idHistorique);

        return unHistorique;
    }

    public Historique getHistoriqueByMessage(String msg) {
        try {
            q = em.createQuery("SELECT H FROM Historique H WHERE H.message = :MSG");
            q.setParameter("MSG", msg);
            return (Historique) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Historique> getHistoriquesByCompte(Compte compte) {
        try {
            q = em.createQuery("SELECT H FROM Historique H WHERE H.compte = :COMPTE");
            q.setParameter("COMPTE", compte);
            return (List<Historique>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Historique unHistorique) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unHistorique);
        tx.commit();
    }

    public void update(Historique unHistorique) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unHistorique);
        tx.commit();
    }

    public void delete(Historique unHistorique) {
        DossierDAO dossierDAO = new DossierDAO();
        Dossier d = dossierDAO.getByHistorique(unHistorique);
        if (d != null) {
            if (d.getHistorique() != null) {
                if (d.getHistorique().contains(unHistorique)) {
                    d.getHistorique().remove(unHistorique);
                    dossierDAO.update(d);
                }
            }
        }

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(unHistorique);
        tx.commit();
    }

    public void delete(int idHistorique) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(getById(idHistorique));
        tx.commit();
    }
}
