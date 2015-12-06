/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.exception;

/**
 *
 * @author Arthur
 */
public class AjoutJustificatifInvalideException extends Exception{  
    public enum cause{
        Justificatif_Null,
        Justificatif_Incomplet,
        Justificatif_Existant
    }
    
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public AjoutJustificatifInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public AjoutJustificatifInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutJustificatifInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutJustificatifInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
