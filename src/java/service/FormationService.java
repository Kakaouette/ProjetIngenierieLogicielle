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
        if(formationDAO.getFormationByIntitule(formation.getIntitule()) != null){
            throw new AjoutFormationInvalideException("Fomration déjà existante.", new Throwable(AjoutFormationInvalideException.cause.Formation_Existante.toString()));
        }
        
        //enregistrement de la formation dans la BDD
        formationDAO.save(formation);
    }
}
