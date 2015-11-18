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

/**
 * <b>Classe faisant le lien avec la BD pour la table Compte</b>
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
 * @see Compte
 * @author roulonn
 */
public class CompteDAO extends Dao {

    public CompteDAO(){}

    public Compte getById(int idCompte) {
        Compte unCompte = null;
        unCompte = em.find(Compte.class, idCompte);

        return unCompte;
    }

    public void save(Compte unCompte) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unCompte);
        tx.commit();
    }

    public void update(Compte unCompte) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unCompte);
        tx.commit();
    }
    
    /**
     * Selection d'un compte en fonction de son identifiant et de son mot de passe (crypté)
     * 
     * @param identifiant
     * @return Compte si il existe
     */
    public Compte getComptebyIdentifiant(String identifiant){
        try {
            q = em.createQuery("SELECT C FROM Compte C WHERE C.login = :ID");
            q.setParameter("ID", identifiant);
            return (Compte) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Compte getComptebyIdentifiant(String identifiant, String mdp){
        try {
            q = em.createQuery("SELECT C FROM Compte C WHERE C.login = :ID AND C.mdp = :MDP");
            q.setParameter("ID", identifiant);
            q.setParameter("MDP", mdp);
            return (Compte) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Selection de tous les comptes dans la BD
     * 
     * @return List de compte
     */
    public List<Compte> SelectAll() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT C FROM Compte C");
            return (List<Compte>) q.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
