/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
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
            request.setAttribute("logout", "logout");
            return "index.jsp";
        }

        Compte compte = null;
        AssertionImpl AI = (AssertionImpl) (Assertion) request.getSession().getAttribute("_const_cas_assertion_");
        if (AI.isValid()) {
            System.out.println(AI.getPrincipal().getName());
            compte = new CompteService().verifierAuthentification(AI.getPrincipal().getName());
            request.getSession().setAttribute("compte", compte);
        }

        return new VoirIndexAction().execute(request, response);

    }
}
