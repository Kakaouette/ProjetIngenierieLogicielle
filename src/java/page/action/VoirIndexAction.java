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

/**
 *
 * @author roulonn
 */
public class VoirIndexAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Accueil");
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        
        if(compte == null)
            return "index.jsp";
        else{
            if(compte.getType()== TypeCompte.secretaire_general)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire generale
                return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }
            else if(compte.getType()== TypeCompte.secretaire_formation)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire formation
                return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }
            else if(compte.getType()== TypeCompte.admin)
            {
                // traitement a effectuer pour la page d'accueil de l'admin
                return "accueil.jsp";
            }
            else if(compte.getType()== TypeCompte.reponsable_formation)
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