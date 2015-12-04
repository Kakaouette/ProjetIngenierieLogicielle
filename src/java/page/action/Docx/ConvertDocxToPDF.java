package page.action.Docx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/*
* Classe permettant de transformer un docx en PDF
*
*/
public class ConvertDocxToPDF
{
    public static void convert(String filename)
    {
        long startTime = System.currentTimeMillis();
        try
        {
            XWPFDocument document = new XWPFDocument(OPCPackage.open("./lettres/target/"+filename));
            File outFile = new File("./lettres/target/"+filename.replace(".docx", "")+".pdf");
            OutputStream out = new FileOutputStream( outFile );
            PdfOptions options = null;// PDFViaITextOptions.create().fontEncoding( "windows-1250" );
            PdfConverter.getInstance().convert( document, out, options );
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
        }
        System.out.println("Generate "+filename.replace(".docx", "")+".pdf with "+(System.currentTimeMillis()-startTime)+" ms.");
    }
}
