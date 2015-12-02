/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
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
        if(compte.getType() == TypeCompte.admin || compte.getType() == TypeCompte.secretaire_general){
            formations = new FormationDAO().SelectAll();
        }else if(compte.getType() == TypeCompte.secretaire_formation){
            formations = compte.getFormationAssocie();
        }
        if(formations == null){
            formations = new ArrayList<Formation>();
            request.setAttribute("message", "Aucune formation trouvé dans la BDD");
        }
        request.setAttribute("formations", formations);
        
        return "gestionDatesInscription.jsp";
    }
    
}
