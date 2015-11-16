/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.entite.TypeCompte;
import service.CompteService;

/**
 * Ajouter un utilisateur dans le Système d'Information
 * Si succès, ajout dans la base 
 * Si échec, aucun ajout dans la base et retour à l'accueil admin
 * @author phanjoseph
 */
public class AjouterUtilisateurAction implements Action {
    
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
        String email = request.getParameter("email");
        String mdp = request.getParameter("mdp");

        Compte nouveauCompte = new Compte();
        nouveauCompte.setLogin(login);
        nouveauCompte.setNom(nom);
        nouveauCompte.setPrenom(prenom);
        nouveauCompte.setMail(email);
        nouveauCompte.setMdp(mdp);
        
        switch(type)
        {
            case "Secretaire Pole":
                nouveauCompte.setType(TypeCompte.secretaire_general);
                break;
            case "Secretaire Formation":
                nouveauCompte.setType(TypeCompte.secretaire_formation);
                break;
            case "Commission":
                nouveauCompte.setType(TypeCompte.commission);
                break;
            case "Directeur Pole":
                nouveauCompte.setType(TypeCompte.directeur_pole);
                break;
            default:
                //nouveauCompte.setType(TypeCompte.admin);
                break;
               
        }
        
        new CompteService().ajouterUtilisateur(nouveauCompte);

        return "index.jsp";
    }
}
