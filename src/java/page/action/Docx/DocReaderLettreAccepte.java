package page.action.Docx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modele.dao.DossierDAO;
import modele.entite.Dossier;
import modele.entite.Formation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;



/**
 * 
 * A LIRE :
 * Ce que j'ai fait ici est le résultat de mon programme de test que j'ai pu faire.
 * Ce qu'il reste a faire:
 *  - Changer les champs a remplacer dans les lettres par des "$NomDeLaVariable"
 *  - Créer une fonction correspondant au "scénario" de remplacement d'un document
 *      (et non créer une fonction par variable comme je l'ai fait) pour ne lire qu'une seule fois
 *      le document et tout remplacer dès qu'il rencontre tel ou tel variable (s'inspirer d'une de mes
 *      fonctions en mettant plusieurs if correspondant a chacunes des variables)
 *  - Transformer tout cela en action avec comme appel des fonction les éléments de la BD
 * 
 */
public class DocReaderLettreAccepte
{
	public static String[] readDocxFile(String fileName)
        {
		try {
			File file = new File("/lettres/target/"+fileName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			XWPFDocument doc = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = doc.getParagraphs();
			List<String> paragraphsList = new ArrayList<String>();
			System.out.println("Nb total de paragraphes : "+paragraphs.size());
			for (XWPFParagraph paragraph : paragraphs) {
				paragraphsList.add(paragraph.getText());
				System.out.println(paragraph.getText());
			}
			fis.close();
			String[] paragraphStringArray = new String[paragraphsList.size()];
			paragraphStringArray = paragraphsList.toArray(paragraphStringArray);
			return paragraphStringArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
        public static String replaceLettreAccepte(String filename, String idDossier)throws InvalidFormatException, IOException
        {
           /* Dossier dossier = new DossierDAO().getById(idDossier);
            
            Formation formation = dossier.getDemandeFormation();*/
            System.out.println(filename);
            
            String date="6 decembre 2015";
            String nom="Gaunt";
            String prenom="Damian";
            String adresse="9 rue leconte";
            String codePostal="84450";
            String ville="BOUHET";
            String civilite="Monsieur";
            String dateCommission="25/06/2015";
            String formation="Master 1 Informatique ICONE en formation initial";
            String debutInscription="3 juillet 2016";
            String finInscription="30 août 2016";
            
            System.out.println(filename);
            
            String newFileName=idDossier+" Lettre refus.docx";
            
            File file = new File("./lettres/target/"+filename);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument doc = new XWPFDocument(fis);
            doc.write(new FileOutputStream("./lettres/target/"+newFileName));
            doc.close();
            
            doc = new XWPFDocument(OPCPackage.open("./lettres/target/"+newFileName));

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
                if(sb.length() > 0 && sb.toString().contains("$dateCommission"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$dateCommission", dateCommission);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du type de la formation effectue");
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
                if(sb.length() > 0 && sb.toString().contains("$finInscriptions"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$finInscriptions", finInscription);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la fin des inscriptions effectue");
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
                if(sb.length() > 0 && sb.toString().contains("debutInscriptions"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$debutInscriptions", debutInscription);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement de la date de début d'inscription effectué");
                }
            }
            doc.write(new FileOutputStream("./lettres/target/temp.docx"));
            new File("./lettres/target/temp.docx").delete();
            doc.close();
            //copyTempToFile(filename);
            System.out.println("replaceAccuseAccepte DONE");
            return newFileName;
        }
	
	public static void copyTempToFile (String filename) throws InvalidFormatException, IOException
	{
		XWPFDocument doc = new XWPFDocument(OPCPackage.open("./lettres/target/Temp.docx"));
		doc.write(new FileOutputStream("./lettres/target/"+filename));
		doc.close();
		new File("./lettres/target/Temp.docx").delete();
	}
	
	public static void createDoc(String filename) throws FileNotFoundException, IOException
	{
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.setText("This is a test file");
		tmpRun.setFontSize(12);
		document.write(new FileOutputStream(new File("./lettres/target/"+filename)));
		System.out.println("Document cree : "+filename);
	}
}
