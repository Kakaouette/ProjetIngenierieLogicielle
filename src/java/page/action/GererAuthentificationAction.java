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
            return "index.jsp";
        }       

        String identifiant = request.getParameter("identifiant");
        String mdp = request.getParameter("mdp");
        
        Compte compte;
        compte = null;

        compte = new CompteService().verifierAuthentification(identifiant, mdp);
        if (compte == null) {
            request.setAttribute("message", "Vos identifiants sont incorrectes");
            return "index.jsp";
        } else {
            request.getSession().setAttribute("compte", compte);
            
            if(compte.getType()== TypeCompte.secretaire_general)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire generale
                return new VoirGestionDossierAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }
            else if(compte.getType()== TypeCompte.secretaire_formation)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire formation
                return new VoirGestionDossierAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }
            else if(compte.getType()== TypeCompte.admin)
            {
                // traitement a effectuer pour la page d'accueil de l'admin
                return "accueil.jsp";
            }
            else if(compte.getType()== TypeCompte.commission)
            {
                // traitement a effectuer pour la page d'accueil de la commission
                return "accueil.jsp";
            }
            else if(compte.getType()== TypeCompte.directeur_pole)
            {
                // traitement a effectuer pour la page d'accueil du directeur de pôle
                return "accueil.jsp";
            }
            else
            {
                return "accueil.jsp";
            }
        }
    }
}
