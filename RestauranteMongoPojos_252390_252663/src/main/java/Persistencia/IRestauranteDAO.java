package Persistencia;

import Dominio.Restaurante;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.List;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author ErnestoLpz_252663
 */
public interface IRestauranteDAO {
    public void insertar(Restaurante restaurante);
    public void insertarVarios(List<Restaurante> restaurantes);

    public List<Restaurante> consultarTodos();

    public List<Restaurante> consultar(Bson filtro);

    public UpdateResult actualizar(Bson filtro, Bson actualizaciones);

    public DeleteResult eliminar(Bson filtro);

    boolean eliminarPorID(ObjectId id);

    public Restaurante consultarRestaurantePorNombreCompleto(String nombre);
}
