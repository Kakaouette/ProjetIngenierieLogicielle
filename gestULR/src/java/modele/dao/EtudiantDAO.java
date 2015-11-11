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

    public void save(Etudiant unEtudiant) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unEtudiant);
        tx.commit();
    }

    public void update(Etudiant unEtudiant) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unEtudiant);
        tx.commit();
    }
}
