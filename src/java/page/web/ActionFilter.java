/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.web;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Action;
import modele.entite.Compte;
import service.ActionService;

/**
 *  Filtrage via les actions
 *  La table Action contient un identifiant (l'action correspondant à une page)
 *  et une liste de compte autoriser à effectuer cette action
 * 
 *  Le filtre vérifie que l'utilisateur connecté à le droit de voir la page, si non il renvoi vers l'index
 * 
 * @author roulonn
 */

public class ActionFilter implements Filter {
    /**
     * Init du filtre, chargement de la liste des Actions dans le ServletContext.
     * ServletContext garde la variable toute la durée de vie de l'application (ApplicationScope).
     * 
     * @param fc
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig fc) throws ServletException {
        if(fc.getServletContext().getAttribute("action") == null){
            fc.getServletContext().setAttribute("action", new ActionService().SelectAlltoMap());
        }
    }
    
    /**
     * Fait les vérification de l'action à chaque requête.
     * Récupération du compte et de la map contenant toutes les actions.<br/>
     * 
     * Une fois fait une vérification des droit à lieu
     * 
     * @param sr (objet requête http)
     * @param sr1 (objet response http)
     * @param fc (chaîne de filtre, tomcat les applique un par un, l'utiliser signifie passe au filtre suivant)
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;
        
        Map<String, Action> lesActions = (Map<String, Action>) sr.getServletContext().getAttribute("action");       
        String attributeAction = (String) request.getParameter("action");
        Compte compte = (Compte) request.getSession().getAttribute("compte");
                
        Action action = lesActions.get(attributeAction);
        //si le compte n'est pas connecté et demande accès aux index ou rien (donc index), on laisse passer
        if(compte == null && (request.getAttribute("action") == null || request.getAttribute("action") == "index")){
            fc.doFilter(sr, sr1);
        //si l'action est null on demande implicitement l'index, on laisse passer
        }else if(action == null){
            fc.doFilter(sr, sr1);
        //si l'action demandé contient un TypeCompte correspondant au type du compte connecté dans la session on laisse passer
        }else if(action.getPage().getTypeAuthoriser().getValue() <= compte.getType().getValue()){
            fc.doFilter(sr, sr1);
        }else{
        //sinon on redirige vers l'index
            response.sendRedirect(request.getContextPath() + "/Navigation");
        }
    }

    @Override
    public void destroy() {

    }

}
