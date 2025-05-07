package Persistencia;

import Dominio.Restaurante;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IRestauranteDAO {
    void insertar(Restaurante restaurante);
    void insertarVarios(List<Restaurante> restaurantes);
    List<Restaurante> consultarTodos();
    List<Restaurante> consultarPorRatingMayor(double valor);
    List<Restaurante> consultarPorRangoRating(double min, double max);
    List<Restaurante> consultarPorCategoria(String categoria);
    List<Restaurante> buscarPorNombreRegex(String patron);
    List<Restaurante> buscarNombreIniciaCon(String prefijo);
    List<Restaurante> consultarPorFechaDesdeOrdenado(int anio, boolean ascendente);
    List<Restaurante> top3RestaurantesPorCategoria(String categoria);
    List<Restaurante> sinCategorias();
    Restaurante consultarRestaurantePorNombreCompleto(String nombre);
    boolean actualizarRatingPorNombre(String nombre, double nuevoRating);
    boolean agregarCategoriaSinDuplicado(String nombre, String nuevaCategoria);
    boolean aumentarRatingPorCategoria(String categoria, double incremento);
    boolean aumentarRatingPorNombreRestaurante(String nombre, double incremento);
    int agregarCategoriasDondeFalten(List<String> categorias);
    boolean actualizarNombre(String anterior, String nuevo);
    boolean actualizarCategorias(String nombre, List<String> categorias);
    boolean eliminarPorNombre(String nombre);
    int eliminarPorRatingMenorA(double limite);
    int eliminarPorCategoria(String categoria);
    int eliminarSinFechaInauguracion();
    boolean eliminarPorID(ObjectId id);
}
