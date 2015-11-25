/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Formation;
import modele.entite.Justificatif;
import service.AjoutFormationInvalideException;
import service.FormationService;

/**
 *
 * @author Arthur
 */
public class AjoutFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String pageSuivante = "";
        
        //recuperation du formulaire
        String description = request.getParameter("description");
        int nbPlace = Integer.parseInt(request.getParameter("nbPlace"));
        Date dateDebut = new Date();
        Date dateFin = new Date();
        String intitule = request.getParameter("intitule");
        List<Justificatif> justificatifs = (List<Justificatif>) request.getSession().getAttribute("justificatifs");
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	try {
            dateDebut = formatter.parse(request.getParameter("dateDebut"));
            dateFin = formatter.parse(request.getParameter("dateFin"));
	} catch (ParseException e) {
            e.printStackTrace();
	}

        //verification de la validité du formulaire
        if(dateDebut == null || dateFin == null || intitule.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(AjoutFormationAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //formation du dossier
        Formation nouvelleFormation = new Formation(description, nbPlace, dateDebut, dateFin, intitule, justificatifs);
        
        //demande de creation du dossier
        try{
            new FormationService().ajouterFormation(nouvelleFormation);
            request.setAttribute("error", "false");
            request.setAttribute("message", "Formation ajouté.");
            
            //redirection
            if(request.getParameter("bouton").equals("enregistrer")){
                request.getSession().removeAttribute("justificatifs"); //free justificatifs
                pageSuivante = "accueil.jsp"; //(String) request.getAttribute("pageRetour");
            }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
                request.getSession().setAttribute("justificatifs", new ArrayList<Justificatif>());
                pageSuivante = "ajoutFormation.jsp";
            }
        }catch(AjoutFormationInvalideException e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été ajouté: " + e.getMessage());
            if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Formation_Existante.toString())){
                request.setAttribute("focus", "intitule");
            }
            pageSuivante = "ajoutFormation.jsp"; //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été ajouté.");
            pageSuivante = "ajoutFormation.jsp"; //redirection
        }
        
        return pageSuivante;
    }
    
}
