/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Historique;
import modele.entite.TypeDossier;
import modele.entite.TypeEtatDossier;

/**
 *
 * @author Arthur
 */
public class DossierService {
    DossierDAO dossierDAO;

    public DossierService() {
        this.dossierDAO = new DossierDAO();
    }
    
    /**
     * @param dossier
     * @return true: dossier exist après l'opération; false: dossier n'existe pas après l'opération
     */
    public boolean ajouterDossier(Dossier dossier){
        dossierDAO.save(dossier);
        return dossierDAO.getById(dossier.getId()) != null;
    }
    
    /**
     * @return regex de l'id d'un dossier
     */
    public String getRegexIdDossier(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        String regex="";
        
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            regex=properties.getProperty("creerDossier.idDossier");
        } catch (IOException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return regex;
    }
    
    public Dossier recupererDossier(String idDossier)
    {
        return this.dossierDAO.getById(idDossier);
    }
    
    /**
     * 
     * @param dossierorigin 
     * @param admDossier 
     * @param etatDossier 
     * @param dateDossier 
     * @param dateHisto 
     * @param messageHisto 
     * @param lettreDossier 
     */
    public void modifierDossier(Dossier dossierorigin, TypeDossier admDossier, TypeEtatDossier etatDossier, String dateDossier, String lettreDossier, String messageHisto, String dateHisto){
        dossierorigin.setAdmissible(admDossier);
        dossierorigin.setEtat(etatDossier);
        dossierorigin.setDate(new Date(dateDossier));
        dossierorigin.setLettre(lettreDossier);
        Historique histo=new Historique();
        histo.setMessage(messageHisto);
        histo.setDate(new Date(dateHisto));
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier=dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        dossierDAO.update(dossierorigin);
    }
    
    /**
     * Mise à jour du dossier sans paramètre
     * @param dossier
     */
    public void miseajour(Dossier dossier){
        dossierDAO.update(dossier);
    }
}