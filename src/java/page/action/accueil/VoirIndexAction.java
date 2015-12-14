/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.accueil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.entite.TypeCompte;
import page.action.Action;
import page.action.dossier.AfficherInformationsDossiersAction;
import page.action.dossier.ConsulterDossierAction;

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
            if(compte.getType()== TypeCompte.secrétaire_formation)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire de formation
                return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }
            else if(compte.getType()== TypeCompte.secrétaire_inscription)
            {
                // traitement a effectuer pour la page d'accueil de la secretaire d'inscriptions
                return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }else if(compte.getType()== TypeCompte.responsable_commission)
            {
                // traitement a effectuer pour la page d'accueil du responsable de commission
                return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }else if(compte.getType()== TypeCompte.responsable_formation)
            {
                // traitement a effectuer pour la page d'accueil du responsable de formation
                return "accueil.jsp";
                //return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }else if(compte.getType()== TypeCompte.responsable_administrative)
            {
                // traitement a effectuer pour la page d'accueil du responsable administratif
                return "accueil.jsp";
                //return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }else if(compte.getType()== TypeCompte.directeur_pole)
            {
                // traitement a effectuer pour la page d'accueil du directeur du pôle
                return "accueil.jsp";
                //return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }else if(compte.getType()== TypeCompte.admin)
            {
                // traitement a effectuer pour la page d'accueil de l'administrateur
                return "accueil.jsp";
                //return new AfficherInformationsDossiersAction().execute(request, response); // charge les dossiers + retourne "listeDossiers.jsp"
            }
            else
            {
                return "accueil.jsp";
            }
            /*switch(compte.getType()){
                case TypeCompte.responsable_commission:{
                    
                }*/
        }
    }
}
