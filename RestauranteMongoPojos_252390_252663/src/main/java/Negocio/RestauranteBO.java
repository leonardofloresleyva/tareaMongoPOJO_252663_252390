package Negocio;

import Dominio.Restaurante;
import Persistencia.IRestauranteDAO;
import Persistencia.PersistenciaException;
import Persistencia.RestauranteDAO;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 * Clase de Negocio RestauranteBO.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class RestauranteBO implements IRestauranteBO{
    // Atributo estático de la clase.
    private static RestauranteBO instance;
    // Atributo DAO para operaciones con la BD.
    private final IRestauranteDAO restauranteDAO;
    /**
     * Contructor por defecto.
     */
    private RestauranteBO(){restauranteDAO = new RestauranteDAO();}
    /**
     * Retorna la instancia SingleTon de la clase.
     * Si la instancia aún no existe, se crea.
     * @return Instancia de la clase.
     */
    public synchronized static RestauranteBO getInstance(){
        if(instance == null)
            instance = new RestauranteBO();
        return instance;
    }
    /**
     * Inserta unos restaurantes por defecto.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public void insertarRestaurantesPorDefecto() throws NegocioException {
        try {
            restauranteDAO.insertarRestaurantesPorDefecto();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Ha ocurrido un error al insertar los restaurantes por defecto;");}
    }
    /**
     * Inserta un nuevo restaurante.
     * @param restaurante Restaurante a insertar.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public void insertar(RestauranteDTO restaurante) throws NegocioException {
        // Valida el nuevo restaurante.
        validarRestaurante(restaurante);
        // Mapea el nuevo restaurante.
        Restaurante restauranteInsertar = Mapper.toEntity(restaurante);
        try {
            // Inserta el nuevo restaurante.
            restauranteDAO.insertar(restauranteInsertar);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al insertar el restaurante " + restaurante.getNombre() + ".");
        }
    }
    /**
     * Inserta una lista de restaurantes.
     * @param restaurantes Lista de restaurantes a insertar.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public void insertarVarios(List<RestauranteDTO> restaurantes) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<Restaurante> restaurantesInsertar = new ArrayList<>();
        // Valida cada restaurante y lo agrega a la lista de restaurantes.
        for(RestauranteDTO restaurante: restaurantes){
            validarRestaurante(restaurante); // Valida el restaurante.
            restaurantesInsertar.add(Mapper.toEntity(restaurante)); // Mapea el restaurante y lo añade a la lista.
        }
        try {
            // Inserta los nuevos restaurantes.
            restauranteDAO.insertarVarios(restaurantesInsertar);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Ha ocurrido un error al insertar la lista de restaurantes.");
        }
    }
    /**
     * Consulta todos los restaurantes.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> consultarTodos() throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultarTodos();
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar todos los restaurantes.");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes cuyo rating es mayor al valor recibido.
     * @param valor Rating a límite.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> consultarPorRatingMayorA(double valor) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes cuyo rating es mayor al valor recibido en el parámetro.
        Bson filtro = Filters.gt("rating", valor);
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtro);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes con rating mayor a " + valor + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes cuyo rating se encuentra dentro del rango recibido.
     * @param min Límite inferior.
     * @param max Límite superior.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> consultarPorRangoRating(double min, double max) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para el límite inferior, recibido en el parámetro.
        Bson filtroMin = Filters.gte("rating", min);
        // Filtro para el límite superior, recibido en el parámetro.
        Bson filtroMax = Filters.lte("rating", max);
        // Filtro para establecer el rango (rating entre ambos límites).
        Bson filtroRango = Filters.and(filtroMin, filtroMax);
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtroRango);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes con rating entre " + min + " y " + max + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     *  Consulta restaurantes cuya categoría es igual a la recibida.
     * @param categoria Categoría a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> consultarPorCategoria(String categoria) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes que tengan la categoría recibida, usando una expresión regular.
        Bson filtroCategoria = Filters.regex("categorias", "^" + categoria + "$", "i");
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtroCategoria);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes con la categoria " + categoria + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes cuyo nombre coincide con la expresión regular recibida.
     * @param patron Expresión regular a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> buscarPorNombreRegex(String patron) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes cuyo nombre coincide con la expresión regular recibida.
        Bson filtroRegex = Filters.regex("nombre", patron);
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtroRegex);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes cuyo nombre coincide con el patron recibido.");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes cuyo nombre comienza con el prefijo recibido.
     * @param prefijo Prefijo a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> buscarNombreIniciaCon(String prefijo) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes que empiezan con el prefijo recibido.
        // El caracter "^" representa el inicio de una línea.
        // La cobinación ".*" representa cualquier caracter, cero o más veces.
        Bson filtroPrefijo = Filters.regex("nombre", "^" + prefijo + ".*");
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtroPrefijo);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes cuyo nombre inicia con ." + prefijo + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes cuya fecha de inauguración está después del año recibido, y muestra
     * los resultados según el orden recibido.
     * @param anio Año a comparar.
     * @param ascendente Orden a aplicar (true = ascendente, false = descendente).
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> consultarPorFechaDesdeOrdenado(int anio, boolean ascendente) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes inaugurados a partir del año recibido como parámetro.
        Bson filtroFecha = Filters.gt("fechaInauguracion", LocalDate.ofYearDay(anio, 1));
        // Ordenamiento a ser aplicado en la consulta. Ascendente por default.
        Bson orden = Sorts.ascending("fechaInauguracion");
        // Si el orden recibido es igual a false, se establece un ordenamiento descendente.
        if(ascendente == false)
            orden = Sorts.descending("fechaInauguracion");
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultarOrden(filtroFecha, orden);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes cuya "
                    + "fecha de inauguracion esta después del anio " + anio + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes cuya categoría equivale a la recibida, y muestra solo tres resultados.
     * @param categoria Categoría a comparar.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> top3RestaurantesPorCategoria(String categoria) throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes que tengan la categoría recibida, usando una expresión regular.
        Bson filtroCategoria = Filters.regex("categorias", "^" + categoria + "$", "i");
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultarLimite(filtroCategoria, 3);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar el top 3 de restaurantes con la categoria " + categoria + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes sin categorías.
     * @return Lista con restaurantes encontrados.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public List<RestauranteDTO> sinCategorias() throws NegocioException {
        // Lista de restaurantes encontrados.
        List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
        // Filtro para obtener restaurantes sin categorías.
        Bson filtroSinCategorias = Filters.exists("categorias", false);
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtroSinCategorias);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                for(Restaurante restaurante : restaurantesEncontradosDAO)
                    restaurantesEncontrados.add(Mapper.toDTO(restaurante));
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar restaurantes sin categorias.");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restaurantesEncontrados;
    }
    /**
     * Consulta restaurantes por su nombre completo.
     * @param nombre Nombre completo del restaurante.
     * @return Restaurante encontrado.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public RestauranteDTO consultarRestaurantePorNombreCompleto(String nombre) throws NegocioException {
        // Lista de restaurantes encontrados.
        RestauranteDTO restauranteEncontrado = new RestauranteDTO();
        // Filtro para obtener un restaurante cuyo nombre sea igual al nombre recibido en el parámetro. 
        Bson filtroNombre = Filters.eq("nombre", nombre);
        try {
            // Ejecuta la consulta.
            List<Restaurante> restaurantesEncontradosDAO = restauranteDAO.consultar(filtroNombre);
            // Mapea cada restaurante encontrado y lo añade a la lista de restaurantes encontrados.
            if(restaurantesEncontradosDAO != null && !restaurantesEncontradosDAO.isEmpty()){
                restauranteEncontrado = Mapper.toDTO(restaurantesEncontradosDAO.getFirst());
            }
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar consultar el restaurante " + nombre + ".");
        }
        // Regresa la lista de restaurantes obtenidos.
        return restauranteEncontrado;
    }
     /**
     * Actualiza el rating de un restaurante por su nombre.
     * @param nombre Nombre del restaurante.
     * @param nuevoRating Nuevo rating del restaurante.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean actualizarRatingPorNombre(String nombre, double nuevoRating) throws NegocioException {
        // Filtro para obtener un restaurante cuyo nombre sea igual al nombre recibido en el parámetro. 
        Bson filtroNombre = Filters.eq("nombre", nombre);
        // Actualiza el rating del restaurante encontrado al valor recibido en el parámetro.
        Bson actualizacion = Updates.set("rating", nuevoRating);
        try {
            // Ejecuta la actualización.
            return restauranteDAO.actualizar(filtroNombre, actualizacion);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar actualizar el rating del restaurante " + nombre + ".");
        }
    }
    /**
     * Agrega una nueva categoría a un restaurante.
     * @param nombre Nombre del restaurante.
     * @param nuevaCategoria Nueva categoría a añadir.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean agregarCategoriaSinDuplicado(String nombre, String nuevaCategoria) throws NegocioException {
        // Filtro para obtener un restaurante cuyo nombre sea igual al recibido en el parámetro.
        Bson filtroNombre = Filters.eq("nombre", nombre);
        // Filtro para obtener restaurantes que tengan la categoría recibida, usando una expresión regular.
        Bson filtroCategoria = Filters.regex("categorias", "^" + nuevaCategoria + "$", "i");
        // Filtro para obtener restaurantes que no tengan la categoría recibida, negando el filtro anterior.
        Bson filtroNoCategoria = Filters.not(filtroCategoria);
        // Filtro que garantiza que se apliquen los dos filtros anteriores.
        Bson filtroNombreNoCategoria = Filters.and(filtroNombre, filtroNoCategoria);
        // Agrega la nueva categoría al restaurante encontrado.
        Bson agregarCategoria = Updates.addToSet("categorias", nuevaCategoria);
        try {
            // Ejecuta la actualización.
            return restauranteDAO.actualizar(filtroNombreNoCategoria, agregarCategoria);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar agregar la categoria " + nuevaCategoria + " al restaurante " + nombre + ".");
        }
    }
    /**
     * Incrementa el rating de uno o varios restaurantes por categoría.
     * @param categoria Categoría de los restaurantes.
     * @param incremento Valor de incremento.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean aumentarRatingPorCategoria(String categoria, double incremento) throws NegocioException {
        // Filtro para obtener restaurantes que tengan la categoría "Sushi", usando una expresión regular.
        Bson filtroCategoria = Filters.regex("categorias", "^" + categoria + "$", "i");
        // Incrementa el rating de los restaurantes encontrados al incremento recibido.
        Bson incrementar = Updates.inc("rating", incremento);
        try {
            // Ejecuta la actualización.
            return restauranteDAO.actualizar(filtroCategoria, incrementar);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar incrementar el rating en " + incremento + " a los restaurantes de categoria " + categoria + ".");
        }
    }
    /**
     * Incrementa el rating de un restaurante por su nombre.
     * @param nombre Nombre del restaurante.
     * @param incremento Valor de incremento.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean aumentarRatingPorNombreRestaurante(String nombre, double incremento) throws NegocioException {
        // Filtro para obtener un restaurante cuyo nombre sea igual al nombre recibido en el parámetro. 
        Bson filtroNombre = Filters.eq("nombre", nombre);
        // Incrementa el rating de los restaurantes encontrados al incremento recibido.
        Bson incrementar = Updates.inc("rating", incremento);
        try {
            // Ejecuta la actualización.
            return restauranteDAO.actualizar(filtroNombre, incrementar);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar incrementar el rating en " + incremento + " al restaurante  " + nombre + ".");
        }
    }
    /**
     * Agrega una lista de categorías a restaurantes que no cuentan con ninguna.
     * @param categorias Lista de categorías a añadir.
     * @return Número de actualizaciones exitosas.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public int agregarCategoriasDondeFalten(List<String> categorias) throws NegocioException {
        // Filtro para obtener restaurantes sin categorías.
        Bson filtroSinCategorias = Filters.exists("categorias", false);
        // Actualización para agregar las nuevas categorías recibidas.
        Bson agregarCategorias = Updates.set("categorias", categorias);
        try {
            // Ejecuta la actualización.
            long resultado = restauranteDAO.actualizarConteo(filtroSinCategorias, agregarCategorias);
            // Se asegura de que el resultado no sea mayor que la capacidad de un int
            if(resultado > Integer.MAX_VALUE)
                throw new NegocioException("El valor del resultado es demasiado grande. No sé por qué devuelve int si el método Result devuelve long, o sea.");
            else
                // Se castea el resultado a int y se retorna.
                return (int) resultado;
            
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar agregar las nuevas categorías a restaurantes sin categorias.");
        }
    }
    /**
     * Actualiza el nombre de un restaurante.
     * @param anterior Nombre previo.
     * @param nuevo Nuevo nombre.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean actualizarNombre(String anterior, String nuevo) throws NegocioException {
         // Filtro para obtener un restaurante cuyo nombre sea igual al nombre antigüo recibido en el parámetro.
        Bson filtroNombre = Filters.eq("nombre", anterior);
        // Actualización para establecer el nombre del restaurante al nuevo nombre recibido en el parámetro.
        Bson cambiarNombre = Updates.set("nombre", nuevo);
        try {
            // Ejecuta la actualización.
            return restauranteDAO.actualizar(filtroNombre, cambiarNombre);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar actualizar el nombre a " + nuevo + " del restaurante " + anterior + ".");
        }
    }
    /**
     * Actualiza las categorías de un restaurante (reemplaza las previas).
     * @param nombre Nombre del restaurante.
     * @param categorias Nuevas categorías.
     * @return VERDARERO si la actualización fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean actualizarCategorias(String nombre, List<String> categorias) throws NegocioException {
        // Filtro para obtener un restaurante cuyo nombre sea igual al nombre recibido en el parámetro. 
        Bson filtroNombre = Filters.eq("nombre", nombre);
        // Actualización que establece las categorías del restaurante obtenido por las nuevas categorías recibidas en el parámetro.
        Bson cambiarCategorias = Updates.set("categorias", categorias);
        try {
            // Ejecuta la actualización.
            return restauranteDAO.actualizar(filtroNombre, cambiarCategorias);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar actualizar las categorias del restaurante " + nombre + ".");
        }
    }
    /**
     * Elimina un restaurante por su nombre.
     * @param nombre Nombre del restaurante.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean eliminarPorNombre(String nombre) throws NegocioException {
        // Filtro para obtener un restaurante cuyo nombre sea igual al nombre recibido en el parámetro. 
        Bson filtroNombre = Filters.eq("nombre", nombre);
        try {
            // Ejecuta la eliminación.
            return restauranteDAO.eliminar(filtroNombre);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar eliminar el restaurante " + nombre + ".");
        }
    }
    /**
     * Elimina restaurantes cuyo rating es menor al valor recibido.
     * @param limite Valor límite.
     * @return Número de eliminaciones exitosas.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public int eliminarPorRatingMenorA(double limite) throws NegocioException {
        // Filtro para eliminar todos los restaurantes cuyo rating sea menor que el valor recibido en el parámetro.
        Bson filtroRating = Filters.lt("rating", limite);
        try {
            // Ejecuta la eliminación.
            long resultado = restauranteDAO.eliminarConteo(filtroRating);
            // Se asegura de que el resultado no sea mayor que la capacidad de un int
            if(resultado > Integer.MAX_VALUE)
                throw new NegocioException("El valor del resultado es demasiado grande. No sé por qué devuelve int si el método Result devuelve long, o sea.");
            else
                // Se castea el resultado a int y se retorna.
                return (int) resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar eliminar restaurantes con rating menor a " + limite + ".");
        }
    }
    /**
     * Elimina restaurantes que contengan la categoría recibida.
     * @param categoria Categoría a comparar.
     * @return Número de eliminaciones exitosas.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public int eliminarPorCategoria(String categoria) throws NegocioException {
        // Filtro para obtener restaurantes que tengan la categoría recibida en el parámetro, usando una expresión regular.
        Bson filtroCategoria = Filters.regex("categorias", "^" + categoria + "$", "i");
        try {
            // Ejecuta la eliminación.
            long resultado = restauranteDAO.eliminarConteo(filtroCategoria);
            // Se asegura de que el resultado no sea mayor que la capacidad de un int
            if(resultado > Integer.MAX_VALUE)
                throw new NegocioException("El valor del resultado es demasiado grande. No sé por qué devuelve int si el método Result devuelve long, o sea.");
            else
                // Se castea el resultado a int y se retorna.
                return (int) resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar eliminar restaurantes con la categoria " + categoria + ".");
        }
    }
    /**
     * Elmina restaurantes que no tengan fecha de inauguración.
     * @return Número de eliminaciones exitosas.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public int eliminarSinFechaInauguracion() throws NegocioException {
        // Filtro para obtener restaurantes sin fecha de inauguracion.
        Bson filtroFecha = Filters.exists("fechaInauguracion", false);
        try {
            // Ejecuta la eliminación.
            long resultado = restauranteDAO.eliminarConteo(filtroFecha);
            // Se asegura de que el resultado no sea mayor que la capacidad de un int
            if(resultado > Integer.MAX_VALUE)
                throw new NegocioException("El valor del resultado es demasiado grande. No sé por qué devuelve int si el método Result devuelve long, o sea.");
            else
                // Se castea el resultado a int y se retorna.
                return (int) resultado;
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar eliminar restaurantes sin fecha de inauguracion.");
        }
    }
    /**
     * Elimina unn restaurante por su ID.
     * @param id ID del restaurante.
     * @return VERDADERO si la eliminación fue exitosa, FALSO en caso contrario.
     * @throws NegocioException Excepción de negocio.
     */
    @Override
    public boolean eliminarPorID(String id) throws NegocioException {
        // Filtro para el ID. Convierte el String recibido en un objeto ObjectId.
        Bson filtroID = Filters.eq("_id", new ObjectId(id));
        try {
            // Ejecuta la eliminación.
            return restauranteDAO.eliminar(filtroID);
        } catch (PersistenciaException e) {
            throw new NegocioException("Ha ocurrido un error al intentar eliminar el restaurante con id" + id + ".");
        }
    }
    /**
     * Valida un restaurante.
     * @param restauranteDTO Restaurante a validar.
     * @throws NegocioException Excepción de negocio.
     */
    private void validarRestaurante(RestauranteDTO restauranteDTO) throws NegocioException{
        // Si el restaurante es null.
        if(restauranteDTO == null)
            throw new NegocioException("El restaurante no puede estar vacío.");
        // Si el nombre del restaurante es null.
        if(restauranteDTO.getNombre() == null)
            throw new NegocioException("El nombre del restaurante no puede estar vacío");
        // Si el rating del restaurante no está vacío y es menor a cero o mayor a 5.
        if(restauranteDTO.getRating() != null && (restauranteDTO.getRating() < 0.0 || restauranteDTO.getRating() > 5.0))
            throw new NegocioException("El rating del restaurante no puede ser menor a cero o mayor a 5.");
        // Si la fecha de inauguración del restaurante no está vacía y está después de la fecha actual.
        if(restauranteDTO.getFechaInauguracion() != null && restauranteDTO.getFechaInauguracion().isAfter(LocalDate.now()))
            throw new NegocioException("La fecha de inauguración no puede ser posterior a la fecha actual.");
    }
}