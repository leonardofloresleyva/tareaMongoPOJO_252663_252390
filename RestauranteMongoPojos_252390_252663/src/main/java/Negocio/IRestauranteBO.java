package Negocio;

import Negocio.RestauranteDTO;
import java.util.List;

/**
 * Interfaz para la clase RestauranteBO.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public interface IRestauranteBO {
    // Inserciones
    /**
     * Inserta unos restaurantes por defecto.
     * @throws NegocioException Excepción de negocio.
     */
    public void insertarRestaurantesPorDefecto() throws NegocioException;;
    /**
     * Inserta un nuevo restaurante.
     * @param restaurante Restaurante a insertar.
     * @throws NegocioException Excepción de negocio.
     */
    public void insertar(RestauranteDTO restaurante) throws NegocioException;;
    /**
     * Inserta una lista de restaurantes.
     * @param restaurantes Lista de restaurantes a insertar.
     * @throws NegocioException Excepción de negocio.
     */
    public void insertarVarios(List<RestauranteDTO> restaurantes) throws NegocioException;;
    
    // Consultas
    /**
     * Consulta todos los restaurantes.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> consultarTodos() throws NegocioException;
    /**
     * Consulta restaurantes cuyo rating es mayor al valor recibido.
     * @param valor Rating a límite.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> consultarPorRatingMayorA(double valor) throws NegocioException;
    /**
     * Consulta restaurantes cuyo rating se encuentra dentro del rango recibido.
     * @param min Límite inferior.
     * @param max Límite superior.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> consultarPorRangoRating(double min, double max) throws NegocioException;
    /**
     *  Consulta restaurantes cuya categoría es igual a la recibida.
     * @param categoria Categoría a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> consultarPorCategoria(String categoria) throws NegocioException;
    /**
     * Consulta restaurantes cuyo nombre coincide con la expresión regular recibida.
     * @param patron Expresión regular a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> buscarPorNombreRegex(String patron) throws NegocioException;
    /**
     * Consulta restaurantes cuyo nombre comienza con el prefijo recibido.
     * @param prefijo Prefijo a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> buscarNombreIniciaCon(String prefijo) throws NegocioException;
    /**
     * Consulta restaurantes cuya fecha de inauguración está después del año recibido, y muestra
     * los resultados según el orden recibido.
     * @param anio Año a comparar.
     * @param ascendente Orden a aplicar (true = ascendente, false = descendente).
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> consultarPorFechaDesdeOrdenado(int anio, boolean ascendente) throws NegocioException;
    /**
     * Consulta restaurantes cuya categoría equivale a la recibida, y muestra solo tres resultados.
     * @param categoria Categoría a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> top3RestaurantesPorCategoria(String categoria) throws NegocioException;
    /**
     * Consulta restaurantes sin categorías.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    public List<RestauranteDTO> sinCategorias() throws NegocioException;
    /**
     * Consulta restaurantes por su nombre completo.
     * @param nombre Nombre completo del restaurante.
     * @return Restaurante encontrado.
     * @throws NegocioException Excepción de negocio.
     */
    public RestauranteDTO consultarRestaurantePorNombreCompleto(String nombre) throws NegocioException;
    
    // Actualizaciones
    /**
     * Actualiza el rating de un restaurante por su nombre.
     * @param nombre Nombre del restaurante.
     * @param nuevoRating Nuevo rating del restaurante.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean actualizarRatingPorNombre(String nombre, double nuevoRating) throws NegocioException;
    /**
     * Agrega una nueva categoría a un restaurante.
     * @param nombre Nombre del restaurante.
     * @param nuevaCategoria Nueva categoría a añadir.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean agregarCategoriaSinDuplicado(String nombre, String nuevaCategoria) throws NegocioException;
    /**
     * Incrementa el rating de uno o varios restaurantes por categoría.
     * @param categoria Categoría de los restaurantes.
     * @param incremento Valor de incremento.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean aumentarRatingPorCategoria(String categoria, double incremento) throws NegocioException;
    /**
     * Incrementa el rating de un restaurante por su nombre.
     * @param nombre Nombre del restaurante.
     * @param incremento Valor de incremento.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean aumentarRatingPorNombreRestaurante(String nombre, double incremento) throws NegocioException;
    /**
     * Agrega una lista de categorías a restaurantes que no cuentan con ninguna.
     * @param categorias Lista de categorías a añadir.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public int agregarCategoriasDondeFalten(List<String> categorias) throws NegocioException;
    /**
     * Actualiza el nombre de un restaurante.
     * @param anterior Nombre previo.
     * @param nuevo Nuevo nombre.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean actualizarNombre(String anterior, String nuevo) throws NegocioException;;
    /**
     * Actualiza las categorías de un restaurante (reemplaza las previas).
     * @param nombre Nombre del restaurante.
     * @param categorias Nuevas categorías.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean actualizarCategorias(String nombre, List<String> categorias) throws NegocioException;;
    
    // Eliminaciones
    /**
     * Elimina un restaurante por su nombre.
     * @param nombre Nombre del restaurante.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean eliminarPorNombre(String nombre) throws NegocioException;;
    /**
     * Elimina restaurantes cuyo rating es menor al valor recibido.
     * @param limite Valor límite.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public int eliminarPorRatingMenorA(double limite) throws NegocioException;;
    /**
     * Elimina restaurantes que contengan la categoría recibida.
     * @param categoria Categoría a comparar.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public int eliminarPorCategoria(String categoria) throws NegocioException;;
    /**
     * Elmina restaurantes que no tengan fecha de inauguración.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public int eliminarSinFechaInauguracion() throws NegocioException;;
    /**
     * Elimina unn restaurante por su ID.
     * @param id ID del restaurante.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    public boolean eliminarPorID(String id) throws NegocioException;;
}