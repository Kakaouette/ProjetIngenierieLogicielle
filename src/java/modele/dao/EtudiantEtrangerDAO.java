/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.EtudiantEtranger;

/**
 * <b>Classe faisant le lien avec la BD pour la table EtudiantEtranger</b>
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
 * @see EtudiantEtranger
 * @author roulonn
 */
public class EtudiantEtrangerDAO extends Dao {

    public EtudiantEtrangerDAO(){}

    public EtudiantEtranger getById(int idEtudiantEtranger) {
        EtudiantEtranger unEtudiantEtranger = null;
        unEtudiantEtranger = em.find(EtudiantEtranger.class, idEtudiantEtranger);

        return unEtudiantEtranger;
    }
    
    public EtudiantEtranger getEtudiantEtrangerByNomPrenom(String nom, String prenom){
        try {
            q = em.createQuery("SELECT E FROM Etudiant E WHERE E.nom = :NOM AND E.prenom = :PRENOM");
            q.setParameter("NOM", nom);
            q.setParameter("PRENOM", prenom);
            return (EtudiantEtranger) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(EtudiantEtranger unEtudiantEtranger) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unEtudiantEtranger);
        tx.commit();
    }

    public void update(EtudiantEtranger unEtudiantEtranger) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unEtudiantEtranger);
        tx.commit();
    }
}
