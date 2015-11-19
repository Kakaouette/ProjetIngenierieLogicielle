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
public class ModifierUtilisateurAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String valueButton = request.getParameter("bouton");
        System.out.println(valueButton);
        
        if(valueButton.equals("enregistrer"))
        {
            //System.out.println("test");
            String type = request.getParameter("type");
            String login = request.getParameter("login");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mail = request.getParameter("email");
            String mdp = request.getParameter("motDePasse"); 
            
            Compte compte=new CompteDAO().getComptebyLogin(login);
            //System.out.println(compte);
            int idCompte = compte.getId();
            //System.out.println(idCompte);
            
            if(type==null)
            {
                type=compte.getType().toString();
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
            
            /*System.out.println(type);
            System.out.println(login);
            System.out.println(nom);
            System.out.println(prenom);
            System.out.println(mail);
            System.out.println(mdp);*/
            
            Boolean update = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);
            if (update == false) {
                request.setAttribute("message", "ERREUR : Modification non effectuée, une erreur est présente dans le formulaire");
                return "modifierUtilisateur.jsp";
            } else {
                request.setAttribute("message", "Modification effectuée");
                
                List<Compte> comptes;

                comptes = new CompteDAO().SelectAll();
                
                request.getSession().setAttribute("comptes", comptes);
                request.getSession().setAttribute("compte", compte);
                
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
