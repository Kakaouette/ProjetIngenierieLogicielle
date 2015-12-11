package service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static gestulrpeuplement.GestULRPeuplement.cryptageMDP;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modele.dao.CompteDAO;
import modele.dao.FormationDAO;
import modele.dao.JustificatifDAO;
import modele.entite.Compte;
import modele.entite.Formation;
import modele.entite.Justificatif;
import modele.entite.TypeCompte;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pierre
 */
public class TestModifierUtilisateur {
    
    public TestModifierUtilisateur() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()
    {
        /*List<Formation> formationToSet = new ArrayList<>();
        formationToSet.add(new FormationDAO().getById(15));
        Compte test = new Compte("Test",cryptageMDP("test"),"Test","Test","test@test.com",TypeCompte.secrétaire_formation,formationToSet);
        //test.setId(8);
        new CompteDAO().save(test);
        
        System.out.println("test3");*/
    }
    
    @After
    public void tearDown()
    {
        //new CompteDAO().delete(new CompteDAO().getComptebyLogin("Test").getId());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /**
     * Test sur la modification d'un utilisateur avec tout les champs remplis, de la classe ModifierUtilisateurAction.
     */
    @Test
    public void testModifierUtilisateurToutLesChamps() {
        System.out.println("testModifierUtilisateurToutLesChamps");
        int idCompte=new CompteDAO().getComptebyLogin("test").getId();
        String type="admin";
        String login="test";
        String nom="test";
        String prenom="test";
        String mail="test@gmail.com";
        String mdp="test";
        List<Formation> formations = new ArrayList();
        boolean expResult = true;
        Boolean resultat = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp,formations);
        assertEquals(expResult, resultat);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test sur la modification d'un utilisateur avec tout les champs vides, de la classe ModifierUtilisateurAction.
     */
    @Test
    public void testModifierUtilisateurAvecChampsNuls() {
        System.out.println("testModifierUtilisateurAvecChampsNuls");
        int idCompte=new CompteDAO().getComptebyLogin("test").getId();
        String type="";
        String login="";
        String nom="";
        String prenom="";
        String mail="";
        String mdp="";
        List<Formation> formations = new ArrayList();
        boolean expResult = true;
        Boolean resultat = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp,formations);
        assertEquals(expResult, resultat);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test sur la modification d'un utilisateur avec seulement quelques champs modifiés, de la classe ModifierUtilisateurAction.
     */
    @Test
    public void testModifierUtilisateurQuelquesChamps() {
        System.out.println("testModifierUtilisateurQuelquesChamps");
        int idCompte=new CompteDAO().getComptebyLogin("test").getId();
        String type="";
        String login="";
        String nom="";
        String prenom="";
        String mail="Bla2@gmail.com";
        String mdp="";
        List<Formation> formations = new ArrayList();
        boolean expResult = true;
        Boolean resultat = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp,formations);
        assertEquals(expResult, resultat);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test sur la modification d'un utilisateur qui passe d'un compte avec des droits limités à un compte de type administrateur, de la classe ModifierUtilisateurAction.
     */
    @Test
    public void testModifierUtilisateurSuperUser() {
        System.out.println("testModifierUtilisateurSuperUser");
        int idCompte=new CompteDAO().getComptebyLogin("test").getId();
        List<Formation> formationToSet = new ArrayList<>();
        formationToSet.add(new FormationDAO().getById(14));
        new CompteService().effectuerModification(idCompte,"responsable_formation", "", "", "", "", "",formationToSet);
        
        String type="admin";
        String login="";
        String nom="";
        String prenom="";
        String mail="";
        String mdp="";
        List<Formation> formations = new ArrayList();
        //fail("A modifier lorsque la classe CompteService aura été modifiée pour gerer la liste des formations");
        
        new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp,formations);
        formations = new CompteDAO().getById(idCompte).getFormationAssocie();
        List<Formation> resultat = new ArrayList();
        assertEquals(formations,resultat);
    }
    
    /**
     * Test sur la modification d'un utilisateur qui possède un compte lié à des formations et dont on retire toutes les formations, de la classe ModifierUtilisateurAction.
     * 
     */
    /*@Test
    public void testModifierUtilisateurDeleteAllFormations() {
        System.out.println("testModifierUtilisateurDeleteAllFormations");
        int idCompte=new CompteDAO().getComptebyLogin("test").getId();
        new CompteService().effectuerModification(idCompte,"responsable_formation", "", "", "", "", "",null);
        List<Formation> formationToSet = new ArrayList<Formation>();
        formationToSet.add(new FormationDAO().getById(14));
        new CompteDAO().getById(idCompte).setFormationAssocie(formationToSet);
        String type="";
        String login="";
        String nom="";
        String prenom="";
        String mail="";
        String mdp="";
        List<Formation> formations = new ArrayList();
        fail("A modifier lorsque la classe CompteService aura été modifiée pour gerer la liste des formations");
        
        //On set la liste des formations du compte à null
        new CompteDAO().getById(idCompte).setFormationAssocie(null);
        //On execute la fonction qui va update le compte
        //new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp, new CompteDAO().getById(idCompte).getFormationAssocie());
        
        //On recupere la liste des formations ducompte après update
        formations = new CompteDAO().getById(idCompte).getFormationAssocie();
        //On verifie que cette liste n'est pas nulle
        assertNotNull(formations);
    }*/
}
