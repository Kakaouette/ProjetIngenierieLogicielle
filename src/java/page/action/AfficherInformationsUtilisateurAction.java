/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.dao.CompteDAO;

/**
 *
 * @author Jordan
 */
public class AfficherInformationsUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String session = request.getParameter("session");
        request.setAttribute("titre", "Liste utilisateurs");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }       

        String login = request.getParameter("login");
        
        Compte compte = null;
        List<Compte> comptes;
        comptes = null;

        comptes = new CompteDAO().SelectAll();
        compte = new CompteDAO().getComptebyLogin(login);

        if (comptes == null) {
            request.setAttribute("message", "ERREUR : Utilisateur non trouvé dans la BDD");
            return "listeUtilisateurs.jsp";
        } else {
            request.getSession().setAttribute("comptes", comptes);
            request.getSession().setAttribute("compte", compte);
            return "listeUtilisateurs.jsp";
        }
    }
    
}
