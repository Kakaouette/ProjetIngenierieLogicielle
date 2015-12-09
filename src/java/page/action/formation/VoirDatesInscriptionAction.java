/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package page.action.formation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import page.action.Action;

/**
 *
 * @author Arthur
 */
public class VoirDatesInscriptionAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("titre", "Gestion des dates d'inscription");
        return "datesInscription.jsp";
    }
    
}