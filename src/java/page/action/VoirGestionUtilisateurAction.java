/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.CompteDAO;
import modele.entite.Compte;

/**
 *
 * @author Arthur
 */
public class VoirGestionUtilisateurAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des utilisateurs");
        List<Compte> comptes = new CompteDAO().SelectAll(); //recuperation des comptes pour la page suivante
        request.setAttribute("utilisateurs", comptes);
        return "listeUtilisateurs.jsp";
    }
    
}
