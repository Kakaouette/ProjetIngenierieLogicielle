/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.FormationService;
import service.SuppressionFormationInvalideException;

/**
 *
 * @author Arthur
 */
public class SuppressionFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //recuperation de l'id
        String idForm = request.getParameter("id");
        
        //verification de la validité du parametre
        if(idForm.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(AjoutFormationAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //mise en forme des données
        int id = Integer.parseInt(idForm);
        
        //demande de creation du dossier
        try{
            new FormationService().supprimerFormation(id);
            request.setAttribute("error", "false");
            request.setAttribute("message", "Formation supprimé.");
        }catch(SuppressionFormationInvalideException e){
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été supprimé: " + e.getMessage());
        }catch(Exception e){ //exception bdd
            request.setAttribute("error", "true");
            request.setAttribute("message", "La formation n'a pas été supprimé.");
        }
        
        return "gestionFormation.jsp"; //redirection
    }
    
}
