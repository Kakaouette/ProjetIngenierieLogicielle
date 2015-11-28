/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;

/**
 *
 * @author Arthur
 */
public class VoirModifFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Modifer une formation");
        
        //recuperation de l'id
        String idForm = request.getParameter("id");
        
        //verification de la validité du parametre
        if(idForm.isEmpty()){
            try {
                throw new Exception("Un des champs requis est vide.");
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationsAction().execute(request, response); //redirection
            }
        }
        int id = Integer.parseInt(idForm);
        Formation formation = new FormationDAO().getById(id);
        if(formation == null){
            try {
                throw new Exception("Formation inexistante.");
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationsAction().execute(request, response); //redirection
            }
        }
        
        //recuperation des données
        String intitule = formation.getIntitule();
        String description = formation.getDescription();
        int nbPlace = formation.getNombrePlace();
        Date debut = formation.getDebut();
        Date fin = formation.getFin();
        List<Justificatif> justificatifs = formation.getLesJustificatifs();
                
        //remplissage du formulaire
        if(request.getAttribute("intitule") == null){
            if(intitule != null){
                request.setAttribute("intitule", intitule);
            }
        }
        if(request.getAttribute("description") == null){
            if(description != null){
                request.setAttribute("description", description);
            }
        }
        if(request.getAttribute("nbPlace") == null){
            request.setAttribute("nbPlace", nbPlace);
        }
        if(request.getAttribute("dateDebut") == null){
            if(debut != null){
                request.setAttribute("dateDebut", debut);
            }
        }
        if(request.getAttribute("dateFin") == null){
            if(fin != null){
                request.setAttribute("dateFin", fin);
            }
        }
        if(request.getSession().getAttribute("justificatifs") == null){
            if(justificatifs == null){
                justificatifs = new ArrayList<Justificatif>();
            }
            request.getSession().setAttribute("justificatifs", justificatifs);
        }
        
        return "ajoutFormation.jsp";
    }
    
}
