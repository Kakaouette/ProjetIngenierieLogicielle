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
public class VoirModifierUtiilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Modifier utilisateur");
        String session = request.getParameter("session");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }   
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        
        request.getSession().setAttribute("type", type);
        request.getSession().setAttribute("nom", nom);
        request.getSession().setAttribute("prenom", prenom);
        request.getSession().setAttribute("email", email);
        request.getSession().setAttribute("login", login);
        
        return "modifierUtilisateur.jsp";
    }
}
