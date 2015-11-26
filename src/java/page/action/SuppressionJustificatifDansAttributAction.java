/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.JustificatifDAO;
import modele.entite.Justificatif;

/**
 *
 * @author Arthur
 */
public class SuppressionJustificatifDansAttributAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Justificatif> justificatifs = (List<Justificatif>) request.getSession().getAttribute("justificatifs");
        Justificatif justificatifToSuppr = new JustificatifDAO().getJustificatifbyTitre(request.getParameter("justificatifASuppr"));
        for(Justificatif j: justificatifs){
            if(j.getId() == justificatifToSuppr.getId()){
                justificatifs.remove(j); //suppression du justificatif dans les justificatifs de la formation
                break;
            }
        }
        request.getSession().setAttribute("justificatifs", justificatifs);
        return new VoirAjoutFormationAction().execute(request, response);
    }
    
}
