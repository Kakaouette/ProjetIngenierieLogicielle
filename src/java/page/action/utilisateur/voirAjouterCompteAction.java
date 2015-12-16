/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.utilisateur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Compte;
import page.action.Action;

/**
 * Ajouter un utilisateur dans le Système d'Information
 * Si succès, ajout dans la base 
 * Si échec, aucun ajout dans la base et retour à l'accueil admin
 * @author phanjoseph
 */
public class voirAjouterCompteAction implements Action {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Créer un compte");
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        request.setAttribute("lesFormations", new FormationDAO().SelectAll());
        
        return "createUser.jsp";
    }
}