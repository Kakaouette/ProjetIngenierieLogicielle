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
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modele.entite.Compte;

/**
 *
 * @author roulonn
 */
@WebFilter("/*")
public class NavigationFiltre implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;

        HttpServletResponse response = (HttpServletResponse) sr1;
        Compte c = (Compte) request.getSession().getAttribute("compte");

        if (request.getServletPath().contains(".jsp")) {
            response.sendRedirect(request.getContextPath() + "/Navigation");
        }

        fc.doFilter(sr, sr1);
    }

    @Override
    public void destroy() {

    }

}
