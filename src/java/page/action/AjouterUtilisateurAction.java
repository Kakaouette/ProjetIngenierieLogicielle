/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.logging.Level;
import java.util.logging.Logger;
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
        String type = request.getParameter("type");
        String login = request.getParameter("login");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String mdp = request.getParameter("mdp");
        
        if(type.isEmpty() || login.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty())
        {
            try {
                throw new Exception("Un des champs est vide !");
            } catch (Exception ex) {
                Logger.getLogger(AjouterUtilisateurAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
            case "Administrateur":
                nouveauCompte.setType(TypeCompte.admin);
                break;
            default:
                try {
                    throw new Exception("Selection du type invalide");
                } catch (Exception ex) {
                    Logger.getLogger(AjouterUtilisateurAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
               
        }
        
        try
        {
            new CompteService().ajouterUtilisateur(nouveauCompte);
            Compte compte = new CompteService().verifierAuthentification(login, mdp);
        
            if (compte == null) {
                request.setAttribute("error", "true");
                request.setAttribute("message", "L'utilisateur n'a pas été crée");
            } else {
                request.setAttribute("error", "false");
                request.setAttribute("message", "L'utilisateur a été crée");
            }
        }
        catch(Exception e)
        {
            request.setAttribute("error", "true");
            request.setAttribute("message", "L'utilisateur n'a pas été crée");
            Compte compte = new CompteService().verifierAuthentification(login, mdp);
            if(compte!=null){
                request.setAttribute("error", "true");
                request.setAttribute("message", "L'utilisateur existe déjà !");
            } 
        }

        return "createUser.jsp";
    }
}
