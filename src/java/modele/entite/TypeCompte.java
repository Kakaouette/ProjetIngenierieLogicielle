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
    admin(30), directeur_pole(25), responsable_administrative(20), responsable_formation(15), responsable_commission(10), secrétaire_formation(5), secrétaire_inscription(1);
    
    private final int value;
    private TypeCompte(int value) {
        this.value = value;
    }
    
     public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        if(this.equals(admin)){
            return "Admin";
        }else if(this.equals(directeur_pole)){
            return "Directeur de pôle";
        }else if(this.equals(responsable_administrative)){
            return "Responsable Administratif";
        }else if(this.equals(responsable_formation)){
            return "Responsable de formation";
        }else if(this.equals(responsable_commission)){
            return "Responsable de commission";
        }else if(this.equals(secrétaire_formation)){
            return "Secrétaire de formation";
        }else if(this.equals(secrétaire_inscription)){
            return "Secrétaire d'inscription";
        }
        
        return this.name();
    }
}
