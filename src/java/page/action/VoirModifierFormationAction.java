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
import modele.dao.FormationDAO;
import modele.entite.Formation;

/**
 *
 * @author Arthur
 */
public class VoirModifierFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //recuperation de l'id
        String idForm = request.getParameter("id");
        
        //verification de la validité du parametre
        if(idForm.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                Logger.getLogger(VoirModifierFormationAction.class.getName()).log(Level.SEVERE, null, ex); //msg console
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationAction().execute(request, response);
            }
        }
        
        //mise en forme des données
        int id = Integer.parseInt(idForm);
        Formation formation = new FormationDAO().getById(id);
        if(formation == null){
            try {
                throw new Exception("Formation inexistante.");
            } catch (Exception ex) {
                Logger.getLogger(VoirModifierFormationAction.class.getName()).log(Level.SEVERE, null, ex); //msg console
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationAction().execute(request, response);
            }
        }
        
        //set attribut pour next page
        request.setAttribute("id", id);
        request.setAttribute("intitule", formation.getIntitule());
        request.setAttribute("description", formation.getDescription());
        request.setAttribute("nbPlace", formation.getNombrePlace());
        request.setAttribute("dateDebut", formation.getDebut());
        request.setAttribute("dateFin", formation.getFin());
        request.getSession().setAttribute("justificatifs", formation.getLesJustificatifs());
        
        return "modiferFormation.jsp";
    }
    
}
