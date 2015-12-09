/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exception.AjoutDossierInvalideException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.AdresseDAO;
import modele.dao.CompteDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.dao.HistoriqueDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeCompte;
import modele.entite.TypeDossier;
import modele.entite.TypeEtatDossier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Etienne
 */
public class DossierServiceTest {
    DossierService instance = new DossierService();
        Adresse adresse;
        Etudiant etudiant;
        Formation formation;
        Compte c;
        Historique historique;
        Dossier dossier;
        
    public DossierServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new DossierService();
        
        adresse = new Adresse("1", "v");
        etudiant = new Etudiant("1", "n", "p", "a", "M", adresse);
        formation = new Formation("d", 0, null, null, "i", null);
        c = new Compte("l", "m", "n", "p", "m", TypeCompte.admin, null);
        historique = new Historique(new Date(), "Message", "Action", c);
        dossier = new Dossier();
    }
    
    @After
    public void tearDown() {
        instance = null;
        
        //free BDD
        try {
            new AdresseDAO().delete(adresse.getId());
        } catch (Exception e) {}
        try {
            new EtudiantDAO().delete(etudiant.getId());
        } catch (Exception e) {}
        try {
            new FormationDAO().delete(formation.getId());
        } catch (Exception e) {}
        try {
            new CompteDAO().delete(c.getId());
        } catch (Exception e) {}
        try {
            new HistoriqueDAO().delete(historique.getId());
        } catch (Exception e) {}
        try {
            new DossierDAO().delete(dossier.getId());
        } catch (Exception e) {}
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierIdNull() throws Exception {
        System.out.println("testAjouterDossierIdNull");
        
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(null, new Date(), TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }catch(AjoutDossierInvalideException e){}
}
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierIdVide() throws Exception {
        System.out.println("testAjouterDossierIdVide");
        
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier("", new Date(), TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierIdIncorrect() throws Exception {
        System.out.println("testAjouterDossierIdIncorrect");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier("azerty", new Date(), TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierDateNull() throws Exception {
        System.out.println("testAjouterDossierDateNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtatNull() throws Exception {
        System.out.println("testAjouterDossierEtatNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), null, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
        
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierTypeNull() throws Exception {
        System.out.println("testAjouterDossierTypeNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.creer, "UneLettre", null, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, null, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantINENull() throws Exception {
        System.out.println("testAjouterDossierEtudiantINENull");
        etudiant = new Etudiant(null, "n", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantINEVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantINEVide");
        etudiant = new Etudiant("", "n", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantNomNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantNomNull");
        etudiant = new Etudiant("1", null, "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantNomVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantNomVide");
        etudiant = new Etudiant("1", "", "p", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantPrenomNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantPrenomNull");
        etudiant = new Etudiant("1", "n", null, "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantPrenomVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantPrenomVide");
        etudiant = new Etudiant("1", "n", "", "a", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantAdresseNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantAdresseNull");
        etudiant = new Etudiant("1", "n", "p", null, "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantAdresseVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantAdresseVide");
        etudiant = new Etudiant("1", "n", "p", "", "M", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantSexeNull() throws Exception {
        System.out.println("testAjouterDossierEtudiantSexeNull");
        etudiant = new Etudiant("1", "n", "p", "a", null, adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierEtudiantSexeVide() throws Exception {
        System.out.println("testAjouterDossierEtudiantSexeVide");
        etudiant = new Etudiant("1", "n", "p", "a", "", adresse);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierFormationNull() throws Exception {
        System.out.println("testAjouterDossierFormationNull");
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, null, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierFormationIntituleNull() throws Exception {
        System.out.println("testAjouterDossierFormationIntituleNull");
        formation = new Formation("d", 0, null, null, null, null);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierFormationIntituleVide() throws Exception {
        System.out.println("testAjouterDossierFormationIntituleVide");
        formation = new Formation("d", 0, null, null, "", null);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), null, TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Erreur: Dossier ajouté");
            new DossierDAO().delete(dossier.getId());
        }catch(AjoutDossierInvalideException e){}
    }
    
    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossierValide() throws Exception {
        System.out.println("testAjouterDossierValide");
        new FormationDAO().save(formation);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        dossier = new Dossier(instance.getNewID(), new Date(), TypeEtatDossier.creer, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
        }catch(AjoutDossierInvalideException e){
            fail(e.getMessage());
        }
        
        if(!new DossierDAO().getById(dossier.getId()).equals(dossier)){
             fail("Dossier inégal.");
        }
    }
    
    
    
    /**
     * Test of getRegexIdDossier method, of class DossierService.
     */
    @Test
    public void testGetRegexIdDossier() {
        System.out.println("getRegexIdDossier");
        String expResult = "pst((0[1-9])|([1-2][0-9])|3[0-1])(0[1-9]|1[0-2])([0-9]{4})[0-9]+";
        String result = "";
        try {
            result = instance.getRegexIdDossier();
        } catch (IOException ex) {
            Logger.getLogger(DossierServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getNewID method, of class DossierService.
     */
    @Test
    public void testGetNewID() {
        System.out.println("getNewID");
        
        DossierDAO dossierDAO = new DossierDAO();
        
        Date dateNow = new Date(); //recuperation de la date actuelle
        String dernierID = dossierDAO.getLastId(dateNow);
        
        String result1 = instance.getNewID();
        Dossier d1 = new Dossier(result1, new Date(), TypeEtatDossier.creer, null, TypeDossier.inscription, etudiant, formation);
        dossierDAO.save(d1);
        
        
        Etudiant etudiant2 = new Etudiant("2", "nn", "pp", "a", "M", adresse);
        new EtudiantDAO().save(etudiant2);
        
        String result2 = instance.getNewID();
        Dossier d2 = new Dossier(result2, new Date(), TypeEtatDossier.creer, null, TypeDossier.inscription, etudiant, formation);
        dossierDAO.save(d2);

        ////        / DATE DU DOSSIER        / /    ID DOSSIER                      ////
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+1), result1);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+2), result2);
        
        new DossierDAO().delete(d1.getId());
        new DossierDAO().delete(d2.getId());
    }

    /**
     * Test of regexIdDossierValide method, of class DossierService.
     */
    @Test
    public void testRegexIdDossierValide() {
        System.out.println("regexIdDossierValide");
        try {
            assertEquals(true, instance.regexIdDossierValide("pst011120111")); //day min
            assertEquals(true, instance.regexIdDossierValide("pst150120111")); //month min
            assertEquals(true, instance.regexIdDossierValide("pst151120110")); //num min
            assertEquals(true, instance.regexIdDossierValide("pst151120115")); //num intermediaire
            assertEquals(true, instance.regexIdDossierValide("pst1511201110")); //num à 2 chiffre
            assertEquals(false, instance.regexIdDossierValide("")); //id vide
            assertEquals(false, instance.regexIdDossierValide("pst")); //id nom complet
            assertEquals(false, instance.regexIdDossierValide("pst001120111")); //day < min
            assertEquals(false, instance.regexIdDossierValide("pst451120110")); //day > max
            assertEquals(false, instance.regexIdDossierValide("pst150020110")); //month < min
            assertEquals(false, instance.regexIdDossierValide("pst151320111")); //month > max
        } catch (Exception ex) {
            Logger.getLogger(DossierServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exception.AjoutDossierInvalideException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.AdresseDAO;
import modele.dao.CompteDAO;
import modele.dao.DossierDAO;
import modele.dao.EtudiantDAO;
import modele.dao.FormationDAO;
import modele.dao.HistoriqueDAO;
import modele.entite.Adresse;
import modele.entite.Compte;
import modele.entite.Dossier;
import modele.entite.Etudiant;
import modele.entite.Formation;
import modele.entite.Historique;
import modele.entite.TypeCompte;
import modele.entite.TypeDossier;
import modele.entite.TypeEtatDossier;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Etienne
 */
public class DossierServiceTest {
    DossierService instance = new DossierService();
    DossierDAO dossierDAO = new DossierDAO();
    CompteDAO compteDAO = new CompteDAO();
    int idCompte; //id du compte créé pour les tests
    Compte compte;//Compte créé pour les tests
    
    public DossierServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        compte = new Compte("adminlogin", "mdpadmin", "Duvalle", "Pierre", "mail@mesmails.fr", TypeCompte.admin, null);
        compteDAO.save(compte);
        compte = compteDAO.getComptebyIdentifiant("adminlogin");
        idCompte = compte.getId();
        System.out.println(idCompte);
    }
    
    @After
    public void tearDown() {
        compteDAO.delete(idCompte);
    }

    /**
     * Test of getNewID method, of class DossierService.
     */
    @Test
    public void testGetNewID() {
        System.out.println("getNewID");
        
        DossierDAO dossierDAO = new DossierDAO();
        
        Date dateNow = new Date(); //recuperation de la date actuelle
        String dernierID = dossierDAO.getLastId(dateNow);
        
        //String id, Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation
        Etudiant etudiant = new EtudiantDAO().getEtudiantByNomPrenom("Jean","Pierre");
        String result1 = instance.getNewID();
        Dossier d1 = new Dossier(result1, new Date(), TypeEtatDossier.transfert_vers_secretariat, null, TypeDossier.inscription, etudiant, null);
        dossierDAO.save(d1);
        String result2 = instance.getNewID();
        Dossier d2 = new Dossier(result2, new Date(), TypeEtatDossier.transfert_vers_secretariat, null, TypeDossier.inscription, etudiant, null);
        dossierDAO.save(d2);
        String result3 = instance.getNewID();
        Dossier d3 = new Dossier(result3, new Date(), TypeEtatDossier.transfert_vers_secretariat, null, TypeDossier.inscription, etudiant, null);
        dossierDAO.save(d3);
        String result4 = instance.getNewID();
        Dossier d4 = new Dossier(result4, new Date(), TypeEtatDossier.transfert_vers_secretariat, null, TypeDossier.inscription, etudiant, null);
        dossierDAO.save(d4);

        ////        / DATE DU DOSSIER        / /    ID DOSSIER                      ////
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+1), result1);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+2), result2);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+3), result3);
        assertEquals(dernierID.substring(0,11)+(Integer.parseInt(dernierID.substring(11))+4), result4);
        
        new DossierDAO().delete(d1.getId());
        new DossierDAO().delete(d2.getId());
        new DossierDAO().delete(d3.getId());
        new DossierDAO().delete(d4.getId());
    }

    /**
     * Test of ajouterDossier method, of class DossierService.
     */
    @Test
    public void testAjouterDossier() throws Exception {
        System.out.println("ajouterDossier");
        Etudiant etudiant = new EtudiantDAO().getEtudiantByNomPrenom("Jean","Pierre");
        Formation formation = new FormationDAO().getFormationByIntitule("M1 ICONE");
        Compte c = new CompteDAO().getById(1);
        Historique historique = new Historique(new Date(), "Message", "Action", c);
        List<Historique> sesHistoriques = new ArrayList<>();
        sesHistoriques.add(historique);
        ///   (Date date, String etat, String lettre, boolean admissible, Etudiant etudiant, Formation demandeFormation, List<Historique> historique)
        Dossier dossier = new Dossier("NeDoitPasPasser", new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, etudiant, formation, sesHistoriques);
        
        /// //////////////////////////// TEST AVEC UN ID INCORRECTE ////////////////////////////
        try {
            instance.ajouterDossier(dossier);
            fail("Ne doit pas passer ici.");
        }
        catch(AjoutDossierInvalideException e)
        {
        }
        /// //////////////////////////// TEST AVEC UN ID CORRECTE ////////////////////////////
        Adresse uneAdresse = new Adresse("test_codePoste", "test_Ville");
        Etudiant unEtudiant = new Etudiant("ineLambda", "test_Nom", "test_Prenom", "test_adressePostale", "test_Homme", uneAdresse);
        String idDossier = instance.getNewID();
        dossier = new Dossier(idDossier, new Date(), TypeEtatDossier.transfert_vers_secretariat, "UneLettre", TypeDossier.inscription, unEtudiant, formation, sesHistoriques);
        
        /// On l'insère
        instance.ajouterDossier(dossier);
        
        /// on vérifie son existance.
        Dossier cpt = new DossierDAO().getById(idDossier);
        assertEquals(dossier.equals(cpt), true);
        
        new DossierDAO().delete(dossier.getId());
        new EtudiantDAO().delete(unEtudiant.getId());
        new AdresseDAO().delete(uneAdresse.getId());
        new HistoriqueDAO().delete(historique.getId());
        
        //new AdresseDAO().delete(uneAdresse);
        //new EtudiantDAO().delete(unEtudiant);
        //dossierDAO.
    }

    /**
     * Test of regexIdDossierValide method, of class DossierService.
     */
    @Test
    public void testRegexIdDossierValide() {
        System.out.println("regexIdDossierValide");
        try {
            assertEquals(true, instance.regexIdDossierValide("pst151120110"));
            assertEquals(true, instance.regexIdDossierValide("pst251220151"));
            assertEquals(true, instance.regexIdDossierValide("pst171119922"));
            assertEquals(true, instance.regexIdDossierValide("pst151120113"));
            assertEquals(true, instance.regexIdDossierValide("pst150120111"));
            assertEquals(false, instance.regexIdDossierValide("pst"));
            assertEquals(false, instance.regexIdDossierValide(""));
            assertEquals(false, instance.regexIdDossierValide("pst451120110"));
            assertEquals(false, instance.regexIdDossierValide("pst601120111"));
            assertEquals(false, instance.regexIdDossierValide("pst151920110"));
        } catch (Exception ex) {
            Logger.getLogger(DossierServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getRegexIdDossier method, of class DossierService.
     */
    @Test
    public void testGetRegexIdDossier() {
        System.out.println("getRegexIdDossier");
        String expResult = "pst((0[1-9])|([1-2][0-9])|3[0-1])(0[1-9]|1[0-2])([0-9]{4})[0-9]+";
        String result = "";
        try {
            result = instance.getRegexIdDossier();
        } catch (IOException ex) {
            Logger.getLogger(DossierServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
    }
    
    /**
     * Méthode permettant de comparer deux dossiers
     * @param updatedDossier Le dossier modifier dans la base
     * @param dossierorigin Le dossier qui correspond au résultat attendu
     * @return true si les deux dossier sont identiques sinon return false
     */
    public boolean comparerDossiers(Dossier updatedDossier, Dossier dossierorigin){
        if(updatedDossier.getAdmissible() == dossierorigin.getAdmissible() &&
                updatedDossier.getEtat() == dossierorigin.getEtat() &&
                updatedDossier.getDate() == dossierorigin.getDate() &&
                updatedDossier.getLettre().equals(dossierorigin.getLettre())){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Méthode permettant de comparer deux historiques
     * @param histodossierupdated L'historique modifier dans la base
     * @param histodossier L'historique qui correspond au résultat attendu
     * @return true si les deux historiques sont identiques sinon return false
     */
    public boolean comparerHistoriques(List<Historique> histodossierupdated, List<Historique> histodossier){
        if (histodossier.size() == histodossierupdated.size()){
            for(int i = 0; i <histodossier.size(); i++){
                if (!histodossier.get(i).equals(histodossierupdated.get(i))){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }
    
    
    /**
     * Test de la modification du champ Etat du dossier id = pst181120151  (Premier dossier créer par GestULRPeuplement)
     * avec une note de modification
     */
    @Test
    public void testModifierEtatAvecNote(){
        Dossier dossierorigin = dossierDAO.getById("pst181120151");
        
        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        dossierorigin.setEtat(TypeEtatDossier.en_attente_commission);
        String messageHisto = "Modification pour test";
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage(messageHisto);
        histo.setAction("Changement d'état du dossier pour " + TypeEtatDossier.en_attente_commission + ".");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120151");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
    
    
    /**
     * Test de la modification du champ Etat du dossier id = pst181120152  (deuxième dossier créer par GestULRPeuplement)
     * sans note de modification
     */
    @Test
    public void testModifierEtatSansNote(){
        Dossier dossierorigin = dossierDAO.getById("pst181120152");
           
        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        dossierorigin.setEtat(TypeEtatDossier.en_transfert_vers_directeur);
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage("");
        histo.setAction("Changement d'état du dossier pour " + TypeEtatDossier.en_transfert_vers_directeur + ".");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120152");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
    
    /**
     * Test d'ajout d'une note sur le dossier id = pst181120153  (troisème dossier créer par GestULRPeuplement)
     */
    @Test
    public void testConserverEtatAvecNote(){
        Dossier dossierorigin = dossierDAO.getById("pst181120153");

        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage("Note pour test sans changement d'état.");
        histo.setAction("");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //ajout du nouvel historique dans le dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        histodossier.add(histo);
        dossierorigin.setHistorique(histodossier);
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120153");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
    
    /**
     * Test d'ajout d'une note sur le dossier id = pst181120153  (troisème dossier créer par GestULRPeuplement)
     */
    @Test
    public void testAucunChangement(){
        Dossier dossierorigin=new DossierDAO().getById("pst181120154");

        TypeEtatDossier oldEtat = dossierorigin.getEtat();
        //Modification du champ état du dossier
        Historique histo=new Historique();
        Date dateHisto = new Date();
        histo.setMessage("Note pour test sans changement d'état.");
        histo.setAction("");
        histo.setDate(dateHisto);
        histo.setCompte(compte);
        
        //Récupération de l'historique du dossier
        List<Historique> histodossier = dossierorigin.getHistorique();
        
        //Modification du dossier
        instance.modifierDossier(dossierorigin);
        
        //Récupération du dossier après modification
        Dossier updatedDossier = new DossierDAO().getById("pst181120154");
        List<Historique> histodossierupdated = updatedDossier.getHistorique();
        
        //On verifie que les dossiers et les historiques sont identiques (On s'attend à avoir true en resultat)
        boolean result = this.comparerDossiers(updatedDossier, dossierorigin) && this.comparerHistoriques(histodossierupdated, histodossier);
        
        assertEquals(true, result);
        //On remet l'état du dossier à son ancienne valeur
        updatedDossier.setEtat(oldEtat);
        instance.modifierDossier(updatedDossier);
    }
}
