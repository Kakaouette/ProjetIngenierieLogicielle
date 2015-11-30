/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;

/**
 *
 * @author Sahare
 */
public class VoirAjoutDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter un dossier");
        
        //recuperation du formulaire
        String formationIntitule = request.getParameter("formationIntitule");
        //verification de la validité du formulaire
        if(formationIntitule.isEmpty()){
            try {
                throw new Exception();
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", "Un des champs requis est vide.");
                return stayHere(request, response); //redirection
            }
        }
        
        //mise en forme des données
        Formation formation = new FormationDAO().getFormationByIntitule(formationIntitule);
        if(formation == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation inconnue");
            request.setAttribute("focus", "formationIntitule");
            return stayHere(request, response); //redirection
        }
        
        request.setAttribute("formationIntitule", request.getParameter("formationIntitule"));
        return "ajoutDossier.jsp";
    }
    
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("formationIntitule", request.getParameter("formationIntitule"));
        return new VoirValidationJustificatifsDossierAction().execute(request, response); //modif: voir récupérer page precedente
    }
}
