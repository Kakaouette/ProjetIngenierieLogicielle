/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.dao.AdresseDAO;
import modele.entite.Adresse;
import service.exception.AjoutAdresseInvalideException;

/**
 *
 * @author Arthur
 */
public class AdresseService {
    AdresseDAO adresseDAO;

    public AdresseService() {
        this.adresseDAO = new AdresseDAO();
    }
    
    public void ajouterAdresse(Adresse adresse) throws AjoutAdresseInvalideException{
        //verification de la validité de la demande
        if(adresse == null){
            throw new AjoutAdresseInvalideException("Requête incorrecte.", new Throwable(AjoutAdresseInvalideException.cause.Adresse_Null.toString()));
        }
        if(adresseDAO.getById(adresse.getId()) != null){ //verif id non utilisé
            throw new AjoutAdresseInvalideException("L'identifiant de l'adresse est déjà utilisé.", new Throwable(AjoutAdresseInvalideException.cause.Adresse_Existante.toString()));
        }
        if(adresse.getCodePostal() == null || adresse.getVille() == null){ //verif champs requis remplis
            throw new AjoutAdresseInvalideException("Adresse incompléte.", new Throwable(AjoutAdresseInvalideException.cause.Adresse_Incomplete.toString()));
        }
        
        adresseDAO.save(adresse);
    }
}
