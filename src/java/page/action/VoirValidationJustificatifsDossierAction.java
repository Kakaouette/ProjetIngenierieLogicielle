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

/**
 *
 * @author Sahare
 */
public class VoirValidationJustificatifsDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter un dossier");
        
        List<Formation> formations = new FormationDAO().SelectAll(); //recuperation des formations pour la page suivante

        if(formations == null) {
            formations = new ArrayList<Formation>();
            request.setAttribute("message", "Aucune formation trouvé dans la BDD");
        }else{
            request.setAttribute("formations", formations);
        }
        if(request.getParameter("formationIntitule") != null){
            request.setAttribute("formationIntitule", request.getParameter("formationIntitule"));
        }
        return "validationJustificatifsDossier.jsp";
    }
    
}
