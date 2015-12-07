/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.SimpleDateFormat;
import modele.dao.DossierDAO;
import modele.entite.Compte;
import modele.entite.Dossier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author dorian
 */
public class DossierServiceTest {
    DossierService instance = new DossierService();
    
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
     * Test de la modification du champ Etat du dossier id = 1
     */
    public void testModifierChamp(){
        Dossier dossierorigin=new DossierDAO().getById("1");
        
        //Récupération des informations qui ne changeront pas
        boolean admDossier = dossierorigin.isAdmissible();
        String dateDossier = dossierorigin.getDate().toString();
        String lettreDossier = dossierorigin.getLettre();
        
        //Information qui modifiront le dossier
        String messageHisto = "Modification pour test";
        String dateHisto = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").toString();
        String etatDossier = "En test";
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin, admDossier, etatDossier, dateDossier, lettreDossier, messageHisto, dateHisto);
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("1");
        boolean result = false;
        if(updatedDossier.isAdmissible() == admDossier &&
                updatedDossier.getEtat().equals(etatDossier) &&
                updatedDossier.getDate().toString().equals(dateDossier) &&
                updatedDossier.getLettre().equals(lettreDossier)){
            result = true;
        }
        
        assertEquals(result, true);
    }
    
    
    /**
     * Test de la modification d'aucun champ du dossier id = 1
     */
    public void testModifierAucunChamp(){
        Dossier dossierorigin=new DossierDAO().getById("1");
        
        //Récupération des informations qui ne changeront pas
        boolean admDossier = dossierorigin.isAdmissible();
        String etatDossier = dossierorigin.getEtat();
        String dateDossier = dossierorigin.getDate().toString();
        String lettreDossier = dossierorigin.getLettre();
        
        //Information qui modifiront le dossier
        String messageHisto = "Modification pour test";
        String dateHisto = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").toString();
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin, admDossier, etatDossier, dateDossier, lettreDossier, messageHisto, dateHisto);
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("1");
        boolean result = false;
        if(updatedDossier.isAdmissible() == admDossier &&
                updatedDossier.getEtat().equals(etatDossier) &&
                updatedDossier.getDate().toString().equals(dateDossier) &&
                updatedDossier.getLettre().equals(lettreDossier)){
            result = true;
        }
        
        assertEquals(result, true);
    }
}
