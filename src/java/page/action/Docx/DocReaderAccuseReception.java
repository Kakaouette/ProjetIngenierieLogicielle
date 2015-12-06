package page.action.Docx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
public class DocReaderAccuseReception
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
	//$annee année de $type, mention $mention, parcours $parcours, $typeFormation.
        public static void replaceAccuseReception(String filename)throws InvalidFormatException, IOException
        {
            
        }
        
	public static void replaceName(String filename, String name) throws InvalidFormatException, IOException
	{
            XWPFDocument doc = new XWPFDocument(OPCPackage.open("./lettres/target/"+filename));
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

                if(sb.length() > 0 && sb.toString().contains("$name"))
                {
                    for(int i = numberOfRuns - 1; i > 0; i--)
                    {
                          p.removeRun(i);
                    }
                    String text = sb.toString().replace("$name", name);
                    XWPFRun run = p.getRuns().get(0);
                    run.setText(text, 0);
                    System.out.println("Changement du name effectue");
                }
            }
            doc.write(new FileOutputStream("./lettres/target/Temp.docx"));
                    doc.close();
                    copyTempToFile(filename);
                    System.out.println("replaceName DONE");
        }
	
	public static void replaceFirstname(String filename, String firstname) throws InvalidFormatException, IOException
	{
		XWPFDocument doc = new XWPFDocument(OPCPackage.open("./lettres/target/"+filename));
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
            
            if(sb.length() > 0 && sb.toString().contains("$firstname"))
            {
            	for(int i = numberOfRuns - 1; i > 0; i--)
            	{
            	      p.removeRun(i);
            	}
                String text = sb.toString().replace("$firstname", firstname);
                XWPFRun run = p.getRuns().get(0);
            	run.setText(text, 0);
                System.out.println("Changement du firstname effectue");
            }
        }
        doc.write(new FileOutputStream("./lettres/target/Temp.docx"));
		doc.close();
		copyTempToFile(filename);
		System.out.println("replaceFirstname DONE");
    }
	
	public static void replaceLastname(String filename, String lastname) throws InvalidFormatException, IOException
	{
		XWPFDocument doc = new XWPFDocument(OPCPackage.open("./lettres/target/"+filename));
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
            
            if(sb.length() > 0 && sb.toString().contains("$lastname"))
            {
            	for(int i = numberOfRuns - 1; i > 0; i--)
            	{
            	      p.removeRun(i);
            	}
                String text = sb.toString().replace("$lastname", lastname);
                XWPFRun run = p.getRuns().get(0);
            	run.setText(text, 0);
                System.out.println("Changement du lastname effectue");
            }
        }
        doc.write(new FileOutputStream("./lettres/target/Temp.docx"));
		doc.close();
		copyTempToFile(filename);
		System.out.println("replaceLastname DONE");
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
