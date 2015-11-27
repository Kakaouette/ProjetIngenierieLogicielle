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
        Action actionPageSuivante = null;
        
        //recuperation du formulaire
        String intitule = request.getParameter("intitule");
        String description = request.getParameter("description");
        String nbPlaceForm = request.getParameter("nbPlace");
        String debut = request.getParameter("dateDebut");
        String fin = request.getParameter("dateFin");
        List<Justificatif> justificatifs = (List<Justificatif>) request.getSession().getAttribute("justificatifs");
        
        //verification de la validité du formulaire
        if(intitule.isEmpty() || nbPlaceForm.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(AjoutFormationAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //mise en forme des données
        int nbPlace = Integer.parseInt(nbPlaceForm);
        Date dateDebut = null;
        Date dateFin = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	try {
            dateDebut = formatter.parse(debut);
            dateFin = formatter.parse(fin);
	} catch (ParseException e) {
            e.printStackTrace();
	}
        
        //formation du dossier
        Formation nouvelleFormation = new Formation(description, nbPlace, dateDebut, dateFin, intitule, justificatifs);
        
        //demande de creation du dossier
        try{
            new FormationService().ajouterFormation(nouvelleFormation);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Formation ajouté.");
            
            //redirection
            if(request.getParameter("bouton").equals("enregistrer")){
                request.getSession().removeAttribute("justificatifs"); //free justificatifs
                actionPageSuivante = new VoirGestionFormationAction(); //(String) request.getAttribute("pageRetour");
            }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
                request.getSession().setAttribute("justificatifs", new ArrayList<Justificatif>());
                actionPageSuivante = new VoirAjoutFormationAction();
            }
        }catch(AjoutFormationInvalideException e){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été ajouté: " + e.getMessage());
            if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Formation_Existante.toString())){
                request.setAttribute("focus", "intitule");
            }
            if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Intitule_Vide.toString())){
                request.setAttribute("focus", "intitule");
            }
            if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Date_Incohérentes.toString())){
                request.setAttribute("focus", "dateDebut");
            }
            
            //keep formulaire
            request.setAttribute("intitule", intitule);
            request.setAttribute("description", description);
            request.setAttribute("nbPlace", nbPlaceForm);
            request.setAttribute("dateDebut", debut);
            request.setAttribute("dateFin", fin);
            actionPageSuivante = new VoirAjoutFormationAction(); //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été ajouté.");
            actionPageSuivante = new VoirAjoutFormationAction(); //redirection
        }
        
        return actionPageSuivante.execute(request, response);
    }
    
}
