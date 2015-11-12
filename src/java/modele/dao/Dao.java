/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * <b>Classe abstraite faisant le lien avec la BD</b>
 * <p>
 * Elle contient :
 * <ul>
 * <li>Un EntityManagerFactory (static final) servant à tous les thread
 * (connexion BD)</li>
 * <li>Un EntityManager pour chaque instance (File d'attente pour envoi de la
 * query)</li>
 * <li>Une Query pour faire des requêtes paramétrées</li>
 * </ul>
 * </p>
 *
 * @author roulonn
 */
public abstract class Dao {
    /**
     * Charge le fichier properties contenant l'url, le nom de compte, le mot de passe et le driver de la base de données
     * @return Propriété chargé
     */
    static Properties getConfigurationProperties() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            properties.setProperty("javax.persistence.jdbc.driver", properties.getProperty("db.driver"));
            properties.setProperty("javax.persistence.jdbc.url", properties.getProperty("db.url"));
            properties.setProperty("javax.persistence.jdbc.user", properties.getProperty("db.user"));
            properties.setProperty("javax.persistence.jdbc.password", properties.getProperty("db.password"));
        } catch (IOException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
    /*static final la connexion est partagé entre toutes les instances sans modification possible, getConfigurationProperties récupère l'url, nom de compte,... présent dans le fichier properties*/
    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestULRPU", getConfigurationProperties());
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
