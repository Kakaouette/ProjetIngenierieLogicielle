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
public class VoirAjoutJustificatifDansAttribut implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter un justificatif dans la formation");
        List<Justificatif> tousJustificatifs = new JustificatifDAO().SelectAll();
        request.setAttribute("tousJustificatifs", tousJustificatifs);
        return "ajoutJustificatifDansAttribut.jsp";
    }
    
}
