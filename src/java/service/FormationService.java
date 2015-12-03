/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modele.dao.FormationDAO;
import modele.dao.JustificatifDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;

/**
 *
 * @author Arthur
 */
public class FormationService {
    FormationDAO formationDAO;

    public FormationService() {
        this.formationDAO = new FormationDAO();
    }
    
    /**
     * 
     * @param formation: formation à ajouter
     * @throws AjoutFormationInvalideException 
     */
    public void ajouterFormation(Formation formation) throws AjoutFormationInvalideException{
        //verification de la validité de la demande
        if(formation == null){
            throw new AjoutFormationInvalideException("Requête incorrecte.", new Throwable(AjoutFormationInvalideException.cause.Formation_Vide.toString()));
        }
        if(formation.getIntitule().isEmpty()){
            throw new AjoutFormationInvalideException("Intitulé non rempli.", new Throwable(AjoutFormationInvalideException.cause.Intitule_Vide.toString()));
        }
        if(formationDAO.getFormationByIntitule(formation.getIntitule()) != null){
            throw new AjoutFormationInvalideException("Formation déjà existante.", new Throwable(AjoutFormationInvalideException.cause.Formation_Existante.toString()));
        }
        if(formation.getDebut() != null && formation.getFin() != null){
            if(formation.getDebut().after(formation.getFin())){
                throw new AjoutFormationInvalideException("Dates de début et de fin incohérentes", new Throwable(AjoutFormationInvalideException.cause.Date_Incohérentes.toString()));
            }
        }
        for(Justificatif justificatif:formation.getLesJustificatifs()){
            new JustificatifDAO().save(justificatif);
        }
        
        //enregistrement de la formation dans la BDD
        formationDAO.save(formation);
    }

    /**
     * 
     * @param formationToSuppr: formation à supprimer de la BDD
     * @throws SuppressionFormationInvalideException 
     */
    public void supprimerFormation(Formation formationToSuppr) throws SuppressionFormationInvalideException {
        //verification de la validité de la demande
        if(formationToSuppr == null){
            throw new SuppressionFormationInvalideException("Requête incorecte.", new Throwable(SuppressionFormationInvalideException.cause.Formation_Vide.toString()));
        }
        if(formationDAO.getById(formationToSuppr.getId()) == null){
            throw new SuppressionFormationInvalideException("Formation " + formationToSuppr.getId() + " inexistante.", new Throwable(SuppressionFormationInvalideException.cause.Formation_Inexistante.toString()));
        }
        if(formationToSuppr.getDebut() != null && formationToSuppr.getFin() != null){
            if(formationToSuppr.getDebut().before(new Date()) && formationToSuppr.getFin().after(new Date())){ //verif formation editable
                throw new SuppressionFormationInvalideException("La formation ne peut être modifier pendant la période d'inscription", new Throwable(SuppressionFormationInvalideException.cause.Inscriptions_En_Cours.toString()));
            }
        }
        List<Justificatif> justificatifs = formationToSuppr.getLesJustificatifs();
        formationToSuppr.setLesJustificatifs(new ArrayList<Justificatif>());
        formationDAO.update(formationToSuppr);
        for(Justificatif justificatif:justificatifs){
            new JustificatifDAO().delete(justificatif);
        }
        
        //suppression de la formation dans la BDD
        formationDAO.delete(formationToSuppr);
    }
    
    /**
     * 
     * @param formationToModif: formation modifié contenant l'id de la formation à modifier
     * @throws ModificationFormationInvalideException 
     */
    public void modifierFormation(Formation formationToModif) throws ModificationFormationInvalideException {
        //verification de la validité de la demande
        if(formationToModif == null){
            throw new ModificationFormationInvalideException("Requête incorecte.", new Throwable(ModificationFormationInvalideException.cause.Formation_Vide.toString()));
        }
        if(formationDAO.getById(formationToModif.getId()) == null){
            throw new ModificationFormationInvalideException("Formation " + formationToModif.getId() + " inexistante.", new Throwable(ModificationFormationInvalideException.cause.Formation_Inexistante.toString()));
        }
        if(formationToModif.getDebut().before(new Date()) && formationToModif.getFin().after(new Date())){ //verif formation editable
            throw new ModificationFormationInvalideException("La formation ne peut être modifier pendant la période d'inscription", new Throwable(ModificationFormationInvalideException.cause.Inscriptions_En_Cours.toString()));
        }
        if(formationToModif.getIntitule().isEmpty()){
            throw new ModificationFormationInvalideException("Intitulé non rempli.", new Throwable(ModificationFormationInvalideException.cause.Intitule_Vide.toString()));
        }
        if(formationDAO.getFormationByIntitule(formationToModif.getIntitule()) != null){
            throw new ModificationFormationInvalideException("Formation déjà existante.", new Throwable(ModificationFormationInvalideException.cause.Formation_Existante.toString()));
        }
        if(formationToModif.getDebut() != null && formationToModif.getFin() != null){
            if(formationToModif.getDebut().after(formationToModif.getFin())){
                throw new ModificationFormationInvalideException("Dates de début et de fin incohérentes", new Throwable(ModificationFormationInvalideException.cause.Date_Incohérentes.toString()));
            }
        }
        List<Justificatif> oldJustificatifs = formationDAO.getById(formationToModif.getId()).getLesJustificatifs();
        List<Justificatif> newJustificatifs = formationToModif.getLesJustificatifs();
        List<Justificatif> justificatifsToAdd = newJustificatifs;
        justificatifsToAdd.removeAll(oldJustificatifs);
        List<Justificatif> justificatifsToSuppr = oldJustificatifs;
        justificatifsToSuppr.removeAll(newJustificatifs);
        formationDAO.update(formationToModif);
        for(Justificatif justificatif:justificatifsToSuppr){
            new JustificatifDAO().delete(justificatif);
        }
        for(Justificatif justificatif:justificatifsToAdd){
            new JustificatifDAO().save(justificatif);
        }
        
        //mise à jour de la formation dans la BDD
        formationDAO.update(formationToModif);
    }
}
