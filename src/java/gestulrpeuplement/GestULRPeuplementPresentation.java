/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestulrpeuplement;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.*;
import modele.entite.*;

/**
 *
 * @author nicol
 */
public class GestULRPeuplementPresentation {
    public static String cryptageMDP(String mdp) {
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
            Logger.getLogger(GestULRPeuplementPresentation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GestULRPeuplementPresentation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Création des comptes
        Compte c1 = new Compte("admin", cryptageMDP("azerty"), "admin", "admin", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.admin, null);
        Compte c2 = new Compte("directeur_pole", cryptageMDP("azerty"), "directeur_pole", "directeur_pole", "christian.inard@univ-lr.fr", TypeCompte.directeur_pole, null);
        Compte c3 = new Compte("responsable_administrative", cryptageMDP("azerty"), "responsable_administrative", "responsable_administrative", "marie-grace.teixeira@univ-lr.fr", TypeCompte.responsable_administrative, null);
        Compte c4 = new Compte("responsable_formation", cryptageMDP("azerty"), "responsable_formation", "responsable_formation", "bernard.besserer@univ-lr.fr", TypeCompte.responsable_formation, null);
        Compte c5 = new Compte("responsable_commission", cryptageMDP("azerty"), "responsable_commission", "responsable_commission", "arnaud.revel@univ-lr.fr", TypeCompte.responsable_commission, null);
        Compte c6 = new Compte("secrétaire", cryptageMDP("azerty"), "secrétaire", "secrétaire", "echaigne@univ-lr.fr", TypeCompte.secrétaire_inscription, null);
        Compte c7 = new Compte("secrétaire_formation", cryptageMDP("azerty"), "secrétaire_formation", "secrétaire_formation", "echaigne@univ-lr.fr", TypeCompte.secrétaire_formation, null);
        
                
        new CompteDAO().save(c1);
        new CompteDAO().save(c2);
        new CompteDAO().save(c3);
        new CompteDAO().save(c4);
        new CompteDAO().save(c5);
        new CompteDAO().save(c6);
        new CompteDAO().save(c7);
        
        //Utilisation des comptes
        Compte compte1 = new CompteDAO().getComptebyIdentifiant("admin");
        Compte compte2 = new CompteDAO().getComptebyIdentifiant("secretaire_formation");
        Compte compte3 = new CompteDAO().getComptebyIdentifiant("directeur_pole");

        
        Historique h1 = new Historique(new Date(), "Modification par l'administrateur", "Modification de lettre", compte1);
        Historique h2 = new Historique(new Date(), "Modification par la secretaire", "Changement d'était : créer -> en cours de traitement", compte2);
        Historique h3 = new Historique(new Date(), "Autre", "Autre", compte2);
        
        new HistoriqueDAO().save(h1);
        new HistoriqueDAO().save(h2);
        new HistoriqueDAO().save(h3);
        
        Historique hist1 = new HistoriqueDAO().getHistoriqueByMessage("Modification par l'administrateur");
        Historique hist2 = new HistoriqueDAO().getHistoriqueByMessage("Modification par la secretaire");
        Historique hist3 = new HistoriqueDAO().getHistoriqueByMessage("Autre");
        
        // Création des justificatifs
        Justificatif j1 = new Justificatif("Carte d'identité","Photocopie de la carte d'identitée", TypeDossier.admissibilite, TypeJustificatifEtranger.francais);
        Justificatif j2 = new Justificatif("Sécurité sociale","Copie de la carte d'immatriculation à la sécurité sociale de l'étudiant + RIB de l'étudiant", TypeDossier.admissibilite, TypeJustificatifEtranger.etranger);
        Justificatif j3 = new Justificatif("Provenant d'une autre université","Attestation de demande de transfert (à demander auprès du service de la scolarité de votre précédente université)", TypeDossier.admissibilite, TypeJustificatifEtranger.francais);
        
        new JustificatifDAO().save(j1);
        new JustificatifDAO().save(j2);
        new JustificatifDAO().save(j3);
        
        //Utilisation des justificatifs
        Justificatif just1 = new JustificatifDAO().getJustificatifbyTitre("Carte d'identité");
        Justificatif just2 = new JustificatifDAO().getJustificatifbyTitre("Sécurité sociale");
        Justificatif just3 = new JustificatifDAO().getJustificatifbyTitre("Provenant d'une autre université");

        
        List<Justificatif> justListe1 = new ArrayList<Justificatif>();
        justListe1.add(just1);
        justListe1.add(just2);
        justListe1.add(just3);
        List<Justificatif> justListe2 = new ArrayList<Justificatif>();
        justListe2.add(just1);
        justListe2.add(just2);
        List<Justificatif> justListe3 = new ArrayList<Justificatif>();
        justListe3.add(just2);
        justListe3.add(just3);
        
        //Création des formations
        Formation f1 = new Formation("License d'informatique", 80, new Date(), new Date(), "L3 Informatique", justListe1);
        Formation f2 = new Formation("Master d'informatique", 80, new Date(), new Date(), "M1 ICONE", justListe2);
        Formation f3 = new Formation("License d'informatique", 80, new Date(), new Date(), "L2 Informatique", justListe3);
        
        new FormationDAO().save(f1);
        new FormationDAO().save(f2);
        new FormationDAO().save(f3);
       
       //Utilisation des formations
       Formation form1 = new FormationDAO().getFormationByIntitule("L3 Informatique");
       Formation form2 = new FormationDAO().getFormationByIntitule("M1 ICONE");
       Formation form3 = new FormationDAO().getFormationByIntitule("L2 Informatique");
        
        //Création des adresses
        Adresse a1 = new Adresse("17000", "La Rochelle");
        Adresse a2 = new Adresse("86000", "Poitiers");
        Adresse a3 = new Adresse("33000", "Bordeaux");
        
        new AdresseDAO().save(a1);
        new AdresseDAO().save(a2);
        new AdresseDAO().save(a3);
        
       // Utilisation des adresses
        Adresse ad1 = new AdresseDAO().getAdresseByCodePostalAndVille("17000", "La Rochelle");
        Adresse ad2 = new AdresseDAO().getAdresseByCodePostalAndVille("86000", "Poitiers");
        Adresse ad3 = new AdresseDAO().getAdresseByCodePostalAndVille("33000", "Bordeaux");
        
        // Création des étudiants
        Etudiant e1 = new Etudiant("1025655482L", "Giguère", "Valentin", "France", "17 rue de la Marne", "Masculin", a3);
        Etudiant e2 = new Etudiant("1897513572H", "Jean", "Pierre", "France", "1337 rue de leet", "Masculin", a2);
        Etudiant e3 = new Etudiant("4823014770L", "Sparrow", "Annie", "France", "42 avenue de Verdun", "Femminin", a1);
        
        new EtudiantDAO().save(e1);
        new EtudiantDAO().save(e2);
        new EtudiantDAO().save(e3);
        
        //Utilisation des étudiants
        Etudiant etu1 = new EtudiantDAO().getEtudiantByNomPrenom("Giguère", "Valentin");
        Etudiant etu2 = new EtudiantDAO().getEtudiantByNomPrenom("Jean", "Pierre");
        Etudiant etu3 = new EtudiantDAO().getEtudiantByNomPrenom("Sparrow", "Annie");
        
        //Création des étudiants étrangés        
        EtudiantEtranger ee1 = new EtudiantEtranger("bon", "favorable", "Cameron", "David", "Autriche", "1532 rue du temple", "Masculin", a1,"1945623518M");
        
        new EtudiantEtrangerDAO().save(ee1);
        
        // Utilisation des étudiants étrangers
        EtudiantEtranger etue1 = new EtudiantEtrangerDAO().getEtudiantEtrangerByNomPrenom("Cameron", "David");
        
        List<Historique> histList1 = new ArrayList<>();
        histList1.add(hist1);
        
        //Création des dossiers
        Dossier d1 = new Dossier("pst181120151", new Date(), TypeEtatDossier.en_attente_transfert_vers_directeur,null, TypeDossier.admissibilite, etu1, form2, histList1);
        Dossier d2 = new Dossier("pst181120152", new Date(), TypeEtatDossier.en_attente_commission, null, TypeDossier.admissibilite, etu1, form1);
        Dossier d3 = new Dossier("pst181120153", new Date(), TypeEtatDossier.en_transfert_vers_directeur, null, TypeDossier.inscription, etu2, form3);
        Dossier d4 = new Dossier("pst181120154", new Date(), TypeEtatDossier.navette, null, TypeDossier.inscription, etu2, form3);
        Dossier d5 = new Dossier("pst181120155", new Date(), TypeEtatDossier.retour_vers_secretariat, null, TypeDossier.inscription, etu2, form3);
        Dossier d6 = new Dossier("pst181120156", new Date(), TypeEtatDossier.terminé,null, TypeDossier.inscription, etu2, form3);
        Dossier d7 = new Dossier("pst181120157", new Date(), TypeEtatDossier.traité_secretariat_formation,null, TypeDossier.inscription, etu2, form3);
        Dossier d8 = new Dossier("pst181120158", new Date(), TypeEtatDossier.transfert_vers_secretariat, null, TypeDossier.inscription, etu2, form3);
        
        new DossierDAO().save(d1);
        new DossierDAO().save(d2);
        new DossierDAO().save(d3);
        new DossierDAO().save(d4);
        new DossierDAO().save(d5);
        new DossierDAO().save(d6);
        new DossierDAO().save(d7);
        new DossierDAO().save(d8);
        
        //Création d'action
        ActionDAO actionDAO = new ActionDAO();
        List<TypeCompte> typecompte = new ArrayList();
        typecompte.add(TypeCompte.admin);
        Action a = new Action("voirAjoutCompte", typecompte);
        actionDAO.save(a);
        
        typecompte = new ArrayList();
        typecompte.add(TypeCompte.admin);
        a = new Action("afficherInformationsDossiers", typecompte);
        actionDAO.save(a);
    }
    
    
}