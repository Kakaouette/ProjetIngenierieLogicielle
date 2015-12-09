package page.action;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeCompte;
import service.DossierService;

/**
 * Gestion des dossiers - Consultation en detail un dossier
 * Si dossier non trouvé, retour vers la page des dossiers
 *
 * @author totodunet
 */
public class ConsulterDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        //recuperation de l'id du formulaire
        String idDossier = request.getParameter("idDossier");
        
        //recuperation du dossier
        Dossier dossier = new DossierService().recupererDossier(idDossier);
     
        //si dossier n'existe pas => retour vers la liste des dossiers 
        if(dossier != null){

            request.setAttribute("dossier", dossier);
            return "consulteDossier.jsp";
        }else{
            request.setAttribute("titre", "Gestion des dossiers");
            List<Dossier> dossiers = new DossierDAO().SelectAll(); //recuperation des comptes pour la page suivante
            request.setAttribute("dossiers", dossiers);
            return "listeDossiers.jsp";
        }
    }
    
}
