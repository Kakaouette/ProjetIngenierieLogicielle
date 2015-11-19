/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.CompteDAO;
import modele.entite.Compte;
import service.CompteService;

/**
 *
 * @author dorian
 */
public class SupprimerUtilisateurAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        String session = request.getParameter("session");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }       
        String login = request.getParameter("login");

        
        CompteService compteService = new CompteService();
        compteService.supprimerUtilisateur(login);
        request.setAttribute("message", "Suppression effectuée");
        return "listeUtilisateurs.jsp";
    }
}
