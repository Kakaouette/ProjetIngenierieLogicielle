/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import service.CompteService;

/**
 *
 * @author Jordan
 */
public class ModifierUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String session = request.getParameter("session");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }       

        String type = request.getParameter("type");
        String login = request.getParameter("login");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String mail = request.getParameter("email");
        String mdp = request.getParameter("motDePasse");
        int idCompte = ((Compte)(request.getSession().getAttribute("compte"))).getId();

        Boolean update = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);

        if (update == false) {
            request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
            return "modifierUtilisateur.jsp";
        } else {
            request.setAttribute("message", "Modification effectuée");
            return "listeUtilisateurs.jsp";
        }
    }
    
}
