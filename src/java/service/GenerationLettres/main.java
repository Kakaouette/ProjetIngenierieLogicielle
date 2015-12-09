package service.GenerationLettres;

import service.GenerationLettres.CreerLettreAccepteService;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/*
* Différents tests
*/
public class main {

	public static void main(String[] args) throws FileNotFoundException, IOException, InvalidFormatException {
            CreerLettreAuditionService.replaceLettreAudition("Ltrre AUDITIONS Master 2 _PSTSPE_OK.docx", "pst181120151");
	}

}
