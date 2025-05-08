package Persistencia;

import Conexion.Conexion;
import Dominio.Restaurante;
import com.mongodb.client.*;
import java.util.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.time.LocalDate;
import org.bson.conversions.Bson;

/**
 * Clase para operaciones CRUD con la BD de Restaurantes.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class RestauranteDAO implements IRestauranteDAO {
    // Colección de restaurantes.
    private final MongoCollection<Restaurante> restauranteCollection;

    /**
     * Constructor de la clase {@code RestauranteDAO}.Inicializa la conexión a la colección "restaurantes"
     * dentro de la base de datos especificada en MongoDB, directamente
     * mapeada a la entidad {@link Restaurante}.
     */
    public RestauranteDAO() {
        MongoDatabase database = Conexion.getDatabase();
        this.restauranteCollection = database.getCollection("restaurantes", Restaurante.class);
    }
    /**
     * Inserta cinco restaurantes.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public void insertarRestaurantesPorDefecto() throws PersistenciaException{
        // Primer restaurante de ejemplo
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNombre("Mariscos El Berna");
        restaurante1.setRating(5.0);
        restaurante1.setFechaInauguracion(LocalDate.of(2021, 8, 25));
        restaurante1.setCategorias(Arrays.asList("Mariscos", "Cerveza", "Familiar", "Al aire libre"));
        // Segundo restaurante de ejemplo
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNombre("Mariscos El Chiflas");
        restaurante2.setRating(5.0);
        restaurante2.setFechaInauguracion(LocalDate.of(2020, 6, 18));
        restaurante2.setCategorias(Arrays.asList("Mariscos", "Bebidas", "Buffet"));
        // Tercer restaurante de ejemplo
        Restaurante restaurante3 = new Restaurante();
        restaurante3.setNombre("Sushi Loco");
        restaurante3.setRating(5.0);
        restaurante3.setFechaInauguracion(LocalDate.of(1995, 2, 20));
        restaurante3.setCategorias(Arrays.asList("Sushi", "Bebidas", "Familiar"));
        // Cuarto restaurante de ejemplo
        Restaurante restaurante4 = new Restaurante();
        restaurante4.setNombre("Little Caesars");
        restaurante4.setRating(2.5);
        restaurante4.setFechaInauguracion(LocalDate.of(1984, 4, 3));
        // Quinto restaurante de ejemplo
        Restaurante restaurante5 = new Restaurante();
        restaurante5.setNombre("Tortas SQL");
        restaurante5.setRating(3.5);
        restaurante5.setFechaInauguracion(LocalDate.of(2025, 11, 12));
        // Lista de restaurantes a insertar.
        List<Restaurante> restaurantesPorDefecto = Arrays.asList(restaurante1, restaurante2, restaurante3, restaurante4, restaurante5);
        // Llama al mismo método de esta clase para insertar la lista de nuevos restaurantes.
        insertarVarios(restaurantesPorDefecto);
        
    }
    
    /**
     * Inserta un nuevo restaurante en la base de datos.
     * @param restaurante La entidad {@link Restaurante} a insertar.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public void insertar(Restaurante restaurante) throws PersistenciaException {
        try {
            restauranteCollection.insertOne(restaurante);
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }

    /**
     * Inserta varios restaurantes en la base de datos de forma masiva.
     * @param restaurantes Una lista de entidades {@link Restaurante} a insertar.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public void insertarVarios(List<Restaurante> restaurantes) throws PersistenciaException {
        try {
            restauranteCollection.insertMany(restaurantes);
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }

    /**
     * Consulta y devuelve todos los restaurantes almacenados en la base de datos.
     * @return Una lista de todas las entidades {@link Restaurante} encontradas.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public List<Restaurante> consultarTodos() throws PersistenciaException {
        try {
            return restauranteCollection.find().into(new ArrayList<>());
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }

    /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado.
     * Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public List<Restaurante> consultar(Bson filtro) throws PersistenciaException {
        try {
            return restauranteCollection.find(filtro).into(new ArrayList<>());
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
    /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado y
     * ordenado con el orden recibido.Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @param orden El objeto {@link Bson} que define los criterios del orden de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public List<Restaurante> consultarOrden(Bson filtro, Bson orden) throws PersistenciaException{
        try {
            return restauranteCollection.find(filtro).sort(orden).into(new ArrayList<>());
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
    
    /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado y
     * ordenado con el orden recibido.Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @param limite El valor {@link int} que define los criterios del límite de restaurantes obtenidos de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public List<Restaurante> consultarLimite(Bson filtro, int limite) throws PersistenciaException{
        try {
            return restauranteCollection.find(filtro).limit(limite).into(new ArrayList<>());
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
    
    /**
     * Actualiza un restaurante en la base de datos que coincide con el filtro,
     * aplicando las actualizaciones especificadas.
     *
     * @param filtro      El objeto {@link Bson} que define el restaurante a actualizar.
     * @param actualizaciones El objeto {@link Bson} que define las modificaciones a realizar.
     * @return El resultado de la operación de actualización.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public boolean actualizar(Bson filtro, Bson actualizaciones) throws PersistenciaException {
        try {
            UpdateResult insercion =  restauranteCollection.updateMany(filtro, actualizaciones);
            return insercion.wasAcknowledged();
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
    /**
     * Actualiza un restaurante en la base de datos que coincide con el filtro,
     * aplicando las actualizaciones especificadas.
     * @param filtro      El objeto {@link Bson} que define el restaurante a actualizar.
     * @param actualizaciones El objeto {@link Bson} que define las modificaciones a realizar.
     * @return La cantidad de registros actualizados de la operación.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public long actualizarConteo(Bson filtro, Bson actualizaciones) throws PersistenciaException{
        try {
            UpdateResult insercion =  restauranteCollection.updateMany(filtro, actualizaciones);
            return insercion.getModifiedCount();
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
    
    /**
     * Elimina uno o varios restaurantes de la base de datos que coinciden con el filtro proporcionado.
     * @param filtro El objeto {@link Bson} que define los criterios para la eliminación.
     * @return El resultado de la operación de eliminación.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public boolean eliminar(Bson filtro) throws PersistenciaException {
        try {
            DeleteResult eliminacion = restauranteCollection.deleteMany(filtro);
            return eliminacion.wasAcknowledged();
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
    
    /**
     * Elimina uno o varios restaurantes de la base de datos que coinciden con el filtro proporcionado.
     * @param filtro El objeto {@link Bson} que define los criterios para la eliminación.
     * @return La cantidad de registros eliminados de la operación.
     * @throws PersistenciaException Excepción de la capa de Persistencia.
     */
    @Override
    public long eliminarConteo(Bson filtro) throws PersistenciaException{
        try {
            DeleteResult eliminacion = restauranteCollection.deleteMany(filtro);
            return eliminacion.getDeletedCount();
        } catch (Exception e) {throw new PersistenciaException(e.getMessage(), e);}
    }
}