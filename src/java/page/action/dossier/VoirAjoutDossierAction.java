/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.dossier;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.entite.Compte;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Justificatif;
import modele.entite.TypeCompte;
import modele.entite.TypeDossier;
import modele.entite.TypeJustificatifEtranger;
import page.action.Action;

/**
 *
 * @author Sahare
 */
public class VoirAjoutDossierAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Ajouter un dossier");
        
        //recuperation des formations pour la page suivante celon le type de compte
        Compte compte = (Compte) request.getSession().getAttribute("compte");
        List<Formation> formations = null;
        if(compte.getType() == TypeCompte.admin || compte.getType() == TypeCompte.responsable_administrative || compte.getType() == TypeCompte.directeur_pole){
            formations = new FormationDAO().SelectAll();
        }else if(compte.getType() == TypeCompte.secrétaire_formation|| compte.getType() == TypeCompte.secrétaire_inscription){
            formations = compte.getFormationAssocie();
        }
        /*if(formations == null){
            formations = new ArrayList<Formation>();
            request.setAttribute("message", "Aucune formation trouvé dans la BDD");
        }*/
        request.setAttribute("formations", formations);
        
        // construction de la liste des INE pour 
        List<String> lesINE = new EtudiantDAO().getAllINE();
        request.setAttribute("lesINE",lesINE);
        List<Etudiant> lesEtudiants = new EtudiantDAO().selectAll();
        request.setAttribute("listeEtudiant",lesEtudiants);
        
        
        ///autofill form///
        //formation
        String intitule = request.getParameter("formationIntitule");
        if(intitule == null && !formations.isEmpty()){
            intitule = formations.get(0).getIntitule();
        }
        request.setAttribute("formationIntitule", intitule);
        //type
        String type = request.getParameter("type");
        if(type == null){
            type = TypeDossier.inscription.toString();
        }
        request.setAttribute("type", type);
        //nationalité et pays
        String nationalite = request.getParameter("nationalite");
        String pays = request.getParameter("pays");
        if(nationalite == null){
            nationalite = TypeJustificatifEtranger.francais.toString();
            if(pays == null){
                pays = "FRANCE";
            }else if(request.getParameter("pays").equals("")){
                pays = "FRANCE";
            }
        }else if(nationalite.equals(TypeJustificatifEtranger.francais.toString())){
            if(pays == null){
                pays = "FRANCE";
            }else if(request.getParameter("pays").equals("")){
                pays = "FRANCE";
            }
        }
        request.setAttribute("nationalite", nationalite);
        request.setAttribute("pays", pays);
        //justificatifs
        Formation formation = new FormationDAO().getFormationByIntitule(intitule);
        TypeDossier typeJ = null;
        if(type.equals(TypeDossier.inscription.toString())){
            typeJ = TypeDossier.inscription;
        }else if(type.equals(TypeDossier.admissibilite.toString())){
            typeJ = TypeDossier.admissibilite;
        }
        TypeJustificatifEtranger nationaliteJ = null;
        if(nationalite.equals(TypeJustificatifEtranger.francais.toString())){
            nationaliteJ = TypeJustificatifEtranger.francais;
        }else if(nationalite.equals(TypeJustificatifEtranger.etranger.toString())){
            nationaliteJ = TypeJustificatifEtranger.etranger;
        }
        if(typeJ != null && nationaliteJ != null){
            List<Justificatif> justificatifs = formation.getLesJustificatifs(typeJ, nationaliteJ);
            /*if(justificatifs.isEmpty()) {
                request.setAttribute("message", "Aucun justificatif trouvé dans la BDD");
            }*/
            request.setAttribute("justificatifs", justificatifs);
        }
        
        if(request.getParameter("reload") != null){
            if(request.getParameter("reload").equals("true")){
                keepForm(request, response);
            }
        }
        
        if(request.getAttribute("freeForm") != null){
            if((boolean)request.getAttribute("freeForm") == true){
                freeForm(request, response);
            }
        }
        
        return "ajoutDossier.jsp";
    }
    
    private void keepForm(HttpServletRequest request, HttpServletResponse response){
        //keep formulaire
        request.setAttribute("idDossier", request.getParameter("idDossier"));
        request.setAttribute("ine", request.getParameter("ine"));
        request.setAttribute("nom", request.getParameter("nom"));
        request.setAttribute("prenom", request.getParameter("prenom"));
        request.setAttribute("sexe", request.getParameter("sexe"));
        request.setAttribute("adresse", request.getParameter("adresse"));
        request.setAttribute("codePostal", request.getParameter("codePostal"));
        request.setAttribute("ville", request.getParameter("ville"));
        request.setAttribute("notes", request.getParameter("notes"));
        if(request.getParameter("nationalite") != null){
            if(request.getParameter("nationalite").equals(TypeJustificatifEtranger.etranger.toString())){
                request.setAttribute("avis", request.getParameter("avis"));
                request.setAttribute("niveau", request.getParameter("niveau"));
            }
        }else{
            request.setAttribute("nationalite", TypeJustificatifEtranger.francais.toString());
        }
    }
    private void freeForm(HttpServletRequest request, HttpServletResponse response){
        //free formulaire
        request.setAttribute("type", null);
        request.setAttribute("formationIntitule", null);
        request.setAttribute("nationalite", null);
        request.setAttribute("idDossier", null);
        request.setAttribute("ine", null);
        request.setAttribute("nom", null);
        request.setAttribute("prenom", null);
        request.setAttribute("sexe", null);
        request.setAttribute("pays", null);
        request.setAttribute("adresse", null);
        request.setAttribute("codePostal", null);
        request.setAttribute("ville", null);
        request.setAttribute("notes", null);
        if(request.getParameter("nationalite") != null){
            if(request.getParameter("nationalite").equals(TypeJustificatifEtranger.etranger.toString())){
                request.setAttribute("avis", null);
                request.setAttribute("niveau", null);
            }
        }
    }
}
