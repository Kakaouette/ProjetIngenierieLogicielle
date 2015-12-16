/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * NavigationFiltre est un filtre qui vérifie que les requête n'appelle pas une 
 * JSP directement (c'est à dire sans passer par le gestionnaire de servlet : Navigation)
 * 
 * Si il y a un appel à une jsp, le filtre redirige la requête vers Navigation (sans action, ce qui conduit à la page d'index ou d'accueil)
 * 
 * @author nicol
 */

public class NavigationFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    /**
     * Vérification que le contexte de la requête ne contient pas un ".jsp".
     * <ul>
     * <li>Si il y a un ".jsp" la requête est redirigé vers /Navigation sans action, ce qui conduit à la page d'index ou d'accueil)</li>
     * <li>Sinon la requête continue normalement et le filtre ne touche à rien</li>
     * </ul>
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
        String path = request.getServletPath();

        if (path.startsWith("/Navigation") && request.getSession().getAttribute("compte") != null) {
            fc.doFilter(sr, sr1);
        }else if(request.getServletPath().equals("/Navigation")&& request.getSession().getAttribute("compte") == null){
            fc.doFilter(sr, sr1);
        }else if(path.startsWith("/bootstrap/")){
            fc.doFilter(sr, sr1);
        }else if(path.startsWith("/images/")){
            fc.doFilter(sr, sr1);
        }else if(path.startsWith("/jQuery/")){
            fc.doFilter(sr, sr1);
        }else if(path.startsWith("/Etudiant")){
            fc.doFilter(sr, sr1);
        }else{
            response.sendRedirect(request.getContextPath() + "/Navigation");
        }
        
    }

    @Override
    public void destroy() {

    }

}
