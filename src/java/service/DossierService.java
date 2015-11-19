/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import modele.dao.DossierDAO;
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
     * @param dossier
     * @return true: dossier exist après l'opération; false: dossier n'existe pas après l'opération
     */
    public boolean ajouterDossier(Dossier dossier){
        dossierDAO.save(dossier);
        return dossierDAO.getById(Integer.parseInt(dossier.getId())) != null;
    }
}
