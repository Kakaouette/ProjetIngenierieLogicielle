/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.formation;

import page.action.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arthur
 */
public class VoirAjoutFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter une formation");
        
        if(request.getAttribute("freeForm") != null){
            if((boolean)request.getAttribute("freeForm") == true){
                freeForm(request, response);
            }
        }
        return "ajoutFormation.jsp";
    }
    
    private void freeForm(HttpServletRequest request, HttpServletResponse response){
        //free formulaire
        request.setAttribute("intitule", null);
        request.setAttribute("description", null);
        request.setAttribute("nbPlace", null);
        request.setAttribute("dateDebut", null);
        request.setAttribute("dateFin", null);
        request.setAttribute("justificatifsInscriptionFrancais", null);
        request.setAttribute("justificatifsAdmissionFrancais", null);
        request.setAttribute("justificatifsInscriptionEtranger", null);
        request.setAttribute("justificatifsAdmissionEtranger", null);
    }
}
