package page.action.Docx;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/*
* Différents tests
*/
public class main {

	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidFormatException {
		//String[] text = DocReader.readDocxFile("Test.docx");
		//DocReader.createDoc("Hey");
		//DocReader.readDocFile("C:\\Test.doc");
		/*DocReader.replaceName("Test.docx","Dupont Jean");
		DocReader.replaceFirstname("Test.docx","Dupont");
		DocReader.replaceLastname("Test.docx","Jean");
		ConvertDocxToPDF.convert("Test.docx");*/
                String fileName;
                //fileName=DocReaderAccuseReception.replaceAccuseReception("Accuse_Reception.docx", "pst181120151");
                fileName=DocReaderLettreRefus.replaceLettreRefus("Ltrre refus_PST_test.docx", "pst181120151");
	}

}
