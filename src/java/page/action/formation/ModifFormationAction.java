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
import modele.entite.Justificatif;
import modele.entite.TypeDossier;
import modele.entite.TypeJustificatifEtranger;
import service.FormationService;
import service.exception.ModificationFormationInvalideException;
import service.exception.SuppressionJustificatifInvalideException;

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
        String[] justificatifsInscriptionFrancaisForm = request.getParameterValues("justificatifsInscriptionFrancais");
        String[] justificatifsAdmissionFrancaisForm = request.getParameterValues("justificatifsAdmissionFrancais");
        String[] justificatifsInscriptionEtrangerForm = request.getParameterValues("justificatifsInscriptionEtranger");
        String[] justificatifsAdmissionEtrangerForm = request.getParameterValues("justificatifsAdmissionEtranger");
        
        //verification de la validité du formulaire
        String[] required = {idForm, intitule, nbPlaceForm};
        String[] requiredName = {"id de la formation", "intitulé", "nombre de place"};
        try {
            validerFormulaire(required, requiredName);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return stayHere(request, response); //redirection
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
        if(justificatifsInscriptionFrancaisForm != null){
            for(String titre : justificatifsInscriptionFrancaisForm){
                String descriptionJustificatif = request.getParameter(titre + "InscriptionFrancaisDescription");
                if(descriptionJustificatif != null){
                    Justificatif jTemp = new Justificatif(titre, descriptionJustificatif, TypeDossier.inscription, TypeJustificatifEtranger.francais);
                    justificatifs.add(jTemp);
                }else{
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", titre + " n'a pas de description.");
                    return stayHere(request, response); //redirection
                }
            }
        }
        if(justificatifsAdmissionFrancaisForm != null){
            for(String titre : justificatifsAdmissionFrancaisForm){
                String descriptionJustificatif = request.getParameter(titre + "AdmissionFrancaisDescription");
                if(descriptionJustificatif != null){
                    Justificatif jTemp = new Justificatif(titre, descriptionJustificatif, TypeDossier.admissibilite, TypeJustificatifEtranger.francais);
                    justificatifs.add(jTemp);
                }else{
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", titre + " n'a pas de description.");
                    return stayHere(request, response); //redirection
                }
            }
        }
        if(justificatifsInscriptionEtrangerForm != null){
            for(String titre : justificatifsInscriptionEtrangerForm){
                String descriptionJustificatif = request.getParameter(titre + "InscriptionEtrangerDescription");
                if(descriptionJustificatif != null){
                    Justificatif jTemp = new Justificatif(titre, descriptionJustificatif, TypeDossier.inscription, TypeJustificatifEtranger.etranger);
                    justificatifs.add(jTemp);
                }else{
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", titre + " n'a pas de description.");
                    return stayHere(request, response); //redirection
                }
            }
        }
        if(justificatifsAdmissionEtrangerForm != null){
            for(String titre : justificatifsAdmissionEtrangerForm){
                String descriptionJustificatif = request.getParameter(titre + "AdmissionEtrangerDescription");
                if(descriptionJustificatif != null){
                    Justificatif jTemp = new Justificatif(titre, descriptionJustificatif, TypeDossier.admissibilite, TypeJustificatifEtranger.etranger);
                    justificatifs.add(jTemp);
                }else{
                    request.setAttribute("typeMessage", "danger");
                    request.setAttribute("message", titre + " n'a pas de description.");
                    return stayHere(request, response); //redirection
                }
            }
        }
        
        //recuperation de la formation
        int id = Integer.parseInt(idForm);
        Formation formationModifiee = new FormationDAO().getById(id);
        //verif id correct
        if(formationModifiee == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation " + id + " inexistante.");
        }else if(formationModifiee.getId() != id){
            request.setAttribute("typeMessage", "info");
            request.setAttribute("message", "Bien tenté");
        }
        //formation de la formation
        formationModifiee.setIntitule(intitule);
        formationModifiee.setDescription(description);
        formationModifiee.setNombrePlace(nbPlace);
        formationModifiee.setDebut(dateDebut);
        formationModifiee.setFin(dateFin);
        formationModifiee.setLesJustificatifs(justificatifs);
        
        //demande de modification de la formation
        try{
            new FormationService().modifierFormation(formationModifiee);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Formation modifié.");
            actionPageSuivante = new VoirGestionFormationsAction(); //redirection
        }catch(ModificationFormationInvalideException | SuppressionJustificatifInvalideException | IOException e){
            //set msg d'erreur
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été modifié: " + e.getMessage());
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
                request.setAttribute("message", "La formation n'a pas été ajouté.");
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
        request.setAttribute("id", request.getParameter("id"));
        request.setAttribute("intitule", request.getParameter("intitule"));
        request.setAttribute("description", request.getParameter("description"));
        request.setAttribute("nbPlace", request.getParameter("nbPlace"));
        request.setAttribute("dateDebut", request.getParameter("dateDebut"));
        request.setAttribute("dateFin", request.getParameter("dateFin"));
        request.setAttribute("justificatifsInscriptionFrancais", request.getParameterValues("justificatifsInscriptionFrancais"));
        if(request.getParameterValues("justificatifsInscriptionFrancais") != null){
            for(String titre : request.getParameterValues("justificatifsInscriptionFrancais")){
                request.setAttribute(titre + "InscriptionFrancaisDescription", request.getParameter(titre + "InscriptionFrancaisDescription"));
            }
        }
        request.setAttribute("justificatifsAdmissionFrancais", request.getParameterValues("justificatifsAdmissionFrancais"));
        if(request.getParameterValues("justificatifsAdmissionFrancais") != null){
            for(String titre : request.getParameterValues("justificatifsAdmissionFrancais")){
                request.setAttribute(titre + "AdmissionFrancaisDescription", request.getParameter(titre + "AdmissionFrancaisDescription"));
            }
        }
        request.setAttribute("justificatifsInscriptionEtranger", request.getParameterValues("justificatifsInscriptionEtranger"));
        if(request.getParameterValues("justificatifsInscriptionEtranger") != null){
            for(String titre : request.getParameterValues("justificatifsInscriptionEtranger")){
                request.setAttribute(titre + "InscriptionEtrangerDescription", request.getParameter(titre + "InscriptionEtrangerDescription"));
            }
        }
        request.setAttribute("justificatifsAdmissionEtranger", request.getParameterValues("justificatifsAdmissionEtranger"));
        if(request.getParameterValues("justificatifsAdmissionEtranger") != null){
            for(String titre : request.getParameterValues("justificatifsAdmissionEtranger")){
                request.setAttribute(titre + "AdmissionEtrangerDescription", request.getParameter(titre + "AdmissionEtrangerDescription"));
            }
        }
        return new VoirModifFormationAction().execute(request, response); //modif: voir récupérer page precedente
    }
    
    private void validerFormulaire(String[] required, String[] requiredName) throws Exception{
        
        //verification de la validité du formulaire
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i].equals("null")){
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
