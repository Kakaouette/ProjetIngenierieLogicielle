/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.dao.CompteDAO;
import modele.dao.DossierDAO;
import modele.entite.Dossier;

/**
 *
 * @author Jordan
 */
public class VoirGestionComptesAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des dossiers");
        List<Dossier> dossiers = new DossierDAO().SelectAll(); //recuperation des comptes pour la page suivante        
        List<Object[]> Tab = new ArrayList<Object[]>();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Dossier c : dossiers) {
            Object[] o = new Object[8];
            o[0] = c.getEtat();
            o[1] = c.getId();
            o[2] = c.getAdmissible();
            o[3] = format.format(c.getDate());
            o[4] = c.getDemandeFormation().getIntitule();
            o[5] = c.getEtudiant().getNom();
            o[6] = c.getEtudiant().getPrenom();
            o[7] = "<a class=\\\"btn btn-info btn-block\\\" href=\\\"Navigation?action=#\\\">Modifier</a>";
            Tab.add(o);
        }

        request.setAttribute("leTableau", Tab);
        request.setAttribute("sortL", 1);
        request.setAttribute("sortC", "asc");
        return "listeDossiers.jsp";
    }
}
