package Persistencia;

/**
 * Excepción de la capa de Persistencia.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class PersistenciaException extends Exception{
    /**
     * Constructor que recibe el mensaje de la excepción.
     * @param message Mensaje de la excepción.
     */
    public PersistenciaException(String message){super(message);}
    /**
     * Constructor que recibe el mensaje y la causa de la excepción.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public PersistenciaException(String message, Throwable cause) {super(message, cause);}
}