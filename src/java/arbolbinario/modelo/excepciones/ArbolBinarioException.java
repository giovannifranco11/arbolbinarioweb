/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolbinario.modelo.excepciones;

/**
 *
 * @author carloaiza
 */
public class ArbolBinarioException extends Exception{

    public ArbolBinarioException() {
    }

    public ArbolBinarioException(String message) {
        super(message);
    }

    public ArbolBinarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArbolBinarioException(Throwable cause) {
        super(cause);
    }
    
    
}
