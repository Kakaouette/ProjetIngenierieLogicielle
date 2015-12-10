/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Dossier;
import page.action.Action;
import service.DossierService;

/**
 *
 * @author totodunet
 */
public class SupprimerDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //recuperation du dossier original
        String idDossier = request.getParameter("idDossier");
        Dossier dossierorigin=  new DossierService().recupererDossier(idDossier);
        request.setAttribute("msgSuppression", true);
        if(new DossierService().supprimerDossier(idDossier)){
            request.setAttribute("message", "Le dossier "+idDossier+" a bien été supprimé !");
        }
        else{
            request.setAttribute("message", "Erreur dans la suppression du dossier "+idDossier);
        }
        return new AfficherInformationsDossiersAction().execute(request,response);
    }

}