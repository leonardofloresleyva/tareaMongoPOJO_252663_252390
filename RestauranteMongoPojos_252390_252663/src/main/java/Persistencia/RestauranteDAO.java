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

    private final MongoCollection<Document> restauranteCollection;

    public RestauranteDAO(MongoClient mongoClient, String databaseName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        this.restauranteCollection = database.getCollection("restaurantes");
    }

    @Override
    public void insertar(Restaurante restaurante) {
        Document document = Mapper.toDocument(restaurante);
        restauranteCollection.insertOne(document);
    }

    @Override
    public void insertarVarios(List<Restaurante> restaurantes) {
        List<Document> documents = restaurantes.stream().map(Mapper::toDocument)
                .collect(Collectors.toList());
        restauranteCollection.insertMany(documents);
    }

    @Override
    public List<Restaurante> consultarTodos() {
        List<Document> documents = restauranteCollection.find().into(new ArrayList<>());
        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }

    public List<Restaurante> consultarPorRatingMayorA(double ratingMinimo) {
        Bson filtro = gt("rating", ratingMinimo);
        List<Document> documents = restauranteCollection.find(filtro).into(new ArrayList<>());
        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }

    //Falta corregir
//    public List<Restaurante> consultarPorRangoRating(double min, double max) {
//        Bson filtro = and(gte("rating", min), lte("rating", max));
//        List<Document> documents = restauranteCollection.find(filtro).into(new ArrayList<>());
//        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
//    }

    public List<Restaurante> consultarPorCategoria(String categoria) {
        Bson filtro = eq("categorias", categoria);
        List<Document> documents = restauranteCollection.find(filtro).into(new ArrayList<>());
        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }

    public List<Restaurante> consultarPorPalabraEnNombre(String palabra) {
        Bson filtro = regex("nombre", palabra, "i");
        List<Document> documents = restauranteCollection.find(filtro).into(new ArrayList<>());
        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }

    public List<Restaurante> consultarPorInicioNombre(String inicio) {
        Bson filtro = regex("nombre", "^" + inicio, "i");
        Bson proyeccion = include("nombre", "rating", "fechainauguracion");
        List<Document> documents = restauranteCollection.find(filtro).projection(proyeccion).into(new ArrayList<>());
        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }

    public List<Restaurante> consultarPorAnioInauguracionOrdenado(int anio, String orden) {
        Date fechaInicioAnio = new Date(anio - 1900, 0, 1);
        Bson filtro = gte("fechainauguracion", fechaInicioAnio);
        Bson sort = orden.equalsIgnoreCase("desc") ? Sorts.descending("fechainauguracion") : Sorts.ascending("fechainauguracion");
        List<Document> documents = restauranteCollection.find(filtro).sort(sort).into(new ArrayList<>());
        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
    }

    //Falta corregir
//    public List<Restaurante> consultarTop3SushiOMariscos() {
//        Bson filtro = and(
//                eq("rating", 5.0), or(eq("categorias", "Sushi"), 
//                        eq("categorias", "Mariscos")));
//        Bson sort = Sorts.descending("rating");
//        List<Document> documents = restauranteCollection.find(filtro).sort(sort).limit(3).into(new ArrayList<>());
//        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
//    }

    //Falta corregir
//    public List<Restaurante> consultarInauguradosDesde2020ExcluyendoCategorias() {
//        Date fechaInicio2020 = new Date(2020 - 1900, 0, 1);
//        List<Document> documents = restauranteCollection.find(
//                and(
//                        gte("fechainauguracion", fechaInicio2020),
//                        not(in("categorias", Arrays.asList("buffet", "cortes", "desayunos")))
//                )
//        ).into(new ArrayList<>());
//        return documents.stream().map(Mapper::toEntity).collect(Collectors.toList());
//    }

    public List<String> consultarNombresSinCategorias() {
        Bson filtro = eq("categorias", Collections.emptyList());
        Bson proyeccion = include("nombre");
        List<Document> documents = restauranteCollection.find(filtro).projection(proyeccion).into(new ArrayList<>());
        return documents.stream().map(doc -> doc.getString("nombre")).collect(Collectors.toList());
    }

    public boolean actualizarRatingPorNombre(String nombreRestaurante, double nuevoRating) {
        Bson filtro = eq("nombre", nombreRestaurante);
        Bson actualizacion = set("rating", nuevoRating);
        UpdateResult resultado = restauranteCollection.updateOne(filtro, actualizacion);
        return resultado.getModifiedCount() > 0;
    }

    public boolean agregarCategoriaSegura(String nombreRestaurante, String nuevaCategoria) {
        Bson filtro = eq("nombre", nombreRestaurante);
        Bson actualizacion = addToSet("categorias", nuevaCategoria);
        UpdateResult resultado = restauranteCollection.updateOne(filtro, actualizacion);
        return resultado.getModifiedCount() > 0;
    }

    public long actualizarRatingSushi() {
        Bson filtro = eq("categorias", "Sushi");
        Bson actualizacion = inc("rating", 0.2);
        UpdateResult resultado = restauranteCollection.updateMany(filtro, actualizacion);
        return resultado.getModifiedCount();
    }

    public long anadirCategoriasRestaurantesSinCategorias(List<String> categorias) {
        Bson filtro = eq("categorias", Collections.emptyList());
        Bson actualizacion = pushEach("categorias", categorias);
        UpdateResult resultado = restauranteCollection.updateMany(filtro, actualizacion);
        return resultado.getModifiedCount();
    }

    public boolean actualizarNombreRestaurante(String nombreActual, String nuevoNombre) {
        Bson filtro = eq("nombre", nombreActual);
        Bson actualizacion = set("nombre", nuevoNombre);
        UpdateResult resultado = restauranteCollection.updateOne(filtro, actualizacion);
        return resultado.getModifiedCount() > 0;
    }

    public boolean actualizarCategoriasRestaurante(String nombreRestaurante, List<String> nuevasCategorias) {
        Bson filtro = eq("nombre", nombreRestaurante);
        Bson actualizacion = set("categorias", nuevasCategorias);
        UpdateResult resultado = restauranteCollection.updateOne(filtro, actualizacion);
        return resultado.getModifiedCount() > 0;
    }

    public long eliminarRestaurantePorNombre(String nombreRestaurante) {
        Bson filtro = eq("nombre", nombreRestaurante);
        DeleteResult resultado = restauranteCollection.deleteOne(filtro);
        return resultado.getDeletedCount();
    }

    public long eliminarRestaurantesPorRatingMenorA(double ratingMinimo) {
        Bson filtro = lt("rating", ratingMinimo);
        DeleteResult resultado = restauranteCollection.deleteMany(filtro);
        return resultado.getDeletedCount();
    }

    public long eliminarRestaurantesPorCategoriaEspecifica(String categoria) {
        Bson filtro = eq("categorias", categoria);
        DeleteResult resultado = restauranteCollection.deleteMany(filtro);
        return resultado.getDeletedCount();
    }

    public long eliminarRestaurantesSinFechaInauguracion() {
        Bson filtro = eq("fechainauguracion", null);
        DeleteResult resultado = restauranteCollection.deleteMany(filtro);
        return resultado.getDeletedCount();
    }

    public boolean insertarUnDocumento(Restaurante restaurante) {
        try {
            Document document = Mapper.toDocument(restaurante);
            restauranteCollection.insertOne(document);
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar documento: " + e.getMessage());
            return false;
        }
    }

    public boolean insertarMuchosDocumentos(List<Restaurante> restaurantes) {
        try {
            List<Document> documents = restaurantes.stream().map(Mapper::toDocument).collect(Collectors.toList());
            restauranteCollection.insertMany(documents);
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar documentos: " + e.getMessage());
            return false;
        }
    }

    // Método para insertar los restaurantes iniciales (adaptado)
    public void insertarRestaurantesIniciales() {
        List<Document> initialDocuments = Arrays.asList(
                new Document("nombre", "Sushi Master")
                        .append("fechainauguracion", new Date(121, 2, 15)) // Año se resta de 1900
                        .append("rating", 5.0)
                        .append("categorias", Arrays.asList("Sushi", "Japonesa", "Mariscos", "Alta Cocina")),
                new Document("nombre", "Taco Rico")
                        .append("fechainauguracion", new Date(119, 7, 20))
                        .append("rating", 4.5)
                        .append("categorias", Arrays.asList("Mexicana", "Tacos")),
                new Document("nombre", "Green Garden")
                        .append("fechainauguracion", new Date(118, 3, 12))
                        .append("rating", 4.2)
                        .append("categorias", Arrays.asList("Vegetariana", "Orgánica")),
                new Document("nombre", "Café Central")
                        .append("fechainauguracion", LocalDate.parse("2017-08-05")) // Mejor usar LocalDate y que el Mapper lo convierta
                        .append("rating", 3.8)
                        .append("categorias", Collections.emptyList()),
                new Document("nombre", "Delicias")
                        .append("fechainauguracion", LocalDate.parse("2016-01-11"))
                        .append("rating", 4.0)
                        .append("categorias", Collections.emptyList())
        );
        restauranteCollection.insertMany(initialDocuments);
        System.out.println("Restaurantes iniciales insertados.");
    }

    @Override
    public List<Restaurante> consultarPorRatingMayor(double valor) {
        Bson filtro = gt("rating", valor);
        return restauranteCollection.find(filtro).map(Mapper::toEntity).into(new ArrayList<>());
    }

    @Override
    public List<Restaurante> buscarPorNombreRegex(String patron) {
        Bson filtro = regex("nombre", patron);
        return restauranteCollection.find(filtro).map(Mapper::toEntity).into(new ArrayList<>());
    }

    @Override
    public List<Restaurante> buscarNombreIniciaCon(String prefijo) {
        Bson filtro = regex("nombre", "^" + Pattern.quote(prefijo), "i");
        return restauranteCollection.find(filtro).map(Mapper::toEntity).into(new ArrayList<>());
    }

    @Override
    public List<Restaurante> consultarPorFechaDesdeOrdenado(int anio, boolean ascendente) {
        LocalDate fechaInicioAnio = LocalDate.ofYearDay(anio, 1);
        Date fechaInicioAnioDate = Date.from(fechaInicioAnio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Bson filtro = gte("fechaInauguracion", fechaInicioAnioDate);
        Bson sort = ascendente ? Sorts.ascending("fechaInauguracion") : Sorts.descending("fechaInauguracion");
        return restauranteCollection.find(filtro).sort(sort).map(Mapper::toEntity).into(new ArrayList<>());
    }

    @Override
    public List<Restaurante> top3RestaurantesPorCategoria(String categoria) {
        Bson filtro = eq("categorias", categoria);
        Bson sort = Sorts.descending("rating");
        return restauranteCollection.find(filtro).sort(sort).limit(3).map(Mapper::toEntity).into(new ArrayList<>());
    }

    @Override
    public List<Restaurante> sinCategorias() {
        Bson filtro = Filters.size("categorias", 0);
        return restauranteCollection.find(filtro).map(Mapper::toEntity).into(new ArrayList<>());
    }

    @Override
    public Restaurante consultarRestaurantePorNombreCompleto(String nombre) {
        Bson filtro = eq("nombre", nombre);
        Document document = restauranteCollection.find(filtro).first();
        return document != null ? Mapper.toEntity(document) : null;
    }

    @Override
    public boolean agregarCategoriaSinDuplicado(String nombre, String nuevaCategoria) {
        Bson filtro = eq("nombre", nombre);
        Bson update = addToSet("categorias", nuevaCategoria);
        UpdateResult result = restauranteCollection.updateOne(filtro, update);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean aumentarRatingPorCategoria(String categoria, double incremento) {
        Bson filtro = eq("categorias", categoria);
        Bson update = inc("rating", incremento);
        UpdateResult result = restauranteCollection.updateMany(filtro, update);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean aumentarRatingPorNombreRestaurante(String nombre, double incremento) {
        Bson filtro = eq("nombre", nombre);
        Bson update = inc("rating", incremento);
        UpdateResult result = restauranteCollection.updateOne(filtro, update);
        return result.getModifiedCount() > 0;
    }

    @Override
    public int agregarCategoriasDondeFalten(List<String> categorias) {
        Bson filtro = Filters.size("categorias", 0);
        Bson update = pushEach("categorias", categorias);
        UpdateResult result = restauranteCollection.updateMany(filtro, update);
        return (int) result.getModifiedCount();
    }

    @Override
    public boolean actualizarNombre(String anterior, String nuevo) {
        Bson filtro = eq("nombre", anterior);
        Bson update = set("nombre", nuevo);
        UpdateResult result = restauranteCollection.updateOne(filtro, update);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean actualizarCategorias(String nombre, List<String> categorias) {
        Bson filtro = eq("nombre", nombre);
        Bson update = set("categorias", categorias);
        UpdateResult result = restauranteCollection.updateOne(filtro, update);
        return result.getModifiedCount() > 0;
    }

    @Override
    public boolean eliminarPorNombre(String nombre) {
        Bson filtro = eq("nombre", nombre);
        DeleteResult result = restauranteCollection.deleteOne(filtro);
        return result.getDeletedCount() > 0;
    }

    @Override
    public int eliminarPorRatingMenorA(double limite) {
        Bson filtro = lt("rating", limite);
        DeleteResult result = restauranteCollection.deleteMany(filtro);
        return (int) result.getDeletedCount();
    }

    @Override
    public int eliminarPorCategoria(String categoria) {
        Bson filtro = eq("categorias", categoria);
        DeleteResult result = restauranteCollection.deleteMany(filtro);
        return (int) result.getDeletedCount();
    }

    @Override
    public int eliminarSinFechaInauguracion() {
        Bson filtro = eq("fechaInauguracion", null);
        DeleteResult result = restauranteCollection.deleteMany(filtro);
        return (int) result.getDeletedCount();
    }
    
    /**
     * Elimina un restaurante específico por su ID.
     * Utiliza el filtro {@code $eq} para encontrar el documento por su "_id"
     * y el método {@code deleteOne} para eliminarlo.
     *
     * @param id
     * El ID del restaurante a eliminar. Debe ser una instancia de {@link ObjectId}.
     * @return {@code true} si la eliminación fue exitosa (se eliminó un documento), {@code false} en caso contrario.
     */
    @Override
    public boolean eliminarPorID(ObjectId id) {
        Bson filtro = eq("_id", id);
        DeleteResult result = restauranteCollection.deleteOne(filtro);
        return result.getDeletedCount() > 0;
    }
}