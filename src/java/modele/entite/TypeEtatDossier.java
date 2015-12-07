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
    creer, transfert_vers_secretariat, traitement_secretariat_formation, en_attente_commission, en_transfert_vers_directeur, retour_vers_secretariat, navette, terminee;

    @Override
    public String toString() {
        switch(this){
            case creer:
                return "Cree";
            case transfert_vers_secretariat:
                return "Transfert vers le secrétariat";
            case traitement_secretariat_formation:
                return "Traitement par le secréatariat";
            case en_attente_commission:
                return "Attente de la commission";
            case en_transfert_vers_directeur:
                return "Transfert vers le directeur";
            case retour_vers_secretariat:
                return "Retour vers le secrétariat";
            case navette:
                return "Navette";
            case terminee :
                return "Terminée";
            default:
                return this.name();
        }
    }
}
