/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.entite.Dossier;

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
}