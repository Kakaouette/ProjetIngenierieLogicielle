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
        if(idForm.isEmpty() || intitule.isEmpty() || nbPlaceForm.isEmpty() || debut == null || fin == null){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(ModifFormationAction.class.getName()).log(Level.SEVERE, null, ex); //msg console
                request.setAttribute("error", "true");
                request.setAttribute("message", ex.getMessage());
                return new VoirModifierFormationAction().execute(request, response);
            }
        }
        //mise en forme des données
        int id = Integer.parseInt(idForm);
        Formation formationModifiee = new FormationDAO().getById(id);
        int nbPlace = Integer.parseInt(nbPlaceForm);
        Date dateDebut = new Date();
        Date dateFin = new Date();
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
            request.setAttribute("error", "false");
            request.setAttribute("message", "Formation modifié.");
            request.getSession().removeAttribute("justificatifs"); //free justificatifs
            actionPageSuivante = new VoirGestionFormationAction(); //redirection
        }catch(ModificationFormationInvalideException e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été ajouté: " + e.getMessage());
            if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.Intitule_Vide.toString())){
                request.setAttribute("focus", "intitule");
            }
            if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.DateDebut_Vide.toString())){
                request.setAttribute("focus", "dateDebut");
            }
            if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.DateFin_Vide.toString())){
                request.setAttribute("focus", "dateFin");
            }
            actionPageSuivante = new VoirModifierFormationAction(); //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été ajouté.");
            actionPageSuivante = new VoirModifierFormationAction(); //redirection
        }
        
        return actionPageSuivante.execute(request, response);
    }
    
}
