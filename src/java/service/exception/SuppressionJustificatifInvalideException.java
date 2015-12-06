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
public class SuppressionJustificatifInvalideException extends Exception{  
    public enum cause{
        Justificatif_Null,
        Justificatif_Inexistant
    }
    
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public SuppressionJustificatifInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public SuppressionJustificatifInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public SuppressionJustificatifInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public SuppressionJustificatifInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
