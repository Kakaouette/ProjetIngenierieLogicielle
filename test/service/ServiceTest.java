/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author nicol
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({service.DossierServiceTest.class, service.DossierService_ajouterDossierTest.class, service.FormationService_ajouterFormationTest.class, service.TestModifierUtilisateur.class, service.CompteServiceTest.class, service.FormationService_modifierFormationTest.class, service.FormationService_supprimerFormationTest.class})
public class ServiceTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
