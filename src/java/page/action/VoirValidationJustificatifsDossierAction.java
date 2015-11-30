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
import modele.entite.Formation;
import modele.entite.Justificatif;

/**
 *
 * @author Sahare
 */
public class VoirValidationJustificatifsDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter un dossier - Validation des justificatifs");
        
        List<Formation> formations = new FormationDAO().SelectAll(); //recuperation des formations pour la page suivante
        if(formations == null) {
            formations = new ArrayList<Formation>();
            request.setAttribute("message", "Aucune formation trouvé dans la BDD");
        }else{
            request.setAttribute("formations", formations);
        }
        
        String intitule = request.getParameter("formationIntitule");
        //keep formulaire
        if(request.getParameter("formationIntitule") != null){
            intitule = request.getParameter("formationIntitule");
        }else{
            intitule = formations.get(0).getIntitule();
        }
        request.setAttribute("formationIntitule", intitule);
        Formation formation = new FormationDAO().getFormationByIntitule(intitule);
        List<Justificatif> justificatifs = formation.getLesJustificatifs();
        if(justificatifs == null) {
            justificatifs = new ArrayList<Justificatif>();
            request.setAttribute("message", "Aucun justificatif trouvé dans la BDD");
        }else{
            request.setAttribute("justificatifs", justificatifs);
            if(request.getParameter("justificatifs") != null){
                request.setAttribute("justificatifsChecked", request.getParameter("justificatifs"));
            }
        }
        return "validationJustificatifsDossier.jsp";
    }
    
}
