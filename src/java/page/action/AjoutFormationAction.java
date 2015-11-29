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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.JustificatifDAO;
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
        String[] justificatifsForm = request.getParameterValues("justificatifs");
        
        //verification de la validité du formulaire
        
        String[] required = {intitule, nbPlaceForm};
        String[] requiredToString = {"intitulé", "nombre de place"};
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i].isEmpty()){
                empty.add(requiredToString[i]);
            }
        }
        if(!empty.isEmpty()){
            try {
                String champs = "";
                for(String champ : empty){
                    if(!champs.equals("")){champs+=", ";}
                    champs += champ;
                }
                if(empty.size()==1){ throw new Exception("Un champ requis est vide. (" + champs + ")");
                }else{ throw new Exception("Des champs requis sont vides. (" + champs + ")"); }
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return stayHere(request, response); //redirection
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
        List<Justificatif> justificatifs = new ArrayList<Justificatif>();
        for(String justificatif : justificatifsForm){
            Justificatif jTemp = new JustificatifDAO().getJustificatifbyTitre(justificatif);
            if(jTemp != null){
                justificatifs.add(jTemp);
            }else{
                try {
                    throw new Exception("Un des justificatifs est inexistant. (" + justificatif + ")");
                } catch (Exception ex) {
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", ex.getMessage());
                    return stayHere(request, response); //redirection
                }
            }
        }
        
        //formation de la formation
        Formation nouvelleFormation = new Formation(description, nbPlace, dateDebut, dateFin, intitule, justificatifs);
        
        //demande de creation de la formation
        try{
            new FormationService().ajouterFormation(nouvelleFormation);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Formation ajouté.");
            
            //redirection
            if(request.getParameter("bouton").equals("enregistrer")){
                actionPageSuivante = new VoirGestionFormationsAction(); //(String) request.getAttribute("pageRetour");
            }else if(request.getParameter("bouton").equals("enregistrer&nouveau")){
                actionPageSuivante = new VoirAjoutFormationAction();
            }
        }catch(AjoutFormationInvalideException e){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été ajouté: " + e.getMessage());
            if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Formation_Existante.toString())){
                request.setAttribute("focus", "intitule");
            }else if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Intitule_Vide.toString())){
                request.setAttribute("focus", "intitule");
            }else if(e.getCause().getMessage().equals(AjoutFormationInvalideException.cause.Date_Incohérentes.toString())){
                request.setAttribute("focus", "dateDebut");
            }
            return stayHere(request, response); //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été ajouté.");
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
        request.setAttribute("justificatifs", request.getParameterValues("justificatifs"));
        return new VoirAjoutFormationAction().execute(request, response); //modif: voir récupérer page precedente
    }
}
