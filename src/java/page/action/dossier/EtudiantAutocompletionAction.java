/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.EtudiantDAO;
import modele.entite.Etudiant;
import page.action.Action;

/**
 *
 * @author Etienne
 */
public class EtudiantAutocompletionAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String ine = request.getParameter("numeroINE");
        System.out.println("ine:"+ine);
        Etudiant etu = new EtudiantDAO().getEtudiantByINE(ine);
        System.out.println("EtudiantAutocompletionAction.etudiant:"+etu);
        System.out.println("EtudiantAutocompletionAction.etu.ville:"+etu.getNom());
        request.setAttribute("etudiant", etu);
        /*
        String ret = "{nom:'"+etu.getNom().replace("'", "\\'")+"',prenom:'"+etu.getPrenom().replace("'", "\\'")
                +"',adressePostale:'"+etu.getAdressePostale().replace("'", "\\'")+"',sexe:'"+etu.getSexe()
                +"',codePostal:'"+etu.getAdresse().getCodePostal()+"',ville:'"+etu.getAdresse().getVille().replace("'", "\\'")+"'}";*/
        return "infoEtudiant.jsp";
    }
    
}