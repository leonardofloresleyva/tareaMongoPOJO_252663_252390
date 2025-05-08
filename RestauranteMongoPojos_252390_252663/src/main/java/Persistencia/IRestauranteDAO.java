package Persistencia;

import Dominio.Restaurante;
import java.util.List;
import org.bson.conversions.Bson;

/**
 *  Interfaz para la clase RestauranteDAO.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public interface IRestauranteDAO {
    /**
     * Inserta cinco restaurantes.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public void insertarRestaurantesPorDefecto() throws PersistenciaException;
    /**
     * Inserta un nuevo restaurante en la base de datos.
     * @param restaurante La entidad {@link Restaurante} a insertar.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public void insertar(Restaurante restaurante) throws PersistenciaException;
    /**
     * Inserta varios restaurantes en la base de datos de forma masiva.
     * @param restaurantes Una lista de entidades {@link Restaurante} a insertar.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public void insertarVarios(List<Restaurante> restaurantes) throws PersistenciaException;
    /**
     * Consulta y devuelve todos los restaurantes almacenados en la base de datos.
     * @return Una lista de todas las entidades {@link Restaurante} encontradas.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public List<Restaurante> consultarTodos() throws PersistenciaException;
    /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado.
     * Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public List<Restaurante> consultar(Bson filtro) throws PersistenciaException;
     /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado y
     * ordenado con el orden recibido.Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @param orden El objeto {@link Bson} que define los criterios del orden de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public List<Restaurante> consultarOrden(Bson filtro, Bson orden) throws PersistenciaException;
    /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado y
     * ordenado con el orden recibido.Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @param limite El valor {@link int} que define los criterios del límite de restaurantes obtenidos de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public List<Restaurante> consultarLimite(Bson filtro, int limite) throws PersistenciaException;
    /**
     * Actualiza un restaurante en la base de datos que coincide con el filtro,
     * aplicando las actualizaciones especificadas.
     *
     * @param filtro      El objeto {@link Bson} que define el restaurante a actualizar.
     * @param actualizaciones El objeto {@link Bson} que define las modificaciones a realizar.
     * @return El resultado de la operación de actualización.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public boolean actualizar(Bson filtro, Bson actualizaciones) throws PersistenciaException;
     /**
     * Actualiza un restaurante en la base de datos que coincide con el filtro,
     * aplicando las actualizaciones especificadas.
     * @param filtro      El objeto {@link Bson} que define el restaurante a actualizar.
     * @param actualizaciones El objeto {@link Bson} que define las modificaciones a realizar.
     * @return La cantidad de registros actualizados de la operación.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public long actualizarConteo(Bson filtro, Bson actualizaciones) throws PersistenciaException;
    /**
     * Elimina uno o varios restaurantes de la base de datos que coinciden con el filtro proporcionado.
     * @param filtro El objeto {@link Bson} que define los criterios para la eliminación.
     * @return El resultado de la operación de eliminación.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public boolean eliminar(Bson filtro) throws PersistenciaException;
    /**
     * Elimina uno o varios restaurantes de la base de datos que coinciden con el filtro proporcionado.
     * @param filtro El objeto {@link Bson} que define los criterios para la eliminación.
     * @return La cantidad de registros eliminados de la operación.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    public long eliminarConteo(Bson filtro) throws PersistenciaException;
}
