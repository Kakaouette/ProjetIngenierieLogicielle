/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele.entite;

import java.io.Serializable;

/**
 * <p>
 * Enum conservant les différent type de compte :
 * <ul>
 *      <li>secretaire_general</li>
 *      <li>secretaire_formation</li>
 *      <li>directeur_pole</li>
 *      <li>commission</li>
 * </ul>
 * </p>
 *
 * @author Nicolas Roulon
 */
public enum TypeAvisDossier implements Serializable {
    favorable, défavorable, en_attente;
    
    @Override
    public String toString() {
        if(this.equals(favorable)){
            return "Favorable";
        }else if(this.equals(défavorable)){
            return "Défavorable";
        }else if(this.equals(en_attente)){
            return "En attente";
        }
        
        return this.name();
    }
}
