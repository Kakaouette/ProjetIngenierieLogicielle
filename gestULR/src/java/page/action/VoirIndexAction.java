/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;

/**
 *
 * @author roulonn
 */
public class VoirIndexAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        request.setAttribute("titre", "Accueil");
        
        String session = request.getParameter("session");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }
        
        if(compte == null)
            return "index.jsp";
        else
            return "accueil.jsp";
    }
}