/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.dao.HistoriqueDAO;
import modele.entite.Historique;
import service.exception.AjoutHistoriqueInvalideException;

/**
 *
 * @author Arthur
 */
public class HistoriqueService {
    HistoriqueDAO historiqueDAO;

    public HistoriqueService() {
        this.historiqueDAO = new HistoriqueDAO();
    }
    
    public void ajouterHistorique(Historique historique) throws AjoutHistoriqueInvalideException{
        //verification de la validité de la demande
        if(historique == null){
            throw new AjoutHistoriqueInvalideException("Requête incorrecte.", new Throwable(AjoutHistoriqueInvalideException.cause.Historique_Null.toString()));
        }
        if(historiqueDAO.getById(historique.getId()) != null){ //verif id non utilisé
            throw new AjoutHistoriqueInvalideException("L'identifiant de l'historique est déjà utilisé.", new Throwable(AjoutHistoriqueInvalideException.cause.Historique_Existant.toString()));
        }
        if(historique.getCompte() == null || historique.getDate() == null){ //verif champs requis remplis
            throw new AjoutHistoriqueInvalideException("Informations sur l'historique incomplétes.", new Throwable(AjoutHistoriqueInvalideException.cause.Historique_Incomplet.toString()));
        }
        
        historiqueDAO.save(historique);
    } 
}
