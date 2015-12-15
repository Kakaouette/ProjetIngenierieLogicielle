/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.formation;

import page.action.Action;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class VoirGestionFormationsAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des formation");
        List<Formation> formations = new FormationDAO().SelectAll(); //recuperation des formations pour la page suivante
        
        if (formations == null) {
            request.setAttribute("typeMessage", "warning");
            request.setAttribute("message", "Aucune formation dans la BDD");
        } else {
            List<Object[]> Tab = new ArrayList<Object[]>();
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            for (Formation f : formations) {
                Object[] o = new Object[7];
                o[0] = f.getIntitule();
                o[1] = f.getDescription();
                o[2] = f.getNombrePlace();
                if(f.getDebut() != null){
                    o[3] = df.format(f.getDebut());
                }
                if(f.getFin() != null){
                    o[4] = df.format(f.getFin());
                }
                o[5] = "<a class=\\\"btn btn-info btn-block\\\" href=\\\"Navigation?action=voirModifFormation&id=" + f.getId() +"\\\"><span class='fa fa-edit fa-2x'></span</a>";
                o[6] = "<a class=\\\"btn btn-danger btn-block\\\" onclick='createDialog(" + f.getId() + ")'><span class='fa fa-remove fa-2x'></span></a>";
                Tab.add(o);
            }

            request.setAttribute("leTableau", Tab);
            request.setAttribute("sortL", 0); //colonne sort par defaut
            request.setAttribute("sortC", "asc"); //type de sort
        }
        return "gestionFormations.jsp";
    }
    
}
