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
import modele.entite.Compte;
import modele.entite.Formation;
import modele.entite.Justificatif;
import modele.entite.TypeCompte;
import modele.entite.TypeDossier;
import modele.entite.TypeJustificatifEtranger;

/**
 *
 * @author Sahare
 */
public class VoirValidationJustificatifsDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        request.setAttribute("titre", "Ajouter un dossier - Validation des justificatifs");
        
        List<Formation> formations = null; 
        //recuperation des formations pour la page suivante celon le type de compte
        if(compte.getType() == TypeCompte.admin || compte.getType() == TypeCompte.secretaire_general){
            formations = new FormationDAO().SelectAll();
        }else if(compte.getType() == TypeCompte.secretaire_formation){
            formations = compte.getFormationAssocie();
        }
        if(formations == null){
            formations = new ArrayList<Formation>();
            request.setAttribute("message", "Aucune formation trouvé dans la BDD");
        }
        request.setAttribute("formations", formations);
        
        String intitule = request.getParameter("formationIntitule");
        //keep formulaire
        if(request.getParameter("formationIntitule") != null){
            intitule = request.getParameter("formationIntitule");
        }else{
            intitule = formations.get(0).getIntitule();
        }
        request.setAttribute("formationIntitule", intitule);
        String type = request.getParameter("type");
        //keep formulaire
        if(request.getParameter("type") != null){
            type = request.getParameter("type");
        }else{
            type = "inscription";
        }
        request.setAttribute("type", type);
        String nationalite = request.getParameter("nationalite");
        if(request.getParameter("nationalite") != null){
            nationalite = request.getParameter("nationalite");
        }else{
            nationalite = "francais";
        }
        request.setAttribute("nationalite", nationalite);
        
        Formation formation = new FormationDAO().getFormationByIntitule(intitule);
        List<Justificatif> justificatifs = formation.getLesJustificatifs();
        if(justificatifs == null) {
            justificatifs = new ArrayList<Justificatif>();
            request.setAttribute("message", "Aucun justificatif trouvé dans la BDD");
        }else{
            List<Justificatif> goodJustificatifs = new ArrayList<Justificatif>();
            for(Justificatif jtemp:justificatifs){
                boolean condition = false;
                if(type.equals("inscription")){
                    condition = (jtemp.getTypeAdmissible() == TypeDossier.inscription);
                }else if(type.equals("admission")){
                    condition = (jtemp.getTypeAdmissible() == TypeDossier.admissibilite);
                }
                if(nationalite.equals("etranger")){
                    condition = condition || (jtemp.getTypeNationalite() == TypeJustificatifEtranger.etranger);
                }
                if(condition){
                    goodJustificatifs.add(jtemp);
                }
            }
            request.setAttribute("justificatifs", goodJustificatifs);
        }
        return "validationJustificatifsDossier.jsp";
    }
    
}
