/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DossierDAO;
import modele.entite.Dossier;

/**
 * Action a déclencher pour charger tout les dossiers de la BDD
 * 
 * @author Jordan
 */
public class VoirGestionDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des dossiers");
        List<Dossier> dossiers = new DossierDAO().SelectAll(); //recuperation des comptes pour la page suivante
        request.setAttribute("dossiers", dossiers);
        return "listeDossiers.jsp";
    }
    
}
