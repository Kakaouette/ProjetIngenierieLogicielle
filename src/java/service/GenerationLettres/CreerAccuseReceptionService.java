package service.GenerationLettres;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Formation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import page.action.dossier.AfficherInformationsDossiersAction;



/**
 * 
 * @author Drapeau, Chasseloup, Giguère
 * @version 3
 */
public class CreerAccuseReceptionService
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
     * @param filename - Nom du fichier modèle de l'accusé de récéption.
     * @param idDossier - Identifiant du dossier pour lequel l'accusé de récéption est créé
     * @throws InvalidFormatException
     * @throws IOException 
     */
    public void replaceAccuseReception(String filename, String idDossier)throws InvalidFormatException, IOException
    {
        Dossier dossier = new DossierDAO().getById(idDossier);

        Formation formation = dossier.getDemandeFormation();

        String annee;
        if(formation.getIntitule().contains("1"))
        {
            annee="1ère";
        }
        else if(formation.getIntitule().contains("2"))
        {
            annee="2ème";
        }
        else if(formation.getIntitule().contains("3"))
        {
            annee="3ème";
        }
        else
        {
            annee="1ère";
            System.out.println("Erreur pour l'année, fonction replaceAccuseReception");
        }

        String type;
        if(formation.getDescription().contains("Licence"))
        {
            type="licence";
        }
        else if(formation.getDescription().contains("Master"))
        {
            type="master";
        }
        else
        {
            type="master";
            System.out.println("Erreur pour le type, fonction replaceAccuseReception");
        }

        String mention=formation.getDescription();

        String parcours=formation.getIntitule();

        String typeFormation="formation initiale"; 

        //String annee="1ère";String type="master";String mention="Informatique";String parcours="ICONE";String typeFormation="formation initiale";
        System.out.println(filename);

        String newFileName=idDossier+" Accuse De Reception.docx";
        
        /*File temp = new File(PATH_MODELS+"/"+filename);
        System.out.println(temp.getAbsolutePath());*/
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(PATH_MODELS+"/"+filename));
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

            if(sb.length() > 0 && sb.toString().contains("$annee"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$annee", annee);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de l'annee effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$type"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$type", type);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du type effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$mention"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$mention", mention);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement de la mention effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$parcours"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$parcours", parcours);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du parcours effectue");
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
            if(sb.length() > 0 && sb.toString().contains("$formation"))
            {
                for(int i = numberOfRuns - 1; i > 0; i--)
                {
                      p.removeRun(i);
                }
                String text = sb.toString().replace("$formation", typeFormation);
                XWPFRun run = p.getRuns().get(0);
                run.setText(text, 0);
                System.out.println("Changement du type de la formation effectue");
            }
        }
        doc.write(new FileOutputStream(PATH_TARGET+"/temp.docx"));
        new File(PATH_TARGET+"/temp.docx").delete();
        doc.close();
        //copyTempToFile(filename);
        System.out.println("replaceAccuseReception DONE");
    }
}
