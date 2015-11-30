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
import modele.dao.JustificatifDAO;
import modele.entite.Justificatif;
import modele.entite.TypeJustificatifEtranger;

/**
 *
 * @author Arthur
 */
public class VoirAjoutFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter une formation");
        List<Justificatif> tousJustificatifs = new JustificatifDAO().SelectAll();
        request.setAttribute("tousJustificatifs", tousJustificatifs);
        List<Justificatif> justificatifsInscription = new JustificatifDAO().SelectInscriptions();
        if(justificatifsInscription == null){
            justificatifsInscription = new ArrayList<Justificatif>();
        }
        request.setAttribute("justificatifsInscription", justificatifsInscription);
        List<Justificatif> justificatifsAdmissibilite = new JustificatifDAO().SelectAdmissibilite();
        if(justificatifsAdmissibilite == null){
            justificatifsAdmissibilite = new ArrayList<Justificatif>();
        }
        request.setAttribute("justificatifsAdmissibilite", justificatifsAdmissibilite);
        List<Justificatif> justificatifsEtranger = new JustificatifDAO().SelectEtranger();
        if(justificatifsEtranger == null){
            justificatifsEtranger = new ArrayList<Justificatif>();
        }
        request.setAttribute("justificatifsEtranger", justificatifsEtranger);
        
        return "ajoutFormation.jsp";
    }
    
}
