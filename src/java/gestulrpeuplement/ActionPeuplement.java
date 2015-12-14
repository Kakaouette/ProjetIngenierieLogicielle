/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestulrpeuplement;

import modele.dao.*;
import modele.entite.*;

/**
 *
 * @author nicol
 */
public class ActionPeuplement {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Page p1 = new Page(/*String*/ "id", /*TypeCompte*/ TypeCompte.admin, /*String*/ "titre");
        Page p0 = new Page("index.jsp", TypeCompte.secrétaire_inscription, "Connexion");
        Page p1 = new Page("accueil.jsp", TypeCompte.secrétaire_inscription, "Accueil");
        
        Page p2 = new Page("listeDossiers.jsp", TypeCompte.secrétaire_inscription, "Afficher les dossiers");
        Page p3 = new Page("ajoutDossier.jsp", TypeCompte.secrétaire_inscription, "Ajouter un dossier");
        Page p4 = new Page("consulteDossier.jsp", TypeCompte.secrétaire_inscription, "Consulter un dossier");
        
        Page p5 = new Page("gestionFormations.jsp", TypeCompte.responsable_formation, "Afficher les formations");
        Page p6 = new Page("ajoutFormation.jsp", TypeCompte.responsable_formation, "Creer une formation");
        Page p7 = new Page("modifFormation.jsp", TypeCompte.responsable_formation, "Consulter une formation");
        
        Page p8 = new Page("gestionDatesInscription.jsp", TypeCompte.responsable_formation, "Modifier dates d'inscription"); 
        
        Page p9 = new Page("gestionComptes.jsp", TypeCompte.admin, "Afficher les utilisateurs");
        Page p10 = new Page("createUser.jsp", TypeCompte.admin, "Creer un utilisateur");
        Page p11 = new Page("modifierUtilisateur.jsp", TypeCompte.admin, "Consulter un utilisateur");
        
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
        Action a2 = new Action("index", "VoirIndexAction", page_connexion);
        /***Gestion dossiers***/
        Action a3 = new Action("afficherInformationsDossiers", "AfficherInformationsDossiersAction", page_listeDossiers);
        Action a4 = new Action("voirAjoutDossier", "VoirAjoutDossierAction", page_ajoutDossier);
        Action a5 = new Action("ajouterDossier", "AjoutDossierAction", page_listeDossiers);
        Action a6 = new Action("consulterDossier", "ConsulterDossierAction", page_consulterDossier);
        Action a7 = new Action("modifierDossier", "ModifierDossierAction", page_listeDossiers);
        Action a8 = new Action("supprimerDossier", "SupprimerDossierAction", page_listeDossiers);
        Action a9 = new Action("voirValidationJustificatifsDossier", "VoirValidationJustificatifsDossierAction", page_consulterDossier);
        Action a25 = new Action("genererLettre", "GenerationLettresAction", page_consulterDossier);
        /***Gestion comptes***/
        Action a10 = new Action("voirGestionComptes", "VoirGestionUtilisateurAction", page_gestionComptes);
        Action a11 = new Action("voirAjoutCompte", "voirAjouterCompteAction", page_createUser);
        Action a12 = new Action("creerUtilisateur", "AjouterCompteAction", page_gestionComptes);
        Action a13 = new Action("voirModifierComptes", "VoirModifierComptesAction", page_modifierUtilisateur);
        Action a14 = new Action("modifierUtilisateur", "ModifierUtilisateurAction", page_gestionComptes);
        Action a15 = new Action("supprimerUtilisateur", "SupprimerUtilisateurAction", page_gestionComptes);
        /***Gestion formations***/
        Action a16 = new Action("voirGestionFormation", "VoirGestionFormationsAction", page_gestionFormations);
        Action a17 = new Action("voirAjoutFormation", "VoirAjoutFormationAction", page_ajoutFormation);
        Action a18 = new Action("ajouterFormation", "AjoutFormationAction", page_gestionFormations);
        Action a19 = new Action("voirModifFormation", "VoirModifFormationAction", page_modifFormation);
        Action a20 = new Action("modifierFormation", "ModifFormationAction", page_gestionFormations);
        Action a21 = new Action("supprimerFormation", "SupprFormationAction", page_gestionFormations);
        Action a22 = new Action("voirGestionDatesInscription", "VoirGestionDatesInscriptionAction", page_gestionDatesInscription);
        Action a23 = new Action("voirDatesInscription", "VoirDatesInscriptionAction", page_gestionDatesInscription);
        Action a24 = new Action("modiferDatesInscription", "ModifDatesInscriptionAction", page_gestionDatesInscription);
        
                
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
        
        Action act_index = new ActionDAO().getById("index");
        Action act_dossiers = new ActionDAO().getById("afficherInformationsDossiers");
        Action act_ajout_dossiers = new ActionDAO().getById("voirAjoutDossier");
        Action act_comptes = new ActionDAO().getById("voirGestionComptes");
        Action act_ajout_compte = new ActionDAO().getById("voirAjoutCompte");
        Action act_formations = new ActionDAO().getById("voirGestionFormation");
        Action act_ajout_formations = new ActionDAO().getById("voirAjoutFormation");
        Action act_dates_inscription = new ActionDAO().getById("voirGestionDatesInscription");
        
        // Création des menus
        Menu m0 = new Menu("Accueil", null, act_index);
        new MenuDAO().save(m0);
        
        Menu m1 = new Menu("Dossiers", null, null);
        Menu m2 = new Menu("Formations", null, null);
        Menu m3 = new Menu("Utilisateurs", null, null);
        
        new MenuDAO().save(m1);
        new MenuDAO().save(m2);
        new MenuDAO().save(m3);
        
        Menu menu_dossiers = new MenuDAO().getMenubyTexte("Dossiers");
        Menu menu_formations = new MenuDAO().getMenubyTexte("Formations");
        Menu menu_comptes = new MenuDAO().getMenubyTexte("Utilisateurs");
        
        Menu m4 = new Menu("Afficher les dossiers", menu_dossiers, act_dossiers);
        Menu m5 = new Menu("Enregistrer un dossier", menu_dossiers, act_ajout_dossiers);
        Menu m6 = new Menu("Afficher les formations", menu_formations, act_formations);
        Menu m7 = new Menu("Nouvelle formation", menu_formations, act_ajout_formations);
        Menu m8 = new Menu("Modifier les dates d'inscription", menu_formations, act_dates_inscription);
        Menu m9 = new Menu("Afficher les comptes", menu_comptes, act_comptes);
        Menu m10 = new Menu("Creer un nouveau compte", menu_comptes, act_ajout_compte);
        
        new MenuDAO().save(m4);
        new MenuDAO().save(m5);
        new MenuDAO().save(m6);
        new MenuDAO().save(m7);
        new MenuDAO().save(m8);
        new MenuDAO().save(m9);
        new MenuDAO().save(m10);
    }
    
    
}
