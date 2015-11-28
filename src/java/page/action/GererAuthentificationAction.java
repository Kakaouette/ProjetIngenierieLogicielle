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
 * Gestion de l'identification, vérification des entrée identifiant/mot de passe
 * Si echec, retour vers la page d'index
 *
 * @author Nicolas Roulon
 */
public class GererAuthentificationAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String session = request.getParameter("session");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }       

        String identifiant = request.getParameter("identifiant");
        String mdp = request.getParameter("mdp");
        
        Compte compte;
        compte = null;

        compte = new CompteService().verifierAuthentification(identifiant, mdp);
        if (compte == null) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Vos identifiants sont incorrectes");
            return "index.jsp";
        } else {
            request.getSession().setAttribute("compte", compte);
            return "accueil.jsp";
        }
    }
}
