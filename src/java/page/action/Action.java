/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package page.action;

/**
 *
 * @author roulonn
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public String execute(HttpServletRequest request,
			HttpServletResponse response);
	
}
