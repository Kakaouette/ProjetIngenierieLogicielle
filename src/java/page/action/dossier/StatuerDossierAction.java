/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Historique;
import modele.entite.TypeEtatDossier;
import page.action.Action;
import service.DossierService;

/**
 *
 * Statuer un dossier (avis favorable prononcé par le directeur du Pôle)
 * 
 * @author totodunet
 */
public class StatuerDossierAction implements Action{
  
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        Compte c=(Compte)request.getSession().getAttribute("compte");
            request.setAttribute("titre","Gestion des dossiers");
            DossierService service=new DossierService();
            Dossier dossier_statuer=service.recupererDossier(request.getParameter("id_dossier"));
            dossier_statuer.setEtat(TypeEtatDossier.retour_vers_secretariat);
            Historique histo=new Historique();
            histo.setDate(new Date());
            histo.setAction("Dossier statué");
            histo.setMessage(request.getParameter("avis_dossier"));
            List<Historique> list_histos=dossier_statuer.getHistorique();
            list_histos.add(histo);
            dossier_statuer.setHistorique(list_histos);
            service.modifierDossier(dossier_statuer);
        return "listeDossiers.jsp";
    }
}
