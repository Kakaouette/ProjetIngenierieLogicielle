/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.CompteDAO;
import modele.entite.Compte;
import modele.entite.Formation;
import modele.entite.TypeCompte;

/**
 *
 * @author nicol
 */
public class CompteService {

    CompteDAO compteDAO;

    public CompteService() {
        this.compteDAO = new CompteDAO();
    }

    /**
     * Crypte la chaine de caractère en SHA-256, le but est d'obtenir un mot de
     * passe crypté pour comparaison avec la base
     *
     * @param mdp
     * @return
     */
    public String cryptageMDP(String mdp) {
        if (mdp == null) {
            return null;
        }
        try {
            byte[] bytesOfMessage = mdp.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(mdp.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CompteService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CompteService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Vérifie que les identifiant passé en paramètres sont correctes
     *
     * @param identifiant
     * @param mdp
     * @return Compte si identifiant correcte
     */
    public Compte verifierAuthentification(String identifiant, String mdp) {
        //récupération du mot de passe crypté
        String mdpCrypt = cryptageMDP(mdp);
        //vérification de l'existance d'un compte avec le coup identifiant + mdp crypté
        Compte compte = compteDAO.getComptebyIdentifiant(identifiant, mdpCrypt);

        //si existe return le compte sinon on return null
        if (compte != null) {
            if (compte.getLogin().equals(identifiant)) {
                return compte;
            }
        }
        return null;
    }

    /**
     * Effectue la modification d'un utilisateur
     *
     * @param idCompte
     * @param type
     * @param login
     * @param nom
     * @param prenom
     * @param mail
     * @param mdp
     * @return boolean indiquant si l'update c'est effectué correctement
     */
    public Boolean effectuerModification(int idCompte, String type, String login, String nom, String prenom, String mail, String mdp, List<Formation> lesFormations) {
        // récupération du compte à modifier
        Compte compte = compteDAO.getById(idCompte);
        // si le compte est trouvé dans la BDD, on lui affecte ses nouvelles valeurs
        if (compte != null) {
            // Type du compte
            if (!"".equals(type)) {
                compte.setType(TypeCompte.valueOf(type));
            }
            // Login du compte
            if (!"".equals(login)) {
                compte.setLogin(login);
            }
            // Nom du compte
            if (!"".equals(nom)) {
                compte.setNom(nom);
            }
            // Prenom du compte
            if (!"".equals(prenom)) {
                compte.setPrenom(prenom);
            }
            // Mail du compte
            if (!"".equals(mail)) {
                compte.setMail(mail);
            }
            // Mot de passe du compte
            if (!"".equals(mdp)) {
                String mdpCrypt = cryptageMDP(mdp); // Cryptage du nouveau mot de passe
                compte.setMdp(mdpCrypt);
            }
            
            //Formations du compte
            if(!compte.getType().equals(TypeCompte.responsable_commission)&&!compte.getType().equals(TypeCompte.secrétaire_formation)&&!compte.getType().equals(TypeCompte.responsable_formation)){
                if(lesFormations != null)
                    lesFormations.clear();
            }
            compte.setFormationAssocie(lesFormations);

            compteDAO.update(compte);
            return true;
        } else {
            return false;
        }
    }

    /*
     * Ajoute un utilisateur dans la Base de données
     *
     * @param nouveauCompte
     * @return rien
     */
    public void ajouterUtilisateur(Compte nouveauCompte) {
        //récupération du mot de passe
        String mdp = nouveauCompte.getMdp();

        //Cryptage du mot de passe
        String mdpCryptee = cryptageMDP(mdp);

        //ajout dans la base de données avec le mot de passe cryptee
        nouveauCompte.setMdp(mdpCryptee);
        compteDAO.save(nouveauCompte);
    }
    
    public boolean supprimerUtilisateur(int id){
        Compte compte = compteDAO.getById(id);
        if (compte != null){
            compteDAO.delete(compte.getId());
            return true;
        }else{
            return false;
        }
    }
}
