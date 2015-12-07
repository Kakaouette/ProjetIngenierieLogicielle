/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exception.AjoutDossierInvalideException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modele.dao.AdresseDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.entite.Dossier;
import modele.entite.Historique;
import service.exception.AjoutAdresseInvalideException;
import service.exception.AjoutEtudiantInvalideException;
import service.exception.AjoutHistoriqueInvalideException;

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
     * @return ID de dossier valide non utilisé dans la BDD
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
     * @throws service.exception.AjoutDossierInvalideException
     * @throws java.io.IOException
     * @throws service.exception.AjoutAdresseInvalideException
     * @throws service.exception.AjoutEtudiantInvalideException
     * @throws service.exception.AjoutHistoriqueInvalideException
     */
    public void ajouterDossier(Dossier dossier) throws service.exception.AjoutDossierInvalideException, IOException, AjoutAdresseInvalideException, AjoutEtudiantInvalideException, AjoutHistoriqueInvalideException{
        //verification de la validité de la demande
        if(dossier == null){
            throw new AjoutDossierInvalideException("Requête incorrecte.", new Throwable(AjoutDossierInvalideException.cause.Dossier_Null.toString()));
        }
        if(dossier.getId() == null){
            throw new AjoutDossierInvalideException("L'identifiant du dossier est invalide", new Throwable(AjoutDossierInvalideException.cause.ID_Invalide.toString()));
        }else if(!regexIdDossierValide(dossier.getId())){ //verif id correspond au regex
            throw new AjoutDossierInvalideException("L'identifiant du dossier est invalide", new Throwable(AjoutDossierInvalideException.cause.ID_Invalide.toString()));
        }
        if(dossierDAO.getById(dossier.getId()) != null){ //verif id non utilisé
            throw new AjoutDossierInvalideException("L'identifiant du dossier est déjà utilisé", new Throwable(AjoutDossierInvalideException.cause.ID_Invalide.toString()));
        }
        if(dossier.getEtudiant() == null || dossier.getDemandeFormation() == null || dossier.getEtat() == null){ //verif champs requis remplis
            throw new AjoutDossierInvalideException("Dossier incomplet", new Throwable(AjoutDossierInvalideException.cause.Dossier_Incomplet.toString()));
        }
        if(new FormationDAO().getById(dossier.getDemandeFormation().getId()) == null){
            throw new AjoutDossierInvalideException("Formation inexistante", new Throwable(AjoutDossierInvalideException.cause.Formation_Inexistante.toString()));
        }
        if(dossierDAO.getByEtudiantAndFormation(dossier.getEtudiant(), dossier.getDemandeFormation()) != null){ //verif dossier existant
            throw new AjoutDossierInvalideException("Un dossier pour cet formation existe déjà pour cet étudiant", new Throwable(AjoutDossierInvalideException.cause.Dossier_Existant.toString())); //Le dossier existe déjà !
        }
        
        //ajout des entitées inexistante
        if(new AdresseDAO().getById(dossier.getEtudiant().getAdresse().getId()) == null){
            new AdresseService().ajouterAdresse(dossier.getEtudiant().getAdresse()); //ajout dans la base de donnée
        }
        if(new EtudiantDAO().getById(dossier.getEtudiant().getId()) == null){
            new EtudiantService().ajouterEtudiant(dossier.getEtudiant()); //ajout dans la base de donnée
        }
        if(dossier.getHistorique() != null){
            for(Historique historique:dossier.getHistorique()){
                new HistoriqueService().ajouterHistorique(historique); //ajout dans la base de donnée
            }
        }
        
        //sauvegarde du dossier dans la BDD
        dossierDAO.save(dossier);
    }
    
    /**
     * @param idDossier: id à verifier
     * @return true: id valide par rapport au regex; false: else
     * @throws java.io.IOException
     */
    public boolean regexIdDossierValide(String idDossier) throws IOException{      
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
     * @throws java.io.IOException
     */
    public String getRegexIdDossier() throws IOException{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        String regex="";
        
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            regex=properties.getProperty("creerDossier.idDossier");
        } catch (IOException ex) {
            throw new IOException("Propriété regex pour l'ID d'un dossier introuvable", ex.getCause());
        }
        return regex;
    }
}
