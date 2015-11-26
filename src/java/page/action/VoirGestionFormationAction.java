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
 * @author Arthur
 */
public class VoirGestionFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des formation");
        List<Formation> formations = new FormationDAO().SelectAll(); //recuperation des formations pour la page suivante
        request.setAttribute("formations", formations);
        
        if (formations == null) {
            request.setAttribute("message", "Aucune formation dans la BDD");
        } else {
            List<Object[]> Tab = new ArrayList<Object[]>();

            for (Formation f : formations) {
                Object[] o = new Object[7];
                o[0] = f.getIntitule();
                o[1] = f.getDescription();
                o[2] = f.getNombrePlace();
                o[3] = "<a class=\\\"btn btn-info btn-block\\\" href=\\\"Navigation?action=voirModifierFormation&id=" + f.getId() +"\\\">Modifier</a>";
                o[4] = "<a class=\\\"btn btn-danger btn-block\\\" onclick='createDialog(" + f.getId() + ")'>Supprimer</a>";
                Tab.add(o);
            }

            request.setAttribute("leTableau", Tab);
            request.setAttribute("sortL", 0); //colonne sort par defaut
            request.setAttribute("sortC", "asc"); //type de sort
        }
        return "gestionFormations.jsp";
    }
    
}
