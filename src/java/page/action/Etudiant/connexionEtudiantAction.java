/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.Etudiant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.entite.Etudiant;
import modele.entite.TypeCompte;
import page.action.Action;
import page.action.dossier.AfficherInformationsDossiersAction;
import service.Etudiant.EtudiantService;

/**
 *
 * @author Drachenfels
 */
public class connexionEtudiantAction implements Action{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Accueil");
        String ine = (String) request.getParameter("INE");
        String nom = (String) request.getParameter("nom");
        String prenom = (String) request.getParameter("prenom");
        Etudiant etu = new EtudiantService().verifAuthentification(ine, nom, prenom);
        
        if(etu == null){
            request.setAttribute("message", "Les informations saisies n'ont pas permis de vous authentifier.");
            request.setAttribute("typeMessage", "danger");
            return "connexionEtudiant.jsp";
        }
        else {
            request.setAttribute("etu", etu);
            request.setAttribute("dossiers", new EtudiantService().dossiersEtu(etu));
            return "listeDossiersEtudiant.jsp";
        }
    }
}
