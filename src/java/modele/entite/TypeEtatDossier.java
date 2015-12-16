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
public enum TypeEtatDossier implements Serializable {
    en_attente_commission, en_attente_transfert_vers_directeur, en_transfert_vers_directeur, navette, navette_directeur, retour_vers_secretariat, terminé, traité_secretariat_formation, transfert_vers_secretariat;

    @Override
    public String toString() {
        switch(this){
            case en_attente_transfert_vers_directeur:
                return "En attente de transfert vers le directeur";
            case transfert_vers_secretariat:
                return "Transfert vers le secrétariat";
            case traité_secretariat_formation:
                return "Traité par le secrétariat";
            case en_attente_commission:
                return "Attente de la commission";
            case en_transfert_vers_directeur:
                return "Transfert vers le directeur";
            case retour_vers_secretariat:
                return "Retour vers le secrétariat";
            case navette:
                return "Navette";
            case navette_directeur:
                return "Navette";
            case terminé :
                return "Terminé";
            default:
                return this.name();
        }
    }
}
