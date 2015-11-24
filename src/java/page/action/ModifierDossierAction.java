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
        dossierorigin.setAdmissible(admDossier);
        String etatDossier = request.getParameter("etat");
        dossierorigin.setEtat(etatDossier);
        String dateDossier=request.getParameter("date");
        dossierorigin.setDate(new Date(dateDossier));
        String lettreDossier=request.getParameter("lettre");
        dossierorigin.setLettre(lettreDossier);
        //historique
        String messageHisto=request.getParameter("msg_histo");
        String dateHisto=request.getParameter("date_histo");
        Historique histo=new Historique();
        histo.setMessage(messageHisto);
        histo.setDate(new Date(dateHisto));
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier=dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        try{
            new DossierDAO().update(dossierorigin);
            request.setAttribute("error", "false");
            request.setAttribute("message", "Le dossier a été modifié avec succès !");
        }catch(Exception e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "Le dossier n'a pas été modifié !");
        }
        
        return "consulteDossier.jsp";
        
    }
    
}
