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
public class VoirGestionComptesAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des comptes");
        
        List<Compte> comptes;
        comptes = new CompteDAO().SelectAll(); //recuperation des comptes pour la page suivante

        if (comptes == null) {
            request.setAttribute("message", "Aucun compte trouvé dans la BDD");
            return "gestionComptes.jsp";
        } else {
            request.setAttribute("comptes", comptes);
            return "gestionComptes.jsp";
        }
    }
    
}
