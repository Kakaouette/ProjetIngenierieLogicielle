/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeEtatDossier;

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
    
    public String getLastId(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat("ddMMyyyy");
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT D FROM Dossier D WHERE D.id LIKE :ID ORDER BY LENGTH(D.id) DESC, D.id DESC");
            q.setParameter("ID", "pst" + formater.format(date) + "%");
            List<Dossier> dossiers = (List<Dossier>) q.getResultList();
            if(dossiers.isEmpty()){return "";}
            return dossiers.get(0).getId();
        } catch (NoResultException e) {
            return "pst" + formater.format(date) + 0;
        }
    }
    
    public Dossier getByEtudiantAndFormation(Etudiant etudiant, Formation formation) {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT D FROM Dossier D WHERE D.etudiant = :ETUDIANT AND D.demandeFormation = :FORMATION");
            q.setParameter("ETUDIANT", etudiant);
            q.setParameter("FORMATION", formation);
            return (Dossier) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Dossier> getByEtudiant(Etudiant etu){
        try{
            em.clear();
            q = em.createQuery("SELECT D FROM Dossier D WHERE D.etudiant = :ETU");
            q.setParameter("ETU", etu);
            return q.getResultList();
        } catch (NoResultException e){
            return null;
        }
    }

    public void save(Dossier unDossier) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(unDossier);
        tx.commit();
    }
    
    public void delete(String idDossier) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(getById(idDossier));
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
    
    /**
     * Selection de les dossiers correspondant aux formations données dans la BD
     * 
     * @param listFormation Liste des formations qui seront utilisées pour récupérer les dossiers
     * @return List de dossier
     */
    public List<Dossier> SelectDossiersByFormation(List<Formation> listFormation) {
        try {
            List<Dossier> listDossier = new ArrayList<>();
            for(Formation f : listFormation){
                em.clear(); //supprime le cache des requêtes
                q = em.createQuery("SELECT D FROM Dossier D WHERE D.demandeFormation = :FORMATIONS");
                q.setParameter("FORMATIONS", f);
                listDossier.addAll((List<Dossier>) q.getResultList());
            }
            return listDossier;
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /**
     * Selection de les dossiers correspondant aux formations données dans la BD pour la commission
     * 
     * @param listFormation Liste des formations qui seront utilisées pour récupérer les dossiers
     * @return List de dossier
     */
    public List<Dossier> SelectDossiersForCommission(List<Formation> listFormation) {
        try {
            List<Dossier> listDossier = new ArrayList<>();
            for(Formation f : listFormation){
                em.clear(); //supprime le cache des requêtes
                q = em.createQuery("SELECT D FROM Dossier D WHERE D.demandeFormation = :FORMATION AND (D.etat = :NAVETTE OR D.etat = :EN_ATTENTE_COMMISSION)");
                q.setParameter("FORMATION", f);
                q.setParameter("NAVETTE", TypeEtatDossier.navette);
                q.setParameter("EN_ATTENTE_COMMISSION", TypeEtatDossier.en_attente_commission);
                listDossier.addAll((List<Dossier>) q.getResultList());
            }
            return listDossier;
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
    
    /**
     * Compte le nombre de dossiers
     * 
     * @return nombre de dossiers
     */
    public int count() {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT count(D) FROM Dossier D");
            return ((Long)q.getResultList().get(0)).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
    
    /**
     * Compte le nombre de dossiers selon l'etat
     * 
     * @return nombre de dossiers
     */
    public int count(TypeEtatDossier etat) {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT count(D) FROM Dossier D where D.etat=:etat");
            q.setParameter("etat", etat);
            return ((Long)q.getResultList().get(0)).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
    
    /**
     * Compte le nombre de dossiers selon l'etat et selon une periode
     * @param periode nombre de jour, = 30 ou 60.
     * @return nombre de dossiers
     */
    public int count(TypeEtatDossier etat, String periode) {
        try {
            em.clear(); //supprime le cache des requêtes
            String req = "SELECT count(D) FROM Dossier D where D.etat=:etat";
            Calendar calendar = new GregorianCalendar();
            switch(periode)
            {
                case "30j":
                    calendar.setTime(new Date());
                    calendar.add((GregorianCalendar.MONTH), -1);
                    q = em.createQuery("SELECT count(D) FROM Dossier D where D.etat=:etat AND D.date BETWEEN :startDate AND :endDate");
                    q.setParameter("endDate",new Date());
                    q.setParameter("startDate",calendar.getTime());
                    break;
                case "60j":
                    calendar.setTime(new Date());
                    calendar.add((GregorianCalendar.MONTH), -2);
                    q = em.createQuery("SELECT count(D) FROM Dossier D where D.etat=:etat AND D.date BETWEEN :startDate AND :endDate");
                    q.setParameter("endDate",new Date());
                    q.setParameter("startDate",calendar.getTime());
                    break;
            }
            q = em.createQuery("SELECT count(D) FROM Dossier D where D.etat=:etat");
            q.setParameter("etat", etat);
            return ((Long)q.getResultList().get(0)).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
    
    /**
     * Compte le nombre de dossiers par formation
     * 
     * @return nombre de dossiers
     */
    public int count(Formation formation) {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT count(D) FROM Dossier D where D.demandeFormation=:formation");
            q.setParameter("formation", formation);
            return ((Long)q.getResultList().get(0)).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
    
    /**
     * Compte le nombre de dossiers par formation et par etat
     * 
     * @return nombre de dossiers
     */
    public int count(Formation formation, TypeEtatDossier etat) {
        try {
            em.clear(); //supprime le cache des requêtes
            q = em.createQuery("SELECT count(D) FROM Dossier D where D.demandeFormation=:formation and D.etat=:etat");
            q.setParameter("formation",formation);
            q.setParameter("etat",etat);
            return ((Long)q.getResultList().get(0)).intValue();
        } catch (NoResultException e) {
            return 0;
        }
    }
}
