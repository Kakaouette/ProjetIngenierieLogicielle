package page.action;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.Dao;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Historique;
import service.DossierService;

/**
 * Gestion des dossiers - Modification d'un dossier
 * Si echec modification, retour vers la page du dossier
 *
 * @author totodunet
 */
public class ModifierDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        //recuperation du dossier original
        String idDossier = request.getParameter("id");
        Dossier dossierorigin=new DossierDAO().getById(idDossier);
        
        //recuperation des infos et modif de l'objet Dossier
        boolean admDossier = new Boolean(request.getParameter("admissibilite"));
        
        String etatDossier = request.getParameter("etat");
        
        String dateDossier=request.getParameter("date");
        
        String lettreDossier=request.getParameter("lettre");
        
        //historique
        String messageHisto=request.getParameter("msg_histo");
        String dateHisto=request.getParameter("date_histo");
        
        
        try{
            new DossierService().modifierDossier(dossierorigin, admDossier, etatDossier, dateDossier, lettreDossier, messageHisto, dateHisto);
            request.setAttribute("error", "false");
            request.setAttribute("message", "Le dossier a été modifié avec succès !");
        }catch(Exception e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "Le dossier n'a pas été modifié !");
        }
        
        return "consulteDossier.jsp";
        
    }
    
}
