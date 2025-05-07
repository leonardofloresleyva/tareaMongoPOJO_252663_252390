package Presentacion;

/**
 * Excepción de la capa de Presentación.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class PresentacionException extends Exception{
    /**
     * Constructor que recibe el mensaje de la excepción.
     * @param message Mensaje de la excepción.
     */
    public PresentacionException(String message) {super(message);}
    /**
     * Constructor que recibe el mensaje y la causa de la excepción.
     * @param message Mensaje de la excepción.
     * @param cause Causa de la excepción.
     */
    public PresentacionException(String message, Throwable cause) {super(message, cause);}
}