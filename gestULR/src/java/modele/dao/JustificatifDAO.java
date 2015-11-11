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
}
