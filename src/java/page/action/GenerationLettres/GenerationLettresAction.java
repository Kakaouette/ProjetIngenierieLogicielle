/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.GenerationLettres;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.TypeEtatDossier;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import page.action.Action;
import service.DossierService;
import service.GenerationLettres.CreerAccuseReceptionService;
import service.GenerationLettres.CreerLettreAccepteService;
import service.GenerationLettres.CreerLettreAuditionService;
import service.GenerationLettres.CreerLettreRefusService;

/**
 *
 * @author Pierre
 */
public class GenerationLettresAction implements Action
{

    @Override
    /**
     * Reçoit l'id d'un dossier et un integer representant la lettre à generer, puis genere cette dernière avec les informations du dossier
     * @param request Objet contenant des informations envoyé par la page précedente ou permettant d'envoyer des informations à la page suivante
     * @param response 
     * @return Le nom du fichier .jsp à afficher
     */
    public String execute(HttpServletRequest request, HttpServletResponse response)
    {
        String message=null;
        String messageError=null;
        String idDossier = request.getParameter("idDossier");
        try
        {
            int typeLettre = Integer.parseInt(request.getParameter("typeLettre"));
            
            /*
            Valeur typeLettre :
            - 1 : Accuse de reception
            - 2 : lettre d'acception
            - 3 : Lettre de refus
            - 4 : Lettre d'audition (admissibilite)
            */
            Dossier dossier;
            switch(typeLettre)
            {
                case 1:
                    new CreerAccuseReceptionService().replaceAccuseReception("Accuse_Reception.docx", idDossier);
                    message = "Accuse de reception genéré";
                    dossier = new DossierDAO().getById(idDossier);
                    dossier.setEtat(TypeEtatDossier.traité_secretariat_formation);
                    new DossierService().modifierDossier(dossier);
                    break;
                case 2:
                    new CreerLettreAccepteService().replaceLettreAccepte("Ltrre accordmaster _PST.docx", idDossier);
                    message = "Lettre d'acceptation genérée";
                    dossier = new DossierDAO().getById(idDossier);
                    dossier.setEtat(TypeEtatDossier.en_transfert_vers_directeur);
                    new DossierService().modifierDossier(dossier);
                    break;
                case 3:
                    new CreerLettreRefusService().replaceLettreRefus("Ltrre refus_PST.docx", idDossier);
                    message = "Lettre de refus genérée";
                    dossier = new DossierDAO().getById(idDossier);
                    dossier.setEtat(TypeEtatDossier.en_transfert_vers_directeur);
                    new DossierService().modifierDossier(dossier);
                    break;
                case 4:
                    new CreerLettreAuditionService().replaceLettreAudition("Ltrre AUDITIONS_PST.docx", idDossier);
                    message = "Lettre d'admission genérée";
                    dossier = new DossierDAO().getById(idDossier);
                    dossier.setEtat(TypeEtatDossier.terminé);
                    new DossierService().modifierDossier(dossier);
                    break;
                default:
                    messageError = "Erreur lors de la generation de la lettre";
            }
        }
        catch (InvalidFormatException | IOException ex)
        {
            Logger.getLogger(GenerationLettresAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("message",message);
        request.setAttribute("messageError",messageError);
        request.setAttribute("dossier",new DossierDAO().getById(idDossier));
        request.setAttribute("titre","Modifier un dossier");
        return "consulteDossier.jsp";
    }
}
