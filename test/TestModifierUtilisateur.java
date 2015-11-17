/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import service.CompteService;

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
    public void setUp() {
    }
    
    @After
    public void tearDown() {
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
        System.out.println("toString");
        int idCompte=1;
        String type="admin";
        String login="Bla";
        String nom="Bla";
        String prenom="Bla";
        String mail="Bla@gmail.com";
        String mdp="Bla";
        boolean expResult = true;
        Boolean resultat = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);
        assertEquals(expResult, resultat);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test sur la modification d'un utilisateur avec seulement quelques champs modifiés, de la classe ModifierUtilisateurAction.
     */
    @Test
    public void testModifierUtilisateurQuelquesChamps() {
        System.out.println("toString");
        int idCompte=1;
        String type="admin";
        String login="Bla2";
        String nom="Bla2";
        String prenom="Bla2";
        String mail="bla2@gmail.com";
        String mdp="Bla2";
        boolean expResult = true;
        Boolean resultat = new CompteService().effectuerModification(idCompte, type, login, nom, prenom, mail, mdp);
        assertEquals(expResult, resultat);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
