/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.FormationService;
import service.SuppressionFormationInvalideException;

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
        if(idForm.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return stayHere(request, response); //redirection
            }
        }
        
        //mise en forme des données
        int id = Integer.parseInt(idForm);
        
        //demande de suppression de la formation
        try{
            new FormationService().supprimerFormation(id);
            request.setAttribute("typeMessage", "success");
            request.setAttribute("message", "Formation supprimé.");
        }catch(SuppressionFormationInvalideException e){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été supprimé: " + e.getMessage());
            return stayHere(request, response); //redirection
        }catch(Exception e){ //exception bdd
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "La formation n'a pas été supprimé.");
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
}
