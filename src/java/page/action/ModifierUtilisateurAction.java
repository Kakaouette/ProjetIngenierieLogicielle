/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;
import service.CompteService;

/**
 *
 * @author Jordan
 */
public class ModifierUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String session = request.getParameter("session");
        if ("deco".equals(session)) {
            request.getSession().invalidate();
            return "index.jsp";
        }       
        
        String valueButton = request.getParameter("bouton");
        System.out.println(valueButton);
        
        if(valueButton.equals("annuler"))
        {
            return "listeUtilisateurs.jsp";
        }
        else if(valueButton.equals("supprimer"))
        {
            //A faire
            return "listeUtilisateurs.jsp";
        }
        else if(valueButton.equals("enregistrer"))
        {
            System.out.println("test");
            String type = request.getParameter("type");
            String login = request.getParameter("login");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mail = request.getParameter("email");
            String mdp = request.getParameter("motDePasse"); 
            
            Compte compte=(Compte)(request.getSession().getAttribute("compte"));
            System.out.println(compte);
            int idCompte = compte.getId();
            
            if(type==null)
            {
                type=compte.getType().toString();
            }
            if(login==null)
            {
                login=compte.getLogin();
            }
            if(nom==null)
            {
                nom=compte.getNom();
            }
            if(prenom==null)
            {
                prenom=compte.getPrenom();
            }
            if(mail==null)
            {
                mail=compte.getMail();
            }
            if(mdp==null)
            {
                mdp=compte.getMdp();
            }
            
            
            Boolean update = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);

            if (update == false) {
                request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
                System.out.println("error");
                return "modifierUtilisateur.jsp";
            } else {
                request.setAttribute("message", "Modification effectuée");
                return "listeUtilisateurs.jsp";
            }
        }
        else
        {
            request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
            return "modifierUtilisateur.jsp";
        }
    }
    
}
