/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;

/**
 *
 * @author Etienne
 */
public class VoirListeFormationAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Création de compte - Vérication pièces jointes");
        int idFormation = Integer.parseInt((String)request.getAttribute("idFormation"));
        Formation uneFormation = new FormationDAO().getById(idFormation);
        List<Justificatif> justificatifs = uneFormation.getLesJustificatifs();
        request.setAttribute("justificatifs", justificatifs);
        return "";
    }
}
