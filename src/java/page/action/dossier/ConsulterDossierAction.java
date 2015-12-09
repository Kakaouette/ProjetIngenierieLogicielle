package page.action.dossier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import page.action.Action;
import service.DossierService;

/**
 * Gestion des dossiers - Consultation en detail un dossier
 * Si dossier non trouvé, retour vers la page des dossiers
 *
 * @author totodunet
 */
public class ConsulterDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        
        //recuperation de l'id du formulaire
        String idDossier = request.getParameter("idDossier");
        
        //recuperation du dossier
        Dossier dossier = new DossierService().recupererDossier(idDossier);
     
        //si dossier n'existe pas => retour vers la liste des dossiers 
        if(dossier != null){
            request.setAttribute("titre", "Modifier un dossier");
            request.setAttribute("dossier", dossier);
            return "consulteDossier.jsp";
        }else{   
            return new AfficherInformationsDossiersAction().execute(request, response);
        }
    }
    
}
