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
 * Action a déclencher pour charger tout les dossiers de la BDD
 * 
 * @author Jordan
 */
public class VoirGestionDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des dossiers");
        List<Dossier> dossiers = new DossierDAO().SelectAll(); //recuperation des comptes pour la page suivante        
        List<Object[]> Tab = new ArrayList<Object[]>();

            for (Dossier c : dossiers) {
                Object[] o = new Object[7];
                o[0] = c.getId();
                o[1] = c.isAdmissible();
                o[2] = c.getDate();
                o[3] = c.getEtat();
                o[4] = c.getDemandeFormation().getIntitule();
                o[5] = c.getEtudiant().getId();
                o[6] = "<a class=\\\"btn btn-info btn-block\\\" href=\\\"Navigation?action=#\\\">Modifier</a>";
                Tab.add(o);
            }

            request.setAttribute("leTableau", Tab);
            request.setAttribute("sortL", 1);
            request.setAttribute("sortC", "asc");
            return "listeDossiers.jsp";
    }
    
}
