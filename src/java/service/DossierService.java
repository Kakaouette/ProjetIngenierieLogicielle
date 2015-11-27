/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modele.dao.AdresseDAO;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
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
     * @return ID de dossier valide
     */
    public String getNewID(){
        Date dateNow = new Date(); //recuperation de la date actuelle
        SimpleDateFormat formater = new SimpleDateFormat("ddMMyyyy");
        
        String lastId = dossierDAO.getLastId(dateNow);
        String newID = "pst" + formater.format(dateNow);
        if(!lastId.equals("")){
            newID += Integer.parseInt(lastId.substring(newID.length()))+1;
        }else{
            newID += 0;
        }
        return newID;
    }
    
    /**
     * @param dossier à ajouter
     * @throws service.AjoutDossierInvalideException
     */
    public void ajouterDossier(Dossier dossier) throws service.AjoutDossierInvalideException{
        //verification de la validité de la demande
        if(regexIdDossierValide(dossier.getId())){ //verif id correspond au regex
            throw new AjoutDossierInvalideException("L'identifiant du dossier est invalide", new Throwable(AjoutDossierInvalideException.cause.ID_Invalide.toString()));
        }
        if(new DossierDAO().getById(dossier.getId()) != null){ //verif id non utilisé
            throw new AjoutDossierInvalideException("L'identifiant du dossier est déjà utilisé", new Throwable(AjoutDossierInvalideException.cause.ID_Invalide.toString()));
        }
        if(new DossierDAO().getByEtudiantAndFormation(dossier.getEtudiant(), dossier.getDemandeFormation()) != null){ //verif dossier existant
            throw new AjoutDossierInvalideException("Un dossier pour cet formation existe déjà pour cet étudiant", new Throwable(AjoutDossierInvalideException.cause.Dossier_Existant.toString())); //Le dossier existe déjà !
        }
        
        //ajout des entitées inexistante
        if(new AdresseDAO().getById(dossier.getEtudiant().getAdresse().getId()) == null){
            new AdresseDAO().save(dossier.getEtudiant().getAdresse()); //ajout dans la base de donnée
        }
        if(new EtudiantDAO().getById(dossier.getEtudiant().getId()) == null){
            new EtudiantDAO().save(dossier.getEtudiant()); //ajout dans la base de donnée
        }
        
        //sauvegarde du dossier dans la BDD
        dossierDAO.save(dossier);
    }
    
    /**
     * @param idDossier: id à verifié
     * @return true: id valide par rapport au regex; false: else
     */
    public boolean regexIdDossierValide(String idDossier){      
        // compilation de la regex
        Pattern p = Pattern.compile(getRegexIdDossier());
        // création d'un moteur de recherche
        Matcher m = p.matcher(idDossier);
        // lancement de la recherche de toutes les occurrences
        boolean b = m.matches();

        if(b){ // si recherche fructueuse
            for(int i=0; i <= m.groupCount(); i++) { // pour chaque groupe
                if(m.group(i).equals(idDossier)){
                    return true;
                }
            }
        }
        return false;
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
