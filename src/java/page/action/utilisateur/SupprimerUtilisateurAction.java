/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.utilisateur;

import page.action.utilisateur.VoirModifierComptesAction;
import page.action.utilisateur.VoirGestionUtilisateurAction;
import page.action.accueil.VoirIndexAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import page.action.Action;
import service.CompteService;

/**
 *
 * @author dorian
 */
public class SupprimerUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String idS = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idS);
        } catch (Exception e) {
            return new VoirIndexAction().execute(request, response);
        }

        CompteService compteService = new CompteService();
        boolean supprimer = compteService.supprimerUtilisateur(id);
        if (supprimer) {
            request.setAttribute("message", "Suppression effectuée");
            return new VoirGestionUtilisateurAction().execute(request, response);
        } else {
            request.setAttribute("message", "Erreur: Le compte n'existe pas");
            return new VoirModifierComptesAction().execute(request, response);
        }
    }
}