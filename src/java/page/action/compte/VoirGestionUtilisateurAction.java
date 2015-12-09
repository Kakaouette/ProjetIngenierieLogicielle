/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.compte;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.dao.CompteDAO;
import page.action.Action;

/**
 *
 * @author Jordan
 */
public class VoirGestionUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des comptes");

        List<Compte> comptes;
        comptes = null;

        comptes = new CompteDAO().SelectAll();

        if (comptes == null) {
            request.setAttribute("message", "ERREUR : Utilisateur non trouvé dans la BDD");
            return "listeUtilisateurs.jsp";
        } else {
            List<Object[]> Tab = new ArrayList<Object[]>();

            for (Compte c : comptes) {
                Object[] o = new Object[7];
                o[0] = c.getLogin();
                o[1] = c.getNom();
                o[2] = c.getPrenom();
                o[3] = c.getType().toString();
                o[4] = c.getMail();
                o[5] = "<a class=\\\"btn btn-info btn-block\\\" href=\\\"Navigation?action=voirModifierComptes&id=" + c.getId() +"\\\"><span class='fa fa-edit fa-2x'></span></a>";
                o[6] = "<a class=\\\"btn btn-block btn-danger\\\" onclick='createDialog(" + c.getId() + ")'><span class='fa fa-remove fa-2x'></span></a>";
                Tab.add(o);
            }

            request.setAttribute("leTableau", Tab);
            request.setAttribute("sortL", 1);
            request.setAttribute("sortC", "asc");
            return "gestionComptes.jsp";
        }
    }

}
