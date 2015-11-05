/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * <b>Classe abstraite faisant le lien avec la BD</b>
 * <p>
 * Elle contient :
 * <ul>
 * <li>Un EntityManagerFactory (static final) servant à tous les thread (connexion BD)</li>
 * <li>Un EntityManager pour chaque instance (File d'attente pour envoi de la query)</li>
 * <li>Une Query pour faire des requêtes paramétrées</li>
 * </ul>
 * </p>
 * 
 * @author roulonn
 */
public abstract class Dao {

    /*static final la connexion est partagé entre toutes les instances sans modification possible*/
    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestULRPU");
    protected EntityManager em = null;
    protected Query q = null;

    Dao() {
        em = this.getEntityManager();
    }

    public EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }
}
