package Negocio;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase POJO que representa un Restaurante.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class RestauranteDTO {
    // Atributos de un restaurante.
    private String nombre;
    private LocalDate fechaInauguracion;
    private Double rating;
    private List<String> categorias;
    /**
     * Constructor por defecto.
     */
    public RestauranteDTO() {}
    /**
     * Constructor que recibe todos los atributos de
     * un restaurante.
     * @param nombre Nombre del restaurante.
     * @param fechaInauguracion Fecha de inauguración del restaurante.
     * @param rating Rating del restaurante.
     * @param categorias Categorías del restaurante.
     */
    public RestauranteDTO(
            String nombre, 
            LocalDate fechaInauguracion, 
            Double rating, 
            List<String> categorias
    ) {
        this.nombre = nombre;
        this.fechaInauguracion = fechaInauguracion;
        this.rating = rating;
        this.categorias = categorias;
    }
    /**
     * Retorna el nombre del restaurante.
     * @return Nombre del restaurante.
     */
    public String getNombre() {return nombre;}
    /**
     * Establece el nombre del restaurante.
     * @param nombre Nuevo nombre del restaurante.
     */
    public void setNombre(String nombre) {this.nombre = nombre;}
    /**
     * Retorna la fecha de inauguración del restaurante.
     * @return Fecha de inauguración del restaurante.
     */
    public LocalDate getFechaInauguracion() {return fechaInauguracion;}
    /**
     * Establece la fecha de inauguración del restaurante.
     * @param fechaInauguracion Nueva fecha de inauguración del restaurante.
     */
    public void setFechaInauguracion(LocalDate fechaInauguracion) {this.fechaInauguracion = fechaInauguracion;}
    /**
     * Retorna el rating del restaurante.
     * @return Rating del restaurante.
     */
    public Double getRating() {return rating;}
    /**
     * Establece el rating del restaurante.
     * @param rating Nuevo rating del restaurante.
     */
    public void setRating(Double rating) {this.rating = rating;}
    /**
     * Retorna la lista de categorías del restaurante.
     * @return Lista de categorías del restaurante.
     */
    public List<String> getCategorias() {return categorias;}
    /**
     * Establece la lista de categorías del restaurante.
     * @param categorias Nueva lista de categorías del restaurante.
     */
    public void setCategorias(List<String> categorias) {this.categorias = categorias;}
    /**
     * Retorna una cadena con la 
     * información relevante del restaurante.
     * @return cadena con la información relevante del restaurante.
     */
    @Override
    public String toString() {
        return "RestauranteDTO{" + "nombre=" + nombre + ", fechaInauguracion=" + fechaInauguracion + ", rating=" + rating + ", categorias=" + categorias + '}';
    }
}