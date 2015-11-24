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
import modele.dao.DossierDAO;
import modele.entite.Dossier;

/**
 *
 * @author Jordan
 */
public class AfficherInformationsDossiersAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Liste dossiers");
        
        List<Dossier> dossiers;
        dossiers = null;

        dossiers = new DossierDAO().SelectAll();

        if (dossiers == null) {
            request.setAttribute("message", "ERREUR : Dossier non trouvé dans la BDD");
            return "listeDossiers.jsp";
        } else {
            List<Object[]> Tab = new ArrayList<Object[]>();

            for (Dossier c : dossiers) {
                Object[] o = new Object[7];
                o[0] = c.getId();
                o[1] = c.isAdmissible();
                o[2] = c.getDate();
                o[3] = c.getEtat();
                o[4] = c.getDemandeFormation().getIntitule();
                o[5] = c.getEtudiant().getId();
                o[6] = "<a class=\\\"btn btn-info btn-block\\\" href=\\\"Navigation?action=voirModifierUtilisateur&id=" + o[0] +"\\\">Modifier</a>";
                //o[6] = "<a class=\\\"btn btn-danger btn-block\\\" href=\\\"Navigation?action=voirModifierUtilisateur&id=" + c.getId() +"\\\">Supprimer</a>";
                Tab.add(o);
            }

            request.setAttribute("leTableau", Tab);
            request.setAttribute("sortL", 1);
            request.setAttribute("sortC", "asc");
            return "listeDossiers.jsp";
        }
    }
    
}
