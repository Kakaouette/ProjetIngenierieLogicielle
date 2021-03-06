/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.formation;

import page.action.Action;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import service.FormationService;
import service.exception.ModificationFormationInvalideException;
import service.exception.SuppressionJustificatifInvalideException;

/**
 *
 * @author Arthur
 */
public class ModifDatesInscriptionAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Action actionPageSuivante = null;
        
        //recuperation du formulaire
        String intitule = request.getParameter("intitule");
        String debut = request.getParameter("dateDebut");
        String fin = request.getParameter("dateFin");
        
        //verification de la validité du formulaire
        String[] required = {intitule, debut, fin};
        String[] requiredName = {"intitulé", "date de début", "date de fin"};
        try {
            validerFormulaire(required, requiredName);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return stayHere(request, response);
        }
        
        //mise en forme des données
        Date dateDebut = null;
        Date dateFin = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	try {
            dateDebut = formatter.parse(debut);
            dateFin = formatter.parse(fin);
	} catch (ParseException e) {
            e.printStackTrace();
	}
        
        //recuperation de la formation
        Formation formationModifiee = new FormationDAO().getFormationByIntitule(intitule);
        if(formationModifiee == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation inconnue");
            return stayHere(request, response); //redirection
        }
        //formation de la formation
        formationModifiee.setDebut(dateDebut);
        formationModifiee.setFin(dateFin);
        
        //demande de modification de la formation
        try{
            new FormationService().modifierFormation(formationModifiee);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Période d'inscription modifié.");
            actionPageSuivante = new VoirGestionFormationsAction(); //redirection
        }catch(ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException e){
            //set msg d'erreur
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La période d'inscription n'a pas été modifié: " + e.getMessage());
            //modif requete celon le type d'erreur
            if(e instanceof ModificationFormationInvalideException){
                if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.Intitule_Vide.toString())){
                    request.setAttribute("focus", "intitule");
                }else if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.Date_Incohérentes.toString())){
                    request.setAttribute("focus", "dateDebut");
                }else if(e.getCause().getMessage().equals(ModificationFormationInvalideException.cause.Inscriptions_En_Cours.toString())){
                    request.setAttribute("typeMessage", "warning");
                }
            }else if(e instanceof IOException){ //exception bdd
                request.setAttribute("message", "La période d'inscription n'a pas été modifié.");
            }
            //reload la page
            return stayHere(request, response); //redirection
        }
        
        return actionPageSuivante.execute(request, response);
    }
    
    /**
     * 
     * @param request
     * @param response
     * @return action de voir la page + retour de String correspondant à la page
     */
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("intitule", request.getParameter("intitule"));
        request.setAttribute("description", request.getParameter("description"));
        request.setAttribute("nbPlace", request.getParameter("nbPlace"));
        request.setAttribute("dateDebut", request.getParameter("dateDebut"));
        request.setAttribute("dateFin", request.getParameter("dateFin"));
        return new VoirGestionDatesInscriptionAction().execute(request, response); //modif: voir récupérer page precedente
    }
    
    private void validerFormulaire(String[] required, String[] requiredName) throws Exception{
        
        //verification de la validité du formulaire
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i].isEmpty()){
                empty.add(requiredName[i]);
            }
        }
        if(!empty.isEmpty()){
            String champs = "";
            for(String champ : empty){
                if(!champs.equals("")){champs+=", ";}
                champs += champ;
            }
            if(empty.size()==1){
                throw new Exception("Un champ requis est vide. (" + champs + ")");
            }else{
                throw new Exception("Des champs requis sont vides. (" + champs + ")");
            }
        }
    }
}
