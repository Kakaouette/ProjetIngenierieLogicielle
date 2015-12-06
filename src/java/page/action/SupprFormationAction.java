/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import service.FormationService;
import service.exception.SuppressionFormationInvalideException;
import service.exception.SuppressionJustificatifInvalideException;

/**
 *
 * @author Arthur
 */
public class SupprFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //recuperation de l'id
        String idForm = request.getParameter("id");
        
        //verification de la validité du parametre
        String[] required = {idForm};
        String[] requiredName = {"id de la formation"};
        try {
            validerFormulaire(required, requiredName);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return stayHere(request, response); //redirection
        }
        
        //mise en forme des données
        int id = Integer.parseInt(idForm);
        //recuperation de la formation
        Formation formation = new FormationDAO().getById(id);
        //verif id correct
        if(formation == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation " + id + " inexistante.");
        }else if(formation.getId() != id){
            request.setAttribute("typeMessage", "info");
            request.setAttribute("message", "Bien tenté");
        }
        
        //demande de suppression de la formation
        try{
            new FormationService().supprimerFormation(formation);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Formation supprimé.");
        }catch(SuppressionFormationInvalideException | SuppressionJustificatifInvalideException | IOException e){
            //set msg d'erreur
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été supprimé: " + e.getMessage());
            //modif requete celon le type d'erreur
            if(e instanceof IOException){ //exception bdd
                request.setAttribute("message", "La formation n'a pas été ajouté.");
            }
            //reload la page
            return stayHere(request, response); //redirection
        }
        
        return new VoirGestionFormationsAction().execute(request, response); //redirection
    }
    
    /**
     * 
     * @param request
     * @param response
     * @return action de voir la page + retour de String correspondant à la page
     */
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        return new VoirGestionFormationsAction().execute(request, response); //modif: voir récupérer page precedente
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
