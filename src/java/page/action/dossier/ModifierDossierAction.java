package page.action.dossier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Gestion des dossiers - Modification d'un dossier
 * Si echec modification, retour vers la page du dossier
 *
 * @author totodunet
 */
public class ModifierDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        //variable permettant de savoir si l'etat a été modifié
        boolean etatChange = false;
        
        //recuperation du dossier original
        String idDossier = request.getParameter("idDossier");
        Dossier dossierorigin=  new DossierService().recupererDossier(idDossier);
        
        //recuperation des infos et modif de l'objet Dossier
        Compte compte = (Compte)request.getSession().getAttribute("compte");
        String etatDossier = request.getParameter("etat");
        if (!etatDossier.equals(dossierorigin.getEtat().name())){
            etatChange = true;
        }
        dossierorigin.setEtat(TypeEtatDossier.valueOf(etatDossier));
        String lettreDossier=request.getParameter("lettre");
        dossierorigin.setLettre(lettreDossier);
        //Historique
        String messageHisto = request.getParameter("msg_histo");
        String actionHisto;
        if(etatChange){
            actionHisto = "Changement d'état du dossier pour " + dossierorigin.getEtat().toString() + ".";
        }else{
            actionHisto = "";
        }
        
        //On ajoute une note seulement si celle-ci n'est pas vide ou bien si il y a eu un changement d'état
        if(!request.getParameter("msg_histo").isEmpty() || etatChange){
            Date dateHisto = new Date();
            Historique histo = new Historique();
            histo.setCompte(compte);
            histo.setMessage(messageHisto);
            histo.setAction(actionHisto);
            histo.setDate(dateHisto);
            //ajout du nouvel historique dans le dossier
            List<Historique> histodossier=dossierorigin.getHistorique();
            histodossier.add(histo);
            dossierorigin.setHistorique(histodossier);
        }
        
        try{
            new DossierService().modifierDossier(dossierorigin);
            request.setAttribute("idDossier", idDossier);
            request.setAttribute("message", "Le dossier a été modifié avec succès !");
        }catch(Exception e){
            request.setAttribute("idDossier", idDossier);
            request.setAttribute("error", "true");
            request.setAttribute("messageError", "Le dossier n'a pas été modifié !");
        }
        
        return new ConsulterDossierAction().execute(request, response);
        
    }
    
}
