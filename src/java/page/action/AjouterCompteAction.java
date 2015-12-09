/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.entite.TypeCompte;
import service.CompteService;

/**
 * Ajouter un utilisateur dans le Système d'Information Si succès, ajout dans la
 * base Si échec, aucun ajout dans la base et retour à l'accueil admin
 *
 * @author phanjoseph
 */
public class AjouterCompteAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Créer un compte");
        
        String type = request.getParameter("type");
        String login = request.getParameter("login");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String mdp = request.getParameter("mdp");

        if (type.isEmpty() || login.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            request.setAttribute("error", "true");
            request.setAttribute("message", "Tous les champs doivent être remplis !");

            return "createUser.jsp";
        }

        Compte nouveauCompte = new Compte();
        nouveauCompte.setLogin(login);
        nouveauCompte.setNom(nom);
        nouveauCompte.setPrenom(prenom);
        nouveauCompte.setMail(email);
        nouveauCompte.setMdp(mdp);
        
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            request.setAttribute("error", "true");
            request.setAttribute("message", "L'adresse email n'est pas valide");

            return "createUser.jsp";
        }
        nouveauCompte.setType(TypeCompte.valueOf(type));

        try {
            new CompteService().ajouterUtilisateur(nouveauCompte);
            Compte compte = new CompteService().verifierAuthentification(login, mdp);

            if (compte == null) {
                request.setAttribute("error", "true");
                request.setAttribute("message", "L'utilisateur n'a pas été crée");
            } else {
                request.setAttribute("error", "false");
                request.setAttribute("message", "L'utilisateur a été crée");
            }
        } catch (Exception e) {
            request.setAttribute("error", "true");
            request.setAttribute("message", "L'utilisateur n'a pas été crée");
            Compte compte = new CompteService().verifierAuthentification(login, mdp);
            if (compte != null) {
                request.setAttribute("error", "true");
                request.setAttribute("message", "L'utilisateur existe déjà !");
            }
        }

        return "createUser.jsp";
    }
}