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
public class AjoutJustificatifDansAttribut implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter une formation");
        List<Justificatif> justificatifs = (List<Justificatif>) request.getSession().getAttribute("justificatifs");
        Justificatif justificatifToAdd = new JustificatifDAO().getJustificatifbyTitre(request.getParameter("justificatifAAjouter"));
        if(justificatifToAdd == null){
            //erreur
        }
        justificatifs.add(justificatifToAdd); //ajout du justificatif dans les justificatifs de la formation
        request.getSession().setAttribute("justificatifs", justificatifs);
        return "ajouterFormation.jsp";
    }
    
}
