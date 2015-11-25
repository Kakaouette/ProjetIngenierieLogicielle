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
import modele.entite.TypeCompte;

/**
 *
 * @author roulonn
 */
public class VoirModifierCompteAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Modifier compte");
        String valueButton = request.getParameter("action");
        //System.out.println(valueButton);
        
        int id=Integer.parseInt(request.getParameter("id"));
        
        Compte compte = new CompteDAO().getById(id);
        
        String login = compte.getLogin();
        String nom = compte.getNom();
        String prenom = compte.getPrenom();
        String email = compte.getMail();
        TypeCompte type = compte.getType();
        
        request.setAttribute("type", type);
        request.setAttribute("nom", nom);
        request.setAttribute("prenom", prenom);
        request.setAttribute("login", login);
        request.setAttribute("email", email);
        request.setAttribute("compte", compte);
        
        
        return "modifierCompte.jsp";
    }
}
