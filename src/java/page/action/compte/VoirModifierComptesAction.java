/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.compte;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.CompteDAO;
import modele.dao.FormationDAO;
import modele.entite.Compte;
import page.action.Action;

/**
 *
 * @author roulonn
 */
public class VoirModifierComptesAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Modifier utilisateur");
        
        String valueButton = request.getParameter("action");
        //System.out.println(valueButton);
        
        int id=Integer.parseInt(request.getParameter("id"));
        
        Compte compte = new CompteDAO().getById(id);
        
        if(compte == null)
            return new VoirGestionUtilisateurAction().execute(request, response);
        
        String login = compte.getLogin();
        String nom = compte.getNom();
        String prenom = compte.getPrenom();
        String email = compte.getMail();
        
        request.setAttribute("type", compte.getType());
        request.setAttribute("nom", nom);
        request.setAttribute("prenom", prenom);
        request.setAttribute("login", login);
        request.setAttribute("email", email);
        request.setAttribute("id", id);
        request.setAttribute("compte", compte);
        request.setAttribute("lesFormations", new FormationDAO().SelectAll());
        
        return "modifierUtilisateur.jsp";
    }
}