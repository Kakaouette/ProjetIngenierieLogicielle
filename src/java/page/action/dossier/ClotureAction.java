/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.TypeAvisDossier;
import modele.entite.TypeEtatDossier;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import page.action.Action;
import service.GenerationLettres.CreerLettreAccepteService;
import service.GenerationLettres.CreerLettreRefusService;
import service.GenerationLettres.DownloadLettresService;

/**
 *
 * @author Jordan
 */
public class ClotureAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Dossier d = new DossierDAO().getById(request.getParameter("id"));
        d.setEtat(TypeEtatDossier.terminé);

        if (d.getAvisCommission() != d.getAvisDirecteur()) {
            if (d.getAvisDirecteur() == TypeAvisDossier.favorable) {
                try {
                    new CreerLettreAccepteService().replaceLettreAccepte("Ltrre accordmaster _PST.docx", d.getId());
                    new DownloadLettresService().downloadFile(d.getId(), 2, request, response);
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ClotureAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClotureAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (d.getAvisDirecteur() == TypeAvisDossier.défavorable) {
                try {
                    new CreerLettreRefusService().replaceLettreRefus("Ltrre refus_PST.docx", d.getId());
                    new DownloadLettresService().downloadFile(d.getId(), 3, request, response);
                } catch (InvalidFormatException ex) {
                    Logger.getLogger(ClotureAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClotureAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        new DossierDAO().update(d);
        return new AfficherInformationsDossiersAction()
                .execute(request, response);
    }
}
