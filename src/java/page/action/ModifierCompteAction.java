/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.dao.CompteDAO;
import modele.entite.Compte;
import service.CompteService;

/**
 *
 * @author Jordan
 */
public class ModifierCompteAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String valueButton = request.getParameter("bouton");
        
        if(valueButton.equals("enregistrer")){
            String type = request.getParameter("type");
            String login = request.getParameter("login");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mail = request.getParameter("email");
            String mdp = request.getParameter("motDePasse"); 
            
            Compte compte = new CompteDAO().getComptebyLogin(login);
            int idCompte = compte.getId();
            
            if(type==null){
                type=compte.getType().toString();
            }
            if(nom==null){
                nom=compte.getNom();
            }
            if(prenom==null){
                prenom=compte.getPrenom();
            }
            if(mail==null){
                mail=compte.getMail();
            }
            if(mdp==null){
                mdp=compte.getMdp();
            }
                        
            Boolean update = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);
            if (update == false) {
                request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
                return "modifierCompte.jsp";
            } else {
                request.setAttribute("message", "Modification effectuée");
                
                List<Compte> comptes = new CompteDAO().SelectAll();
                request.setAttribute("comptes", comptes);
                
                request.setAttribute("compte", compte);
                
                return "gestionComptes.jsp";
            }
        }else{
            request.setAttribute("message", "Vous n'avez pas appuyé sur enregistrer");
            return "modifierCompte.jsp";
        }
    }
    
}
