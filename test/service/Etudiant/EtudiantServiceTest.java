/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Etudiant;

import java.util.List;
import modele.dao.EtudiantDAO;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Drachenfels
 */
public class EtudiantServiceTest {
    
    public EtudiantServiceTest() {
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

    /**
     * Test of verifAuthentification method, of class EtudiantService.
     */
    @Test
    public void testVerifAuthentificationEtudiantExiste() {
        System.out.println("verifAuthentificationEtudiantExiste");
        Etudiant etu = new EtudiantDAO().getEtudiantByINE("ine2");
        Etudiant test = new EtudiantService().verifAuthentification("ine2", "Jean", "Pierre");
        assertEquals(etu, test);
    }
    
    @Test
    public void testVerifAuthentificationEtudiantInconnu() {
        System.out.println("verifAuthentificationEtudiantExiste");
        Etudiant test = new EtudiantService().verifAuthentification("ine10", "blblbl", "Pierre");
        assertNull(test);
    }
    
}
