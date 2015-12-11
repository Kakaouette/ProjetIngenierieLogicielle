/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.formation;

import page.action.Action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Compte;
import modele.entite.Formation;
import modele.entite.TypeCompte;

/**
 *
 * @author Arthur
 */
public class VoirGestionDatesInscriptionAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des dates d'inscription");
        
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        List<Formation> formations = null; 
        //recuperation des formations pour la page suivante celon le type de compte
        if(compte.getType() == TypeCompte.admin || compte.getType() == TypeCompte.secrétaire_inscription){
            formations = new FormationDAO().SelectAll();
        }else if(compte.getType() == TypeCompte.secrétaire_formation){
            formations = compte.getFormationAssocie();
        }
        if(formations == null){
            formations = new ArrayList<Formation>();
            request.setAttribute("message", "Aucune formation trouvé dans la BDD");
        }
        
        String intitule = formations.get(0).getIntitule();
        //keep formulaire
        if(request.getParameter("intitule") != null){
            intitule = request.getParameter("intitule");
        }
        request.setAttribute("intitule", intitule);
        //recuperation de la formation
        Formation formationModifiee = new FormationDAO().getFormationByIntitule(intitule);
        if(formationModifiee == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation inconnue");
            return stayHere(request, response); //redirection
        }
        request.setAttribute("description", formationModifiee.getDescription());
        request.setAttribute("nbPlace", formationModifiee.getNombrePlace());
        Date debut = new Date();
        if(formationModifiee.getDebut() != null){
            debut = formationModifiee.getDebut();
        }
        Date fin = new Date();
        if(formationModifiee.getFin() != null){
            fin = formationModifiee.getFin();
        }
        if(formationModifiee.getDebut() != null && formationModifiee.getFin()!= null){
            if(formationModifiee.getDebut().before(new Date()) && formationModifiee.getFin().after(new Date())){ //verif formation editable
                request.setAttribute("typeMessage", "warning");
                request.setAttribute("message", "La formation ne peut être modifier pendant la période d'inscription");
            }
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        request.setAttribute("dateDebut", df.format(debut));
        request.setAttribute("dateFin", df.format(fin));
        request.setAttribute("formations", formations);
        
        return "gestionDatesInscription.jsp";
    }
    
    /**
     * 
     * @param request
     * @param response
     * @return action de voir la page + retour de String correspondant à la page
     */
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("intitule", request.getParameter("intitule"));
        request.setAttribute("description", request.getParameter("description"));
        request.setAttribute("nbPlace", request.getParameter("nbPlace"));
        request.setAttribute("dateDebut", request.getParameter("dateDebut"));
        request.setAttribute("dateFin", request.getParameter("dateFin"));
        return new VoirGestionDatesInscriptionAction().execute(request, response); //modif: voir récupérer page precedente
    }
}
