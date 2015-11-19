/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Adresse;
import modele.entite.Compte;

/**
 * <b>Classe faisant le lien avec la BD pour la table Adresse</b>
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
 * @see Adresse
 * @author roulonn
 */
public class AdresseDAO extends Dao {

    public AdresseDAO(){}

    public Adresse getById(int idAdresse) {
        Adresse unAdresse = null;
        unAdresse = em.find(Adresse.class, idAdresse);

        return unAdresse;
    }

    public Adresse getAdresseByCodePostal(String codePostal){
        try {
            q = em.createQuery("SELECT A FROM Adresse A WHERE A.codePostal = :CP");
            q.setParameter("CP", codePostal);
            return (Adresse) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public void save(Adresse unAdresse) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unAdresse);
        tx.commit();
    }

    public void update(Adresse unAdresse) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(unAdresse);
        tx.commit();
    }
}
