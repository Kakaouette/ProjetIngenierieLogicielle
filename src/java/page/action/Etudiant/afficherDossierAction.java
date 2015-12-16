/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.Etudiant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.entite.TypeCompte;
import page.action.Action;
import page.action.dossier.AfficherInformationsDossiersAction;

/**
 *
 * @author Drachenfels
 */
public class afficherDossierAction implements Action{
    
     @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Acceuil");
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        
        
        if(compte == null)
            return "connexionEtudiant.jsp";
        else{
            if(compte.getType()== TypeCompte.secrétaire_formation)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire de formation
                return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }else return "connexionEtudiant.jsp";     
        }
    }  
}
