package Persistencia;

import Dominio.Restaurante;
import Negocio.Mapper;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import java.util.*;
import static com.mongodb.client.model.Projections.include;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static javax.management.Query.and;
import static javax.management.Query.or;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author ErnestoLpz_252663
 */
public class RestauranteDAO implements IRestauranteDAO {

    private final MongoCollection<Restaurante> restauranteCollection;

    /**
     * Constructor de la clase {@code RestauranteDAO}.
     * Inicializa la conexión a la colección "restaurantes"
     * dentro de la base de datos especificada en MongoDB, directamente
     * mapeada a la entidad {@link Restaurante}.
     *
     * @param mongoClient  Instancia del cliente de MongoDB.
     * @param databaseName Nombre de la base de datos a utilizar.
     */
    public RestauranteDAO(MongoClient mongoClient, String databaseName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.restauranteCollection = database.getCollection("restaurantes", Restaurante.class);
    }

    /**
     * Inserta un nuevo restaurante en la base de datos.
     *
     * @param restaurante La entidad {@link Restaurante} a insertar.
     */
    @Override
    public void insertar(Restaurante restaurante) {
        restauranteCollection.insertOne(restaurante);
    }

    /**
     * Inserta varios restaurantes en la base de datos de forma masiva.
     *
     * @param restaurantes Una lista de entidades {@link Restaurante} a insertar.
     */
    public void insertarVarios(List<Restaurante> restaurantes) {
        restauranteCollection.insertMany(restaurantes);
    }

    /**
     * Consulta y devuelve todos los restaurantes almacenados en la base de datos.
     *
     * @return Una lista de todas las entidades {@link Restaurante} encontradas.
     */
    public List<Restaurante> consultarTodos() {
        return restauranteCollection.find().into(new ArrayList<>());
    }

    /**
     * Consulta y devuelve una lista de restaurantes que coinciden con el filtro proporcionado.
     * Este método genérico permite realizar diversas consultas basadas en diferentes criterios.
     *
     * @param filtro El objeto {@link Bson} que define los criterios de la consulta.
     * @return Una lista de entidades {@link Restaurante} que cumplen con el filtro.
     */
    public List<Restaurante> consultar(Bson filtro) {
        return restauranteCollection.find(filtro).into(new ArrayList<>());
    }

    /**
     * Actualiza un restaurante en la base de datos que coincide con el filtro,
     * aplicando las actualizaciones especificadas.
     *
     * @param filtro      El objeto {@link Bson} que define el restaurante a actualizar.
     * @param actualizaciones El objeto {@link Bson} que define las modificaciones a realizar.
     * @return El resultado de la operación de actualización.
     */
    public UpdateResult actualizar(Bson filtro, Bson actualizaciones) {
        return restauranteCollection.updateMany(filtro, actualizaciones);
    }

    /**
     * Elimina uno o varios restaurantes de la base de datos que coinciden con el filtro proporcionado.
     *
     * @param filtro El objeto {@link Bson} que define los criterios para la eliminación.
     * @return El resultado de la operación de eliminación.
     */
    public DeleteResult eliminar(Bson filtro) {
        return restauranteCollection.deleteMany(filtro);
    }

    /**
     * Elimina un restaurante de la base de datos por su ID.
     *
     * @param id El {@link ObjectId} del restaurante a eliminar.
     * @return El resultado de la operación de eliminación (para verificar si se eliminó algún documento).
     */
    public boolean eliminarPorID(ObjectId id) {
        return restauranteCollection.deleteOne(eq("_id", id)).getDeletedCount() > 0;
    }

    /**
     * Consulta y devuelve un restaurante específico por su nombre completo.
     *
     * @param nombre El nombre completo del restaurante a buscar.
     * @return La entidad {@link Restaurante} cuyo nombre coincide, o {@code null} si no se encuentra.
     */
    public Restaurante consultarRestaurantePorNombreCompleto(String nombre) {
        return restauranteCollection.find(eq("nombre", nombre)).first();
    }
}