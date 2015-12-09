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
public enum TypeCompte implements Serializable {
    admin, secretaire_general, secretaire_formation, directeur_pole, reponsable_formation;

    @Override
    public String toString() {
        if(this.equals(secretaire_general)){
            return "Secrétaire générale";
        }else if(this.equals(secretaire_formation)){
            return "Secrétaire de formation";
        }else if(this.equals(directeur_pole)){
            return "Directeur de pôle";
        }else if(this.equals(reponsable_formation)){
            return "Reponsable de formation";
        }else if(this.equals(admin)){
            return "Admin";
        }
        
        return this.name();
    }
}
