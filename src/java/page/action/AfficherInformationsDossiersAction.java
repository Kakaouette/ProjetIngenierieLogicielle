/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

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
            request.setAttribute("dossiers", dossiers);
            return "listeDossiers.jsp";
        }
    }
    
}
