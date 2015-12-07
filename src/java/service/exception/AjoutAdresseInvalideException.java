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
public class AjoutAdresseInvalideException extends Exception{
    public enum cause{
        Adresse_Null,
        Adresse_Incomplete,
        Adresse_Existante
    }

    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    */  
    public AjoutAdresseInvalideException() {}  
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    */  
    public AjoutAdresseInvalideException(String message) {  
            super(message); 
    } 
    /** 
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutAdresseInvalideException(Throwable cause) {  
            super(cause); 
    }  
    /**
    * Crée une nouvelle instance de IDDossierInvalideException 
    * @param message Le message détaillant exception 
    * @param cause L'exception à l'origine de cette exception 
    */  
    public AjoutAdresseInvalideException(String message, Throwable cause) {  
            super(message, cause); 
    }
}
