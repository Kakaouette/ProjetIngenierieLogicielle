/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Justificatif;

/**
 *
 * @author Arthur
 */
public class VoirAjoutFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter une formation");
        if(request.getSession().getAttribute("justificatifs") == null){
            List<Justificatif> justificatifs = new ArrayList<Justificatif>();
            request.getSession().setAttribute("justificatifs", justificatifs);
        }
        return "ajoutFormation.jsp";
    }
    
}
