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
        //Création des justificatifs
        Justificatif j1 = new Justificatif("Carte d'identité", "Photocopie de la carte d'identitée", TypeDossier.admissibilite, TypeJustificatifEtranger.francais);
        Justificatif j2 = new Justificatif("Sécurité sociale", "Copie de la carte d'immatriculation à la sécurité sociale de l'étudiant + RIB de l'étudiant", TypeDossier.admissibilite, TypeJustificatifEtranger.etranger);
        Justificatif j3 = new Justificatif("Provenant d'une autre université", "Attestation de demande de transfert (à demander auprès du service de la scolarité de votre précédente université)", TypeDossier.admissibilite, TypeJustificatifEtranger.francais);
        Justificatif j4 = new Justificatif("Diplôme du BAC", "Copie du diplôme du baccalauréat", TypeDossier.inscription, TypeJustificatifEtranger.francais);
        Justificatif j5 = new Justificatif("Diplôme BAC+2", "Copie du diplôme validant le niveau BAC+2", TypeDossier.inscription, TypeJustificatifEtranger.francais);

        new JustificatifDAO().save(j1);
        new JustificatifDAO().save(j2);
        new JustificatifDAO().save(j3);
        new JustificatifDAO().save(j4);
        new JustificatifDAO().save(j5);

        //Utilisation des justificatifs
        j1 = new JustificatifDAO().getJustificatifbyTitre("Carte d'identité");
        j2 = new JustificatifDAO().getJustificatifbyTitre("Sécurité sociale");
        j3 = new JustificatifDAO().getJustificatifbyTitre("Provenant d'une autre université");
        j4 = new JustificatifDAO().getJustificatifbyTitre("Diplôme du BAC");
        j5 = new JustificatifDAO().getJustificatifbyTitre("Diplôme BAC+2");

        List<Justificatif> justListe1 = new ArrayList<Justificatif>();
        justListe1.add(j1);
        justListe1.add(j2);
        justListe1.add(j3);
        justListe1.add(j5);
        List<Justificatif> justListe2 = new ArrayList<Justificatif>();
        justListe2.add(j1);
        justListe2.add(j2);
        List<Justificatif> justListe3 = new ArrayList<Justificatif>();
        justListe3.add(j2);
        justListe3.add(j3);
        justListe3.add(j4);

        //Création des formations
        Formation f1 = new Formation("Licence d'informatique", 80, new Date(115, 11, 13), new Date(116, 0, 2), "L3 Informatique", justListe1);
        Formation f2 = new Formation("Master d'informatique", 80, new Date(115, 2, 6), new Date(115, 3, 6), "M1 ICONE", justListe2);
        Formation f3 = new Formation("Licence d'informatique", 80, new Date(116, 0, 1), new Date(116, 1, 1), "L2 Informatique", justListe3);

        //Ajout des formations
        new FormationDAO().save(f1);
        new FormationDAO().save(f2);
        new FormationDAO().save(f3);

        //Utilisation des formations
        f1 = new FormationDAO().getFormationByIntitule("L3 Informatique");
        f2 = new FormationDAO().getFormationByIntitule("M1 ICONE");
        f3 = new FormationDAO().getFormationByIntitule("L2 Informatique");

        //Création des comptes
        Compte c1 = new Compte("admin",  "admin", "admin", "nicolas.roulon@etudiant.univ-lr.fr", TypeCompte.admin, null);
        Compte c2 = new Compte("directeur_pole",  "directeur_pole", "directeur_pole", "christian.inard@univ-lr.fr", TypeCompte.directeur_pole, null);
        Compte c3 = new Compte("responsable_administrative",  "responsable_administrative", "responsable_administrative", "marie-grace.teixeira@univ-lr.fr", TypeCompte.responsable_administrative, null);
        Compte c4 = new Compte("responsable_formation",  "responsable_formation", "responsable_formation", "bernard.besserer@univ-lr.fr", TypeCompte.responsable_formation, null);
        Compte c5 = new Compte("responsable_commission",  "responsable_commission", "responsable_commission", "arnaud.revel@univ-lr.fr", TypeCompte.responsable_commission, null);
        Compte c6 = new Compte("secrétaire",  "secrétaire", "secrétaire", "echaigne@univ-lr.fr", TypeCompte.secrétaire_inscription, null);
        Compte c7 = new Compte("secrétaire_formation",  "secrétaire_formation", "secrétaire_formation", "echaigne@univ-lr.fr", TypeCompte.secrétaire_formation, null);
        
        List<Formation> formations = new ArrayList<Formation>();
        //feed responsable_formation
        formations.add(f1);
        c4.setFormationAssocie(formations);
        //feed responsable_commission
        formations = new ArrayList<Formation>();
        formations.add(f2);
        c5.setFormationAssocie(formations);
        //feed secrétaire_formation
        formations = new ArrayList<Formation>();
        formations.add(f1);
        formations.add(f2);
        formations.add(f3);
        c7.setFormationAssocie(formations);

        //Ajout des comptes
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

        //Création des adresses
        Adresse a1 = new Adresse("17000", "La Rochelle");
        Adresse a2 = new Adresse("86000", "Poitiers");
        Adresse a3 = new Adresse("33000", "Bordeaux");

        new AdresseDAO().save(a1);
        new AdresseDAO().save(a2);
        new AdresseDAO().save(a3);

        //Utilisation des adresses
        a1 = new AdresseDAO().getAdresseByCodePostalAndVille("17000", "La Rochelle");
        a2 = new AdresseDAO().getAdresseByCodePostalAndVille("86000", "Poitiers");
        a3 = new AdresseDAO().getAdresseByCodePostalAndVille("33000", "Bordeaux");

        // Création des étudiants
        Etudiant e1 = new Etudiant("1025655482L", "Giguère", "Valentin", "France", "17 rue de la Marne", "Masculin", a1);
        Etudiant e2 = new Etudiant("1897513572H", "Jean", "Pierre", "France", "1337 rue de leet", "Masculin", a2);
        Etudiant e3 = new Etudiant("4823014770L", "Sparrow", "Annie", "France", "42 avenue de Verdun", "Feminin", a3);
        Etudiant e4 = new Etudiant("2165468465D", "Meunier", "Thérèse", "France", "17 rue de la Marne", "Masculin", a1);
        Etudiant e5 = new Etudiant("6464521356T", "Romain", "Laurent", "France", "84, rue Marie de Médicis", "Masculin", a2);
        Etudiant e6 = new Etudiant("5468761649E", "Avent", "Vachon", "France", "40, Rue St Ferréol", "Masculin", a3);
        Etudiant e7 = new Etudiant("5654688798Z", "Apolline", "Pouchard", "France", "8, rue des Nations Unies", "Feminin", a1);
        Etudiant e8 = new Etudiant("4846549848A", "René", "Duffet", "France", "40, avenue Voltaire", "Masculin", a2);
        Etudiant e9 = new Etudiant("7646582858S", "Pensee", "Goddu", "France", "5, Rue de Verdun", "Feminin", a3);

        new EtudiantDAO().save(e1);
        new EtudiantDAO().save(e2);
        new EtudiantDAO().save(e3);
        new EtudiantDAO().save(e4);
        new EtudiantDAO().save(e5);
        new EtudiantDAO().save(e6);
        new EtudiantDAO().save(e7);
        new EtudiantDAO().save(e8);
        new EtudiantDAO().save(e9);

        //Utilisation des étudiants
        e1 = new EtudiantDAO().getEtudiantByNomPrenom("Giguère", "Valentin");
        e2 = new EtudiantDAO().getEtudiantByNomPrenom("Jean", "Pierre");
        e3 = new EtudiantDAO().getEtudiantByNomPrenom("Sparrow", "Annie");
        e4 = new EtudiantDAO().getEtudiantByNomPrenom("Meunier", "Thérèse");
        e5 = new EtudiantDAO().getEtudiantByNomPrenom("Romain", "Laurent");
        e6 = new EtudiantDAO().getEtudiantByNomPrenom("Avent", "Vachon");
        e7 = new EtudiantDAO().getEtudiantByNomPrenom("Apolline", "Pouchard");
        e8 = new EtudiantDAO().getEtudiantByNomPrenom("René", "Duffet");
        e9 = new EtudiantDAO().getEtudiantByNomPrenom("Pensee", "Goddu");

        //Création des étudiants étrangers        
        EtudiantEtranger ee1 = new EtudiantEtranger("bon", "favorable", "Cameron", "David", "Autriche", "1532 rue du temple", "Masculin", a1, "1945623518M");
        EtudiantEtranger ee2 = new EtudiantEtranger("bon", "favorable", "Mary", "Parsons", "U.S.A.", "592 Grove Avenue", "Feminin", a1, "1658461258K");

        new EtudiantEtrangerDAO().save(ee1);
        new EtudiantEtrangerDAO().save(ee2);

        //Utilisation des étudiants étrangers
        ee1 = new EtudiantEtrangerDAO().getEtudiantEtrangerByNomPrenom("Cameron", "David");
        ee2 = new EtudiantEtrangerDAO().getEtudiantEtrangerByNomPrenom("Mary", "Parsons");

        Historique h1 = new Historique(new Date(), "Création du dossier", "Création du dossier", compte2);
        Historique h2 = new Historique(new Date(), "Modification par l'administrateur", "Modification de lettre", compte1);
        Historique h3 = new Historique(new Date(), "Changement d'état : traité par le secrétairiat -> en attente de commission", "Changement d'état : traité par le secrétairiat -> en attente de commission", compte2);
        Historique h4 = new Historique(new Date(), "Changement d'état : en attente de commission -> en attente transfert vers directeur", "Changement d'état : en attente de commission -> en attente transfert vers directeur", compte2);
        Historique h5 = new Historique(new Date(), "Changement d'état : en attente transfert vers directeur -> en transfert vers directeur", "Changement d'état : en attente transfert vers directeur -> en transfert vers directeur", compte2);
        Historique h6 = new Historique(new Date(), "Changement d'état : en transfert vers directeur -> retour_vers_secretariat", "Changement d'état : en transfert vers directeur -> retour_vers_secretariat", compte2);
        Historique h7 = new Historique(new Date(), "Changement d'état : retour_vers_secretariat -> terminé", "Changement d'état : retour_vers_secretariat -> terminé", compte2);
        Historique h8 = new Historique(new Date(), "Changement d'état : en transfert vers directeur -> navette", "Changement d'état : en transfert vers directeur -> navette", compte2);

        new HistoriqueDAO().save(h1);
        new HistoriqueDAO().save(h2);
        new HistoriqueDAO().save(h3);
        new HistoriqueDAO().save(h4);
        new HistoriqueDAO().save(h5);
        new HistoriqueDAO().save(h6);
        new HistoriqueDAO().save(h7);
        new HistoriqueDAO().save(h8);

        h1 = new HistoriqueDAO().getHistoriqueByMessage("Création du dossier");
        h2 = new HistoriqueDAO().getHistoriqueByMessage("Modification par l'administrateur");
        h3 = new HistoriqueDAO().getHistoriqueByMessage("Changement d'état : traité par le secrétairiat -> en attente de commission");
        h4 = new HistoriqueDAO().getHistoriqueByMessage("Changement d'état : en attente de commission -> en attente transfert vers directeur");
        h5 = new HistoriqueDAO().getHistoriqueByMessage("Changement d'état : en attente transfert vers directeur -> en transfert vers directeur");
        h6 = new HistoriqueDAO().getHistoriqueByMessage("Changement d'état : en transfert vers directeur -> retour_vers_secretariat");
        h7 = new HistoriqueDAO().getHistoriqueByMessage("Changement d'état : retour_vers_secretariat -> terminé");
        h8 = new HistoriqueDAO().getHistoriqueByMessage("Changement d'état : en transfert vers directeur -> navette");

        List<Historique> histList1 = new ArrayList<>();
        histList1.add(h1);
        List<Historique> histList2 = new ArrayList<>();
        histList2.add(h1);
        histList2.add(h2);
        List<Historique> histList3 = new ArrayList<>();
        histList3.add(h1);
        histList3.add(h3);
        List<Historique> histList4 = new ArrayList<>();
        histList4.addAll(histList3);
        histList4.add(h4);
        List<Historique> histList5 = new ArrayList<>();
        histList5.addAll(histList4);
        histList5.add(h5);
        List<Historique> histList6 = new ArrayList<>();
        histList6.addAll(histList5);
        histList6.add(h6);
        List<Historique> histList7 = new ArrayList<>();
        histList7.addAll(histList6);
        histList7.add(h7);
        List<Historique> histList8 = new ArrayList<>();
        histList8.addAll(histList5);
        histList8.add(h8);

        //Création des dossiers
        Dossier d1 = new Dossier("pst181120157", new Date(), TypeEtatDossier.traité_secretariat_formation, null, TypeDossier.inscription, e3, f2, histList1);
        Dossier d2 = new Dossier("pst181120152", new Date(), TypeEtatDossier.traité_secretariat_formation, null, TypeDossier.admissibilite, e1, f1, histList2);
        Dossier d3 = new Dossier("pst181120156", new Date(), TypeEtatDossier.en_attente_commission, null, TypeDossier.admissibilite, e4, f1, histList3);
        Dossier d4 = new Dossier("pst181120159", new Date(), TypeEtatDossier.en_attente_commission, null, TypeDossier.admissibilite, e2, f1, histList3);
        Dossier d5 = new Dossier("pst181120151", new Date(), TypeEtatDossier.en_attente_commission, null, TypeDossier.admissibilite, e1, f2, histList3);
        Dossier d6 = new Dossier("pst181120155", new Date(), TypeEtatDossier.en_attente_transfert_vers_directeur, null, TypeDossier.admissibilite, e4, f2, histList4);
        Dossier d7 = new Dossier("pst181120158", new Date(), TypeEtatDossier.en_attente_transfert_vers_directeur, null, TypeDossier.admissibilite, e5, f2, histList4);
        Dossier d8 = new Dossier("pst181120153", new Date(), TypeEtatDossier.en_transfert_vers_directeur, null, TypeDossier.inscription, e2, f3, histList5);
        Dossier d9 = new Dossier("pst181120154", new Date(), TypeEtatDossier.navette, null, TypeDossier.inscription, e2, f2, histList8);
        Dossier d10 = new Dossier("pst1811201513", new Date(), TypeEtatDossier.retour_vers_secretariat, null, TypeDossier.inscription, ee1, f3, histList6);
        Dossier d11 = new Dossier("pst1811201510", new Date(), TypeEtatDossier.retour_vers_secretariat, null, TypeDossier.inscription, e6, f3, histList6);
        Dossier d12 = new Dossier("pst1811201511", new Date(), TypeEtatDossier.retour_vers_secretariat, null, TypeDossier.inscription, e7, f3, histList6);
        Dossier d13 = new Dossier("pst1811201512", new Date(), TypeEtatDossier.retour_vers_secretariat, null, TypeDossier.inscription, e8, f3, histList6);
        Dossier d14 = new Dossier("pst1811201523", new Date(), TypeEtatDossier.terminé, null, TypeDossier.inscription, e3, f1, histList7);
        Dossier d15 = new Dossier("pst1811201525", new Date(), TypeEtatDossier.terminé, null, TypeDossier.inscription, e9, f1, histList7);
        Dossier d16 = new Dossier("pst1811201524", new Date(), TypeEtatDossier.terminé, null, TypeDossier.inscription, ee2, f1, histList7);

        new DossierDAO().save(d1);
        new DossierDAO().save(d2);
        new DossierDAO().save(d3);
        new DossierDAO().save(d4);
        new DossierDAO().save(d5);
        new DossierDAO().save(d6);
        new DossierDAO().save(d7);
        new DossierDAO().save(d8);
        new DossierDAO().save(d9);
        new DossierDAO().save(d10);
        new DossierDAO().save(d11);
        new DossierDAO().save(d12);
        new DossierDAO().save(d13);
        new DossierDAO().save(d14);
        new DossierDAO().save(d15);
        new DossierDAO().save(d16);

        menu();
    }

    public static void menu() {
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
        /**
         * *Gestion dossiers**
         */
        Action a3 = new Action("afficherInformationsDossiers", "dossier.AfficherInformationsDossiersAction", page_listeDossiers);
        Action a4 = new Action("voirAjoutDossier", "dossier.VoirAjoutDossierAction", page_ajoutDossier);
        Action a5 = new Action("ajouterDossier", "dossier.AjoutDossierAction", page_listeDossiers);
        Action a6 = new Action("consulterDossier", "dossier.ConsulterDossierAction", page_consulterDossier);
        Action a7 = new Action("modifierDossier", "dossier.ModifierDossierAction", page_listeDossiers);
        Action a8 = new Action("supprimerDossier", "dossier.SupprimerDossierAction", page_listeDossiers);
        Action a9 = new Action("voirValidationJustificatifsDossier", "dossier.VoirValidationJustificatifsDossierAction", page_consulterDossier);
        Action a25 = new Action("genererLettre", "GenerationLettres.GenerationLettresAction", page_consulterDossier);
        Action a26 = new Action("etudiantAutocompletion", "dossier.EtudiantAutocompletionAction", p12);
        /**
         * *Gestion comptes**
         */
        Action a10 = new Action("voirGestionComptes", "utilisateur.VoirGestionUtilisateurAction", page_gestionComptes);
        Action a11 = new Action("voirAjoutCompte", "utilisateur.voirAjouterCompteAction", page_createUser);
        Action a12 = new Action("creerUtilisateur", "utilisateur.AjouterCompteAction", page_gestionComptes);
        Action a13 = new Action("voirModifierComptes", "utilisateur.VoirModifierComptesAction", page_modifierUtilisateur);
        Action a14 = new Action("modifierUtilisateur", "utilisateur.ModifierUtilisateurAction", page_gestionComptes);
        Action a15 = new Action("supprimerUtilisateur", "utilisateur.SupprimerUtilisateurAction", page_gestionComptes);
        /**
         * *Gestion formations**
         */
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
