package service.GenerationLettres;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Formation;
import modele.entite.Etudiant;
import modele.entite.Historique;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import page.action.dossier.AfficherInformationsDossiersAction;
import static service.GenerationLettres.CreerLettreAccepteService.PATH_MODELS;
import static service.GenerationLettres.CreerLettreAuditionService.PATH_TARGET;



/**
 * 
 * @author Drapeau, Chasseloup, Giguère
 * @version 3
 */
public class CreerLettreRefusService
{
    static private String getConfigurationPropertiesPathModels() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            return properties.getProperty("path.models");
        } catch (IOException ex) {
            Logger.getLogger(AfficherInformationsDossiersAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    static private String getConfigurationPropertiesPathTarget() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("serveur.properties"));
            return properties.getProperty("path.target");
        } catch (IOException ex) {
            Logger.getLogger(AfficherInformationsDossiersAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    static final String PATH_MODELS = getConfigurationPropertiesPathModels();
    static final String PATH_TARGET = getConfigurationPropertiesPathTarget();
    
    /**
     * 
     * @param filename - Nom du fichier modèle de la lettre de refus
     * @param idDossier - Identifiant du dossier pour lequel la lettre de refus est créée
     * @throws InvalidFormatException
     * @throws IOException 
     */
    public void replaceLettreRefus(String filename, String idDossier)throws InvalidFormatException, IOException
    {
        Dossier dossier = new DossierDAO().getById(idDossier);
        Etudiant etu = dossier.getEtudiant();
        Formation Dossierformation = dossier.getDemandeFormation();
        List<Historique> hist = dossier.getHistorique();


        Date dateActuelle=new Date();
        DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy",Locale.FRANCE);
        String date = dateForm.format(dateActuelle);
        String nom=etu.getNom();
        String prenom=etu.getPrenom();
        String adresse=etu.getAdressePostale();
        String codePostal=etu.getAdresse().getCodePostal();
        String ville=etu.getAdresse().getVille();
        String civilite ="";
        if(etu.getSexe().equals("Masculin"))
            civilite="Monsieur";
        if(etu.getSexe().equals("Feminin"))
            civilite="Madame";
        String dateCommission="";
        for(Historique h : hist){
            if(h.getAction().equals("Réunion commité"))
                dateCommission = dateForm.format(h.getDate());
        }
        String pays ="";
            if(!etu.getPays().equalsIgnoreCase("france"))
                pays = etu.getPays();

        String formation=Dossierformation.getIntitule();

        System.out.println(filename);

        String newFileName=idDossier+" Lettre refus.docx";

        File file = new File(PATH_MODELS+"/"+filename);
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        XWPFDocument doc = new XWPFDocument(fis);
        doc.write(new FileOutputStream(PATH_TARGET+"/"+newFileName));
        doc.close();
            
        doc = new XWPFDocument(OPCPackage.open(PATH_TARGET+"/"+newFileName));

        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$formation"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$formation", formation);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la formation effectue");
            }
        }
         for (XWPFParagraph p : doc.getParagraphs())
            {
                int numberOfRuns = p.getRuns().size();
                StringBuilder sb = new StringBuilder();
                for (XWPFRun r : p.getRuns())
                {
                    int pos = r.getTextPosition();
                    if(r.getText(pos) != null)
                    {
                        sb.append(r.getText(pos));
                    }
                }
                if(sb.length() > 0 && sb.toString().contains("$pays"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$pays", pays);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du pays effectue");
                }
            }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$date"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$date", date);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la date effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$civilite"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$civilite", civilite);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la civilite effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$prenom"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$prenom", prenom);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du prenom effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$nom"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$nom", nom);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du nom effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$adresse"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$adresse", adresse);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de l'adresse effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$codePostal"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$codePostal", codePostal);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du code postal effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$ville"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$ville", ville);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la ville effectue");
            }
        }
        for (XWPFParagraph p : doc.getParagraphs())
        {
            int numberOfRuns = p.getRuns().size();
            StringBuilder sb = new StringBuilder();
            for (XWPFRun r : p.getRuns())
            {
                int pos = r.getTextPosition();
                if(r.getText(pos) != null)
                {
                    sb.append(r.getText(pos));
                }
            }
            if(sb.length() > 0 && sb.toString().contains("$Commission"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$Commission", dateCommission);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la date de commission effectue");
            }
        }
        doc.write(new FileOutputStream(PATH_TARGET+"/temp.docx"));
        new File(PATH_TARGET+"/temp.docx").delete();
        doc.close();
        //copyTempToFile(filename);
        System.out.println("replaceLettreRefus DONE");
    }
}
