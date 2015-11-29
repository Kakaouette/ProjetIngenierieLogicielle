/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.FormationDAO;
import modele.dao.JustificatifDAO;
import modele.entite.Formation;
import modele.entite.Justificatif;

/**
 *
 * @author Arthur
 */
public class VoirModifFormationAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Modifer une formation");
        
        //recuperation de l'id
        String idForm = request.getParameter("id");
        
        //verification de la validité du parametre
        String[] required = {idForm};
        String[] requiredToString = {"idForm"};
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i].isEmpty()){
                empty.add(requiredToString[i]);
            }
        }
        if(!empty.isEmpty()){
            try {
                String champs = "";
                for(String champ : empty){
                    if(!champs.equals("")){champs+=", ";}
                    champs += champ;
                }
                if(empty.size()==1){ throw new Exception("Un champ requis est vide. (" + champs + ")");
                }else{ throw new Exception("Des champs requis sont vides. (" + champs + ")"); }
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationsAction().execute(request, response); //redirection
            }
        }
        int id = Integer.parseInt(idForm);
        Formation formation = new FormationDAO().getById(id);
        if(formation == null){
            try {
                throw new Exception("Formation inexistante.");
            } catch (Exception ex) {
                request.setAttribute("typeMessage", "danger");
                request.setAttribute("message", ex.getMessage());
                return new VoirGestionFormationsAction().execute(request, response); //redirection
            }
        }
        
        //recuperation des données
        String intitule = formation.getIntitule();
        String description = formation.getDescription();
        int nbPlace = formation.getNombrePlace();
        Date debut = formation.getDebut();
        Date fin = formation.getFin();
        List<Justificatif> justificatifs = formation.getLesJustificatifs();
        //recuperation des données complémentaires
        List<Justificatif> tousJustificatifs = new JustificatifDAO().SelectAll();
        request.setAttribute("tousJustificatifs", tousJustificatifs);
                
        //remplissage du formulaire
        if(request.getAttribute("id") == null){
            request.setAttribute("id", idForm);
        }
        if(request.getAttribute("intitule") == null){
            if(intitule != null){
                request.setAttribute("intitule", intitule);
            }
        }
        if(request.getAttribute("description") == null){
            if(description != null){
                request.setAttribute("description", description);
            }
        }
        if(request.getAttribute("nbPlace") == null){
            request.setAttribute("nbPlace", nbPlace);
        }
        if(request.getAttribute("dateDebut") == null){
            if(debut != null){
                request.setAttribute("dateDebut", debut);
            }
        }
        if(request.getAttribute("dateFin") == null){
            if(fin != null){
                request.setAttribute("dateFin", fin);
            }
        }
        if(request.getAttribute("justificatifs") == null){
            if(justificatifs == null){
                justificatifs = new ArrayList<Justificatif>();
            }
            String[] justificatifsToString = new String[0];
            for(Justificatif jTemp : justificatifs){
                //add slot
                if (justificatifsToString==null)
                    justificatifsToString = new String[1];
                else {
                    String[] tabTemp = new String [justificatifsToString.length+1];
                    for( int i = 0 ;i<justificatifsToString.length ; i++){ //load old values
                         tabTemp[i] = justificatifsToString[i];
                    }
                    justificatifsToString=tabTemp;
                }
                
                justificatifsToString[justificatifsToString.length-1] = jTemp.getTitre();
            }
            request.setAttribute("justificatifs", justificatifsToString);
        }
        
        return "modifFormation.jsp";
    }
    
    public void addSlot(String[] tab){
        if (tab==null)
            tab = new String[1];
        else {
            int position = tab.length;
            String[] tabTemp = new String [position+1];
            for( int i = 0 ;i<tab.length ; i++){
                 tabTemp[i] = tab[i];
            }
            tab=tabTemp;
        }
    }
}
