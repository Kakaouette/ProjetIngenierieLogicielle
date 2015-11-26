/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.dao.FormationDAO;
import modele.entite.Formation;

/**
 *
 * @author Arthur
 */
public class FormationService {
    FormationDAO formationDAO;

    public FormationService() {
        this.formationDAO = new FormationDAO();
    }
    
    public void ajouterFormation(Formation formation) throws AjoutFormationInvalideException{
        //verification de la validité de la demande
        if(formation != null){
            throw new AjoutFormationInvalideException("Requête incorecte.", new Throwable(AjoutFormationInvalideException.cause.Formation_Vide.toString()));
        }
        if(formationDAO.getFormationByIntitule(formation.getIntitule()) != null){
            throw new AjoutFormationInvalideException("Formation déjà existante.", new Throwable(AjoutFormationInvalideException.cause.Formation_Existante.toString()));
        }
        
        //enregistrement de la formation dans la BDD
        formationDAO.save(formation);
    }

    public void supprimerFormation(int id) throws SuppressionFormationInvalideException {
        //verification de la validité de la demande
        Formation formation = formationDAO.getById(id);
        if(formation != null){
            throw new SuppressionFormationInvalideException("Formation inexistante.", new Throwable(SuppressionFormationInvalideException.cause.Formation_Inexistante.toString()));
        }
        
        //suppression de la formation dans la BDD
        formationDAO.delete(formation);
    }
    
    public void modifierFormation(Formation formation) throws ModificationFormationInvalideException {
        //verification de la validité de la demande
        if(formation != null){
            throw new ModificationFormationInvalideException("Requête incorecte.", new Throwable(ModificationFormationInvalideException.cause.Formation_Vide.toString()));
        }
        if(formationDAO.getById(formation.getId()) == null){
            throw new ModificationFormationInvalideException("Formation inexistante.", new Throwable(ModificationFormationInvalideException.cause.Formation_Inexistante.toString()));
        }
        if(!formation.getIntitule().isEmpty()){
            throw new ModificationFormationInvalideException("Intitulé non rempli.", new Throwable(ModificationFormationInvalideException.cause.Intitule_Vide.toString()));
        }
        if(formation.getDebut() != null){
            throw new ModificationFormationInvalideException("Date de debut non rempli.", new Throwable(ModificationFormationInvalideException.cause.DateDebut_Vide.toString()));
        }
        if(formation.getFin()!= null){
            throw new ModificationFormationInvalideException("Date de fin non rempli.", new Throwable(ModificationFormationInvalideException.cause.DateFin_Vide.toString()));
        }
        
        //mise à jour de la formation dans la BDD
        formationDAO.update(formation);
    }
}
