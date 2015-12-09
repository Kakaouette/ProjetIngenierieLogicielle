/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Etudiant;

/**
 * <b>Classe faisant le lien avec la BD pour la table Etudiant</b>
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
 * @see Etudiant
 * @author roulonn
 */
public class EtudiantDAO extends Dao {

    public EtudiantDAO(){}

    public Etudiant getById(int idEtudiant) {
        Etudiant unEtudiant = null;
        unEtudiant = em.find(Etudiant.class, idEtudiant);

        return unEtudiant;
    }
    
    public Etudiant getEtudiantByINE(String ine){
        try {
            q = em.createQuery("SELECT E FROM Etudiant E WHERE E.ine = :INE");
            q.setParameter("INE", ine);
            return (Etudiant) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Etudiant getEtudiantByNomPrenom(String nom, String prenom){
        try {
            q = em.createQuery("SELECT E FROM Etudiant E WHERE E.nom = :NOM AND E.prenom = :PRENOM");
            q.setParameter("NOM", nom);
            q.setParameter("PRENOM", prenom);
            return (Etudiant) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(Etudiant unEtudiant) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unEtudiant);
        tx.commit();
    }

    public void delete(int idEtudiant) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(getById(idEtudiant));
        tx.commit();
    }
    
    public void update(Etudiant unEtudiant) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unEtudiant);
        tx.commit();
    }
}
