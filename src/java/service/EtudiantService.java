/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.dao.EtudiantDAO;
import modele.entite.Etudiant;
import service.exception.AjoutEtudiantInvalideException;

/**
 *
 * @author Arthur
 */
public class EtudiantService {
    EtudiantDAO etudiantDAO;

    public EtudiantService() {
        this.etudiantDAO = new EtudiantDAO();
    }
    
    public void ajouterEtudiant(Etudiant etudiant) throws AjoutEtudiantInvalideException{
        //verification de la validité de la demande
        if(etudiant == null){
            throw new AjoutEtudiantInvalideException("Requête incorrecte.", new Throwable(AjoutEtudiantInvalideException.cause.Etudiant_Null.toString()));
        }
        if(etudiantDAO.getById(etudiant.getId()) != null){ //verif id non utilisé
            throw new AjoutEtudiantInvalideException("L'identifiant de l'étudiant est déjà utilisé.", new Throwable(AjoutEtudiantInvalideException.cause.Etudiant_Existant.toString()));
        }
        if(etudiant.getIne() == null | etudiant.getNom() == null || etudiant.getPrenom() == null || etudiant.getSexe()== null || etudiant.getAdresse() == null || etudiant.getAdressePostale() == null){ //verif champs requis remplis
            throw new AjoutEtudiantInvalideException("Informations sur l'étudiant incomplétes.", new Throwable(AjoutEtudiantInvalideException.cause.Etudiant_Incomplet.toString()));
        }
        if(etudiantDAO.getEtudiantByINE(etudiant.getIne()) != null){ //verif id non utilisé
            throw new AjoutEtudiantInvalideException("L'étudiant existe déja.", new Throwable(AjoutEtudiantInvalideException.cause.Etudiant_Existant.toString()));
        }
        
        etudiantDAO.save(etudiant);
    }    
}
