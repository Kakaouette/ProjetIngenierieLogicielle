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
public class GestULRPeuplement {
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
            Logger.getLogger(GestULRPeuplement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GestULRPeuplement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Création des comptes
        Compte c1 = new Compte("admin",  "admin", "admin", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.admin, null);
        Compte c2 = new Compte("directeur_pole",  "directeur_pole", "directeur_pole", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.directeur_pole, null);
        Compte c3 = new Compte("responsable_administrative",  "responsable_administrative", "responsable_administrative", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.responsable_administrative, null);
        Compte c4 = new Compte("responsable_formation",  "responsable_formation", "responsable_formation", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.responsable_formation, null);
        Compte c5 = new Compte("responsable_commission",  "responsable_commission", "responsable_commission", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.responsable_commission, null);
        Compte c6 = new Compte("secrétaire",  "secrétaire", "secrétaire", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.secrétaire_inscription, null);
        Compte c7 = new Compte("secrétaire_formation",  "secrétaire_formation", "secrétaire_formation", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.secrétaire_formation, null);
        
                
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
        Etudiant e1 = new Etudiant("ine1", "Giguère", "Valentin", "pays", "17 rue de la Marne", "Masculin", a3);
        Etudiant e2 = new Etudiant("ine2", "Jean", "Pierre", "ici", "1337 rue de leet", "Masculin", a2);
        Etudiant e3 = new Etudiant("ine3", "Sparrow", "Annie", "la bas", "42 avenue de Verdun", "Femminin", a1);
        
        new EtudiantDAO().save(e1);
        new EtudiantDAO().save(e2);
        new EtudiantDAO().save(e3);
        
        //Utilisation des étudiants
        Etudiant etu1 = new EtudiantDAO().getEtudiantByNomPrenom("Giguère", "Valentin");
        Etudiant etu2 = new EtudiantDAO().getEtudiantByNomPrenom("Jean", "Pierre");
        Etudiant etu3 = new EtudiantDAO().getEtudiantByNomPrenom("Sparrow", "Annie");
        
        //Création des étudiants étrangés
        EtudiantEtranger ee1 = new EtudiantEtranger("bon", "favorable", "Cameron", "David", "Autriche", "1532 rue du temple", "Masculin", a1,"ine4");
        
        new EtudiantEtrangerDAO().save(ee1);
        
        // Utilisation des étudiants étrangers
        EtudiantEtranger etue1 = new EtudiantEtrangerDAO().getEtudiantEtrangerByNomPrenom("Cameron", "David");
        
        List<Historique> histList1 = new ArrayList<>();
        histList1.add(hist1);
        
        //Création des dossiers
        Dossier d1 = new Dossier("pst181120151", new Date(), TypeEtatDossier.en_attente_transfert_vers_directeur, "Blalalalalalalalala", TypeDossier.admissibilite, etu1, form2, histList1);
        Dossier d2 = new Dossier("pst181120152", new Date(), TypeEtatDossier.en_attente_commission, "yeah", TypeDossier.admissibilite, etu1, form1);
        Dossier d3 = new Dossier("pst181120153", new Date(), TypeEtatDossier.en_transfert_vers_directeur, "sad", TypeDossier.inscription, etu2, form3);
        Dossier d4 = new Dossier("pst181120154", new Date(), TypeEtatDossier.navette, "sad", TypeDossier.inscription, etu2, form3);
        Dossier d5 = new Dossier("pst181120155", new Date(), TypeEtatDossier.retour_vers_secretariat, "sad", TypeDossier.inscription, etu2, form3);
        Dossier d6 = new Dossier("pst181120156", new Date(), TypeEtatDossier.terminé, "sad", TypeDossier.inscription, etu2, form3);
        Dossier d7 = new Dossier("pst181120157", new Date(), TypeEtatDossier.traité_secretariat_formation, "sad", TypeDossier.inscription, etu2, form3);
        Dossier d8 = new Dossier("pst181120158", new Date(), TypeEtatDossier.transfert_vers_secretariat, "sad", TypeDossier.inscription, etu2, form3);
        
        new DossierDAO().save(d1);
        new DossierDAO().save(d2);
        new DossierDAO().save(d3);
        new DossierDAO().save(d4);
        new DossierDAO().save(d5);
        new DossierDAO().save(d6);
        new DossierDAO().save(d7);
        new DossierDAO().save(d8);
        
        System.out.println(new DossierDAO().count(form2, TypeEtatDossier.en_attente_transfert_vers_directeur));
        
        menu();
    }
    
    public static void menu(){
        // Page p1 = new Page(/*String*/ "id", /*TypeCompte*/ TypeCompte.admin, /*String*/ "titre");
        Page p0 = new Page("index.jsp", TypeCompte.secrétaire_inscription, "Connexion");
        Page p1 = new Page("accueil.jsp", TypeCompte.secrétaire_inscription, "Accueil");
        
        Page p2 = new Page("listeDossiers.jsp", TypeCompte.secrétaire_inscription, "Afficher les dossiers");
        Page p3 = new Page("ajoutDossier.jsp", TypeCompte.secrétaire_inscription, "Ajouter un dossier");
        Page p4 = new Page("consulteDossier.jsp", TypeCompte.secrétaire_inscription, "Consulter un dossier");
        Page p12 = new Page("infoEtudiant.jsp", TypeCompte.secrétaire_inscription, "");
        
        Page p5 = new Page("gestionFormations.jsp", TypeCompte.responsable_formation, "Afficher les formations");
        Page p6 = new Page("ajoutFormation.jsp", TypeCompte.responsable_formation, "Creer une formation");
        Page p7 = new Page("modifFormation.jsp", TypeCompte.responsable_formation, "Consulter une formation");
        
        Page p8 = new Page("gestionDatesInscription.jsp", TypeCompte.responsable_formation, "Modifier dates d'inscription"); 
        
        Page p9 = new Page("gestionComptes.jsp", TypeCompte.admin, "Afficher les utilisateurs");
        Page p10 = new Page("createUser.jsp", TypeCompte.admin, "Creer un utilisateur");
        Page p11 = new Page("modifierUtilisateur.jsp", TypeCompte.admin, "Consulter un utilisateur");
        
        Page p13 = new Page("connexionEtudiant.jsp", TypeCompte.secrétaire_inscription, "Connexion");
        Page p14 = new Page("listeDossierEtudiant.jsp", TypeCompte.secrétaire_inscription, "Vos dossiers");
        
        new PageDAO().save(p0);
        new PageDAO().save(p1);
        new PageDAO().save(p2);
        new PageDAO().save(p3);
        new PageDAO().save(p4);
        new PageDAO().save(p5);
        new PageDAO().save(p6);
        new PageDAO().save(p7);
        new PageDAO().save(p8);
        new PageDAO().save(p9);
        new PageDAO().save(p10);
        new PageDAO().save(p11);
        new PageDAO().save(p12);
        new PageDAO().save(p13);
        new PageDAO().save(p14);
        
        Page page_connexion = new PageDAO().getById("index.jsp");
        Page page_accueil = new PageDAO().getById("accueil.jsp");
        Page page_listeDossiers = new PageDAO().getById("listeDossiers.jsp");
        Page page_ajoutDossier = new PageDAO().getById("ajoutDossier.jsp");
        Page page_consulterDossier = new PageDAO().getById("consulteDossier.jsp");
        Page page_gestionFormations = new PageDAO().getById("gestionFormations.jsp");
        Page page_ajoutFormation = new PageDAO().getById("ajoutFormation.jsp");
        Page page_modifFormation = new PageDAO().getById("modifFormation.jsp");
        Page page_gestionDatesInscription = new PageDAO().getById("gestionDatesInscription.jsp");
        Page page_gestionComptes = new PageDAO().getById("gestionComptes.jsp");
        Page page_createUser = new PageDAO().getById("createUser.jsp");
        Page page_modifierUtilisateur = new PageDAO().getById("modifierUtilisateur.jsp");
        
        //Action a1 = new Action(""/*id*/, ""/*classAction*/, 0/*page*/);
        // Création des actions
        Action a1 = new Action("gererAuthentification", "accueil.GererAuthentificationAction", page_connexion);        
        Action a2 = new Action("index", "accueil.VoirIndexAction", page_connexion);
        /***Gestion dossiers***/
        Action a3 = new Action("afficherInformationsDossiers", "dossier.AfficherInformationsDossiersAction", page_listeDossiers);
        Action a4 = new Action("voirAjoutDossier", "dossier.VoirAjoutDossierAction", page_ajoutDossier);
        Action a5 = new Action("ajouterDossier", "dossier.AjoutDossierAction", page_listeDossiers);
        Action a6 = new Action("consulterDossier", "dossier.ConsulterDossierAction", page_consulterDossier);
        Action a7 = new Action("modifierDossier", "dossier.ModifierDossierAction", page_listeDossiers);
        Action a8 = new Action("supprimerDossier", "dossier.SupprimerDossierAction", page_listeDossiers);
        Action a9 = new Action("voirValidationJustificatifsDossier", "dossier.VoirValidationJustificatifsDossierAction", page_consulterDossier);
        Action a25 = new Action("genererLettre", "GenerationLettres.GenerationLettresAction", page_consulterDossier);
        Action a26 = new Action("etudiantAutocompletion", "dossier.EtudiantAutocompletionAction", p12);
        /***Gestion comptes***/
        Action a10 = new Action("voirGestionComptes", "utilisateur.VoirGestionUtilisateurAction", page_gestionComptes);
        Action a11 = new Action("voirAjoutCompte", "utilisateur.voirAjouterCompteAction", page_createUser);
        Action a12 = new Action("creerUtilisateur", "utilisateur.AjouterCompteAction", page_gestionComptes);
        Action a13 = new Action("voirModifierComptes", "utilisateur.VoirModifierComptesAction", page_modifierUtilisateur);
        Action a14 = new Action("modifierUtilisateur", "utilisateur.ModifierUtilisateurAction", page_gestionComptes);
        Action a15 = new Action("supprimerUtilisateur", "utilisateur.SupprimerUtilisateurAction", page_gestionComptes);
        /***Gestion formations***/
        Action a16 = new Action("voirGestionFormation", "formation.VoirGestionFormationsAction", page_gestionFormations);
        Action a17 = new Action("voirAjoutFormation", "formation.VoirAjoutFormationAction", page_ajoutFormation);
        Action a18 = new Action("ajouterFormation", "formation.AjoutFormationAction", page_gestionFormations);
        Action a19 = new Action("voirModifFormation", "formation.VoirModifFormationAction", page_modifFormation);
        Action a20 = new Action("modifierFormation", "formation.ModifFormationAction", page_gestionFormations);
        Action a21 = new Action("supprimerFormation", "formation.SupprFormationAction", page_gestionFormations);
        Action a22 = new Action("voirGestionDatesInscription", "formation.VoirGestionDatesInscriptionAction", page_gestionDatesInscription);
        Action a23 = new Action("voirDatesInscription", "formation.VoirDatesInscriptionAction", page_gestionDatesInscription);
        Action a24 = new Action("modiferDatesInscription", "formation.ModifDatesInscriptionAction", page_gestionDatesInscription);
        
        Action a27 = new Action("connexionEtudiant", "formation.ModifDatesInscriptionAction", p13);
        Action a28 = new Action("afficherDossier", "formation.ModifDatesInscriptionAction", p14);
                
        new ActionDAO().save(a1);
        new ActionDAO().save(a2);
        new ActionDAO().save(a3);
        new ActionDAO().save(a4);
        new ActionDAO().save(a5);
        new ActionDAO().save(a6);
        new ActionDAO().save(a7);
        new ActionDAO().save(a8);
        new ActionDAO().save(a9);
        new ActionDAO().save(a10);
        new ActionDAO().save(a11);
        new ActionDAO().save(a12);
        new ActionDAO().save(a13);
        new ActionDAO().save(a14);
        new ActionDAO().save(a15);
        new ActionDAO().save(a16);
        new ActionDAO().save(a17);
        new ActionDAO().save(a18);
        new ActionDAO().save(a19);
        new ActionDAO().save(a20);
        new ActionDAO().save(a21);
        new ActionDAO().save(a22);
        new ActionDAO().save(a23);
        new ActionDAO().save(a24);
        new ActionDAO().save(a25);
        new ActionDAO().save(a26);
        new ActionDAO().save(a27);
        new ActionDAO().save(a28);
        
        Action act_index = new ActionDAO().getById("index");
        Action act_dossiers = new ActionDAO().getById("afficherInformationsDossiers");
        Action act_ajout_dossiers = new ActionDAO().getById("voirAjoutDossier");
        Action act_comptes = new ActionDAO().getById("voirGestionComptes");
        Action act_ajout_compte = new ActionDAO().getById("voirAjoutCompte");
        Action act_formations = new ActionDAO().getById("voirGestionFormation");
        Action act_ajout_formations = new ActionDAO().getById("voirAjoutFormation");
        Action act_dates_inscription = new ActionDAO().getById("voirGestionDatesInscription");
        
        // Création des menus
        Menu m0 = new Menu("Accueil", null, act_index, "fa-home");
        new MenuDAO().save(m0);
        
        Menu m1 = new Menu("Dossiers", null, null, "fa-folder-open");
        Menu m2 = new Menu("Formations", null, null, "fa-university");
        Menu m3 = new Menu("Utilisateurs", null, null, "fa-users");
        
        new MenuDAO().save(m1);
        new MenuDAO().save(m2);
        new MenuDAO().save(m3);
        
        Menu menu_dossiers = new MenuDAO().getMenubyTexte("Dossiers");
        Menu menu_formations = new MenuDAO().getMenubyTexte("Formations");
        Menu menu_comptes = new MenuDAO().getMenubyTexte("Utilisateurs");
        
        Menu m4 = new Menu("Afficher les dossiers", menu_dossiers, act_dossiers, "fa-edit");
        Menu m5 = new Menu("Enregistrer un dossier", menu_dossiers, act_ajout_dossiers, "fa-plus");
        Menu m6 = new Menu("Afficher les formations", menu_formations, act_formations, "fa-edit");
        Menu m7 = new Menu("Nouvelle formation", menu_formations, act_ajout_formations, "fa-plus");
        Menu m8 = new Menu("Modifier les dates d'inscription", menu_formations, act_dates_inscription, "fa-calendar");
        Menu m9 = new Menu("Afficher les utilisateurs", menu_comptes, act_comptes, "fa-edit");
        Menu m10 = new Menu("Creer un nouveau utilisateur", menu_comptes, act_ajout_compte, "fa-plus");
        
        new MenuDAO().save(m4);
        new MenuDAO().save(m5);
        new MenuDAO().save(m6);
        new MenuDAO().save(m7);
        new MenuDAO().save(m8);
        new MenuDAO().save(m9);
        new MenuDAO().save(m10);
       
    }
    
}
