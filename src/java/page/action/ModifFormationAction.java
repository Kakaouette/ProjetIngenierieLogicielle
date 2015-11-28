/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;
import service.FormationService;
import service.ModificationFormationInvalideException;

/**
 *
 * @author Arthur
 */
public class ModifFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Action actionPageSuivante = null;
        
        //recuperation du formulaire
        String idForm = request.getParameter("id");
        String intitule = request.getParameter("intitule");
        String description = request.getParameter("description");
        String nbPlaceForm = request.getParameter("nbPlace");
        String debut = request.getParameter("dateDebut");
        String fin = request.getParameter("dateFin");
        List<Justificatif> justificatifs = (List<Justificatif>) request.getSession().getAttribute("justificatifs");
        
        //verification de la validité du formulaire
        if(idForm.isEmpty() || intitule.isEmpty() || nbPlaceForm.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(ModifFormationAction.class.getName()).log(Level.SEVERE, null, ex); //msg console
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return stayHere(request, response); //redirection
            }
        }
        //mise en forme des données
        int id = Integer.parseInt(idForm);
        Formation formationModifiee = new FormationDAO().getById(id);
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
        formationModifiee.setIntitule(intitule);
        formationModifiee.setDescription(description);
        formationModifiee.setNombrePlace(nbPlace);
        formationModifiee.setDebut(dateDebut);
        formationModifiee.setFin(dateFin);
        formationModifiee.setLesJustificatifs(justificatifs);
        
        //demande de modification du dossier
        try{
            new FormationService().modifierFormation(formationModifiee);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Formation modifié.");
            request.getSession().removeAttribute("justificatifs"); //free justificatifs
            actionPageSuivante = new VoirGestionFormationAction(); //redirection
        }catch(ModificationFormationInvalideException e){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été ajouté: " + e.getMessage());
            if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.Intitule_Vide.toString())){
                request.setAttribute("focus", "intitule");
            }
            return stayHere(request, response); //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été ajouté.");
            return stayHere(request, response); //redirection
        }
        
        return actionPageSuivante.execute(request, response);
    }
    
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("intitule", request.getParameter("intitule"));
        request.setAttribute("description", request.getParameter("description"));
        request.setAttribute("nbPlace", request.getParameter("nbPlace"));
        request.setAttribute("dateDebut", request.getParameter("dateDebut"));
        request.setAttribute("dateFin", request.getParameter("dateFin"));
        return new VoirModifierFormationAction().execute(request, response); //modif: voir récupérer page precedente
    }
}
