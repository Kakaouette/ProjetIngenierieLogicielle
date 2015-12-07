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
import modele.dao.FormationDAO;
import modele.entite.Formation;

/**
 *
 * @author Sahare
 */
public class VoirAjoutDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter un dossier");
        
        //recuperation du formulaire
        String formationIntitule = request.getParameter("formationIntitule");
        String type = request.getParameter("type");
        String nationalite = request.getParameter("nationalite");
        if(nationalite == null){
            nationalite = "francais";
        }
        //verification de la validité du formulaire
        String[] required = {formationIntitule, type, nationalite};
        String[] requiredName = {"intitulé de la formation", "type", "nationalité"};
        try {
            validerFormulaire(required, requiredName);
        } catch (Exception ex) {
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", ex.getMessage());
            return stayHere(request, response);
        }
        
        //mise en forme des données
        Formation formation = new FormationDAO().getFormationByIntitule(formationIntitule);
        if(formation == null){
            request.setAttribute("typeMessage", "danger");
            request.setAttribute("message", "Formation inconnue");
            request.setAttribute("focus", "formationIntitule");
            return stayHere(request, response); //redirection
        }
        
        request.setAttribute("formationIntitule", formationIntitule);
        request.setAttribute("type", type);
        request.setAttribute("nationalite", nationalite);
        return "ajoutDossier.jsp";
    }
    
    private String stayHere(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("formationIntitule", request.getParameter("formationIntitule"));
        request.setAttribute("type", request.getParameter("type"));
        request.setAttribute("nationalite", request.getParameter("nationalite"));
        return new VoirValidationJustificatifsDossierAction().execute(request, response); //modif: voir récupérer page precedente
    }
    
    private void validerFormulaire(String[] required, String[] requiredName) throws Exception{
        
        //verification de la validité du formulaire
        List<String> empty = new ArrayList<String>();
        for(int i=0; i<required.length; i++){
            if(required[i] == null){
                empty.add(requiredName[i]);
            }
        }
        if(!empty.isEmpty()){
            String champs = "";
            for(String champ : empty){
                if(!champs.equals("")){champs+=", ";}
                champs += champ;
            }
            if(empty.size()==1){
                throw new Exception("Un champ requis est vide. (" + champs + ")");
            }else{
                throw new Exception("Des champs requis sont vides. (" + champs + ")");
            }
        }
    }
}
