/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

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
        //recuperation du formulaire
        String description = request.getParameter("description");
        int nbPlace = (int) request.getAttribute("nbPlace");
        Date dateDebut = (Date) request.getAttribute("dateDebut");
        Date dateFin = (Date) request.getAttribute("dateFin");
        String intitule = request.getParameter("intitule");
        List<Justificatif> justificatifs = (List<Justificatif>) request.getAttribute("justificatifs");
        
        //verification de la validité du formulaire
        if(description.isEmpty() || dateDebut == null || dateFin == null || intitule.isEmpty()){
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
            request.setAttribute("message", "Formation créée.");
        }catch(AjoutFormationInvalideException e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été créée: " + e.getMessage());
            if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Formation_Existante.toString())){
                request.setAttribute("focus", "intitule");
            }
        }catch(Exception e){ //exception bdd
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été créée.");
        }
        
        String pageAVoir = "";
        if(request.getParameter("bouton").equals("enregistrer")){
            pageAVoir = request.getParameter("pageRetour");
        }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
            pageAVoir = "ajoutFormation.jsp";
        }
        return pageAVoir;
    }
    
}
