/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.dao.JustificatifDAO;
import modele.entite.Justificatif;
import service.exception.AjoutJustificatifInvalideException;
import service.exception.SuppressionJustificatifInvalideException;

/**
 *
 * @author Arthur
 */
public class JustificatifService {
    JustificatifDAO justificatifDAO;

    public JustificatifService() {
        this.justificatifDAO = new JustificatifDAO();
    }
    
    /**
     * Fonction permettant d'ajouter un justificatif dans la BDD
     * 
     * @param justificatifToAdd: justificatif à ajouter dans la BDD
     * @throws AjoutJustificatifInvalideException: exceptions empechant l'ajout
     */
    public void ajouterJustificatif(Justificatif justificatifToAdd) throws AjoutJustificatifInvalideException{
        //verification de la validité de la demande
        if(justificatifToAdd == null){
            throw new AjoutJustificatifInvalideException("Requête incorrecte.", new Throwable(AjoutJustificatifInvalideException.cause.Justificatif_Null.toString()));
        }
        if(justificatifDAO.getById(justificatifToAdd.getId()) != null){
            throw new AjoutJustificatifInvalideException("L'identifiant du justificatif est déjà utilisé.", new Throwable(AjoutJustificatifInvalideException.cause.Justificatif_Existant.toString()));
        }
        if(justificatifToAdd.getTitre() == null || justificatifToAdd.getTypeAdmissible() == null || justificatifToAdd.getTypeNationalite() == null){
            throw new AjoutJustificatifInvalideException("Informations sur le justificatif incomplétes.", new Throwable(AjoutJustificatifInvalideException.cause.Justificatif_Incomplet.toString()));
        }
        
        //enregistrement de la formation dans la BDD
        justificatifDAO.save(justificatifToAdd);
    }
    
    /**
     * Fonction permettant de supprimer un justificatif de la BDD
     * 
     * @param justificatifToSuppr: justificatif à supprimer de la BDD (contenant l'id de la formation à modifier)
     * @throws SuppressionJustificatifInvalideException: exceptions empechant la suppression
     */
    public void supprimerJustificatif(Justificatif justificatifToSuppr) throws SuppressionJustificatifInvalideException {
        //verification de la validité de la demande
        if(justificatifToSuppr == null){
            throw new SuppressionJustificatifInvalideException("Requête incorecte.", new Throwable(SuppressionJustificatifInvalideException.cause.Justificatif_Null.toString()));
        }
        if(justificatifDAO.getById(justificatifToSuppr.getId()) == null){
            throw new SuppressionJustificatifInvalideException("Justificatif " + justificatifToSuppr.getId() + " inexistant.", new Throwable(SuppressionJustificatifInvalideException.cause.Justificatif_Inexistant.toString()));
        }
        
        //suppression de la formation dans la BDD
        justificatifDAO.delete(justificatifToSuppr.getId());
    }
}
