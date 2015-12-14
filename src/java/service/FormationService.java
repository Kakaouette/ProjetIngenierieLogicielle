/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import service.exception.AjoutFormationInvalideException;
import service.exception.ModificationFormationInvalideException;
import service.exception.SuppressionFormationInvalideException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modele.dao.FormationDAO;
import modele.dao.JustificatifDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;
import service.exception.AjoutJustificatifInvalideException;
import service.exception.SuppressionJustificatifInvalideException;

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
     * Fonction permettant d'ajouter une formation dans la BDD
     * 
     * @param formationToAdd: formation à ajouter dans la BDD
     * @throws AjoutFormationInvalideException: exceptions empechant l'ajout
     * @throws service.exception.AjoutJustificatifInvalideException
     * @throws java.io.IOException: exception bdd
     */
    public void ajouterFormation(Formation formationToAdd) throws AjoutFormationInvalideException, AjoutJustificatifInvalideException, IOException{
        //verification de la validité de la demande
        if(formationToAdd == null){
            throw new AjoutFormationInvalideException("Requête incorrecte.", new Throwable(AjoutFormationInvalideException.cause.Formation_Null.toString()));
        }
        if(formationToAdd.getIntitule() == null){
            throw new AjoutFormationInvalideException("Intitulé non rempli.", new Throwable(AjoutFormationInvalideException.cause.Intitule_Vide.toString()));
        }else if(formationToAdd.getIntitule().isEmpty()){
            throw new AjoutFormationInvalideException("Intitulé non rempli.", new Throwable(AjoutFormationInvalideException.cause.Intitule_Vide.toString()));
        }
        if(formationDAO.getById(formationToAdd.getId()) != null || formationDAO.getFormationByIntitule(formationToAdd.getIntitule()) != null){
            throw new AjoutFormationInvalideException("Formation déjà existante.", new Throwable(AjoutFormationInvalideException.cause.Formation_Existante.toString()));
        }
        if(formationToAdd.getDebut() != null && formationToAdd.getFin() != null){ //eviter les null pointer
            if(formationToAdd.getDebut().after(formationToAdd.getFin())){ //verif date fin est après date début
                throw new AjoutFormationInvalideException("Dates de début et de fin incohérentes", new Throwable(AjoutFormationInvalideException.cause.Date_Incohérentes.toString()));
            }
        }
        if(formationToAdd.getLesJustificatifs() != null){ //eviter les null pointer
            //verification des doublons
            for(Justificatif justificatif:formationToAdd.getLesJustificatifs()){
                int c=0;
                for(Justificatif jTemp:formationToAdd.getLesJustificatifs()){
                    if(jTemp.getTitre().equals(justificatif.getTitre()) && jTemp.getTypeAdmissible().equals(justificatif.getTypeAdmissible()) && jTemp.getTypeNationalite().equals(justificatif.getTypeNationalite())){
                        c++;
                        if(c>1){
                            throw new AjoutJustificatifInvalideException("Le titre du justificatif est déjà utilisé.", new Throwable(AjoutJustificatifInvalideException.cause.Justificatif_Existant.toString()));
                        }
                    }
                }
            }
            //ajout des justificatifs
            for(Justificatif justificatif:formationToAdd.getLesJustificatifs()){
                new JustificatifService().ajouterJustificatif(justificatif);
            }
        }
        
        //enregistrement de la formation dans la BDD
        formationDAO.save(formationToAdd);
    }

    /**
     * Fonction permettant de supprimer une formation de la BDD
     * 
     * @param formationToSuppr: formation à supprimer de la BDD (contenant l'id de la formation à modifier)
     * @throws SuppressionFormationInvalideException: exceptions empechant la suppression
     * @throws service.exception.SuppressionJustificatifInvalideException
     * @throws java.io.IOException: exception bdd
     */
    public void supprimerFormation(Formation formationToSuppr) throws SuppressionFormationInvalideException, SuppressionJustificatifInvalideException, IOException {
        //verification de la validité de la demande
        if(formationToSuppr == null){
            throw new SuppressionFormationInvalideException("Requête incorecte.", new Throwable(SuppressionFormationInvalideException.cause.Formation_Null.toString()));
        }
        if(formationDAO.getById(formationToSuppr.getId()) == null){
            throw new SuppressionFormationInvalideException("Formation " + formationToSuppr.getId() + " inexistante.", new Throwable(SuppressionFormationInvalideException.cause.Formation_Inexistante.toString()));
        }
        if(formationDAO.getById(formationToSuppr.getId()).getDebut() != null && formationDAO.getById(formationToSuppr.getId()).getFin() != null){ //eviter les null pointer
            if(formationDAO.getById(formationToSuppr.getId()).getDebut().before(new Date()) && formationDAO.getById(formationToSuppr.getId()).getFin().after(new Date())){ //verif formation editable
                throw new SuppressionFormationInvalideException("La formation ne peut être modifier pendant la période d'inscription", new Throwable(SuppressionFormationInvalideException.cause.Inscriptions_En_Cours.toString()));
            }
        }
        if(formationToSuppr.getLesJustificatifs() != null){ //eviter les null pointer
            List<Justificatif> justificatifs = formationToSuppr.getLesJustificatifs();
            formationToSuppr.setLesJustificatifs(new ArrayList<Justificatif>());
            formationDAO.update(formationToSuppr);
            for(Justificatif justificatif:justificatifs){
                new JustificatifService().supprimerJustificatif(justificatif);
            }
        }
        
        //suppression de la formation dans la BDD
        formationDAO.delete(formationToSuppr.getId());
    }
    
    /**
     * Fonction permettant de modifier une formation dans la BDD
     * 
     * @param formationToModif: formation modifié (contenant l'id de la formation à modifier dans la BDD)
     * @throws ModificationFormationInvalideException: exceptions empechant la modification
     * @throws service.exception.SuppressionJustificatifInvalideException
     * @throws java.io.IOException: exception bdd
     */
    public void modifierFormation(Formation formationToModif) throws ModificationFormationInvalideException, SuppressionJustificatifInvalideException, IOException {
        //verification de la validité de la demande
        if(formationToModif == null){
            throw new ModificationFormationInvalideException("Requête incorecte.", new Throwable(ModificationFormationInvalideException.cause.Formation_Null.toString()));
        }
        if(formationDAO.getById(formationToModif.getId()) == null){
            throw new ModificationFormationInvalideException("Formation " + formationToModif.getId() + " inexistante.", new Throwable(ModificationFormationInvalideException.cause.Formation_Inexistante.toString()));
        }
        if(formationDAO.getById(formationToModif.getId()).getDebut() != null && formationDAO.getById(formationToModif.getId()).getFin()!= null){ //eviter les null pointer
            if(formationDAO.getById(formationToModif.getId()).getDebut().before(new Date()) && formationDAO.getById(formationToModif.getId()).getFin().after(new Date())){ //verif formation editable
                throw new ModificationFormationInvalideException("La formation ne peut être modifier pendant la période d'inscription", new Throwable(ModificationFormationInvalideException.cause.Inscriptions_En_Cours.toString()));
            }
        }
        if(formationToModif.getIntitule() == null){ //eviter les null pointer
            throw new ModificationFormationInvalideException("Intitulé non rempli.", new Throwable(ModificationFormationInvalideException.cause.Intitule_Vide.toString()));
        }else if(formationToModif.getIntitule().isEmpty()){
            throw new ModificationFormationInvalideException("Intitulé non rempli.", new Throwable(ModificationFormationInvalideException.cause.Intitule_Vide.toString()));
        }
        if(formationDAO.getFormationByIntitule(formationToModif.getIntitule()) != null && formationDAO.getFormationByIntitule(formationToModif.getIntitule()).getId() != formationToModif.getId()){
            throw new ModificationFormationInvalideException("Formation déjà existante.", new Throwable(ModificationFormationInvalideException.cause.Formation_Existante.toString()));
        }
        if(formationToModif.getDebut() != null && formationToModif.getFin() != null){ //eviter les null pointer
            if(formationToModif.getDebut().after(formationToModif.getFin())){ //verif date fin est après date début
                throw new ModificationFormationInvalideException("Dates de début et de fin incohérentes", new Throwable(ModificationFormationInvalideException.cause.Date_Incohérentes.toString()));
            }
        }
        List<Justificatif> oldJustificatifs = formationDAO.getById(formationToModif.getId()).getLesJustificatifs();
                
        //mise à jour de la formation dans la BDD
        formationDAO.update(formationToModif);
        //supression des justificatifs inutiles
        if(oldJustificatifs != null){ //eviter les null pointer
            for(Justificatif justificatif:oldJustificatifs){
                new JustificatifService().supprimerJustificatif(justificatif);
            }
        }
    }
}
