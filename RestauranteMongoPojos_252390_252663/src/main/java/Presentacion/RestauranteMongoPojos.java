package Presentacion;

import Negocio.NegocioException;
import Negocio.RestauranteBO;
import Negocio.RestauranteDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase de Presentación.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class RestauranteMongoPojos {
    /**
     * Código main para la ejecución del proyecto.
     * @param args Argumentos.
     */
    public static void main(String[] args) {
        try {
            RestauranteBO negocio = RestauranteBO.getInstance();
            // 1. Prueba de método insertarRestaurantesPorDefecto.
            negocio.insertarRestaurantesPorDefecto();
            // Nuevo restaurante de ejemplo.
            RestauranteDTO restauranteOne = new RestauranteDTO();
            restauranteOne.setNombre("Mariscos Don Kamaron");
            restauranteOne.setRating(4.5);
            restauranteOne.setFechaInauguracion(LocalDate.of(1997, 3, 25));
            restauranteOne.setCategorias(Arrays.asList("Mariscos", "Familiar", "Tropical"));
            // 2. Prueba de método insertar.
            negocio.insertar(restauranteOne);
            
            // Cuatro nuevos restaurantes de ejemplo (2 sin fecha de inauguración).
            RestauranteDTO restauranteTwo = new RestauranteDTO();
            restauranteTwo.setNombre("Mariscos Don Pulpo");
            restauranteTwo.setRating(1.0);
            restauranteTwo.setFechaInauguracion(LocalDate.of(1972, 8, 12));
            restauranteTwo.setCategorias(Arrays.asList("Mariscos", "Alcohol", "Fresco"));
            
            RestauranteDTO restauranteThree = new RestauranteDTO();
            restauranteThree.setNombre("Crustaceo Cascarudo");
            restauranteThree.setRating(5.0);
            restauranteThree.setCategorias(Arrays.asList("Hamburguesas", "Cangreburguers", "Debajo del mar", "familiar"));
            
            RestauranteDTO restauranteFour = new RestauranteDTO();
            restauranteFour.setNombre("Tacos Don Baby");
            restauranteFour.setRating(2.5);
            restauranteFour.setCategorias(Arrays.asList("Tacos", "Familiar", "Buffet"));
            
            RestauranteDTO restauranteFive = new RestauranteDTO();
            restauranteFive.setNombre("Sushi Cuerdo");
            restauranteFive.setRating(3.2);
            restauranteFive.setCategorias(Arrays.asList("Sushi", "Original", "Cortes"));
            // 3. Prueba de método insertarVarios.
            negocio.insertarVarios(Arrays.asList(restauranteTwo, restauranteThree, restauranteFour, restauranteFive));
            // lista de restaurantes encontrados (utilizado para los métodos que devuelven una lista de restaurantes).
            List<RestauranteDTO> restaurantesEncontrados = new ArrayList<>();
            // 4. Prueba de método consultarTodos.
            restaurantesEncontrados = negocio.consultarTodos();
            imprimirConsulta(
                    "Lista de todos los restaurantes: ", 
                    restaurantesEncontrados
            );
            // 5. Prueba de método consultarPorRatingMayorA.
            restaurantesEncontrados = negocio.consultarPorRatingMayorA(4.0);
            imprimirConsulta(
                    "Lista de restaurantes con rating mayor a 4 estrellas: ", 
                    restaurantesEncontrados
            );
            // 6. Prueba de método consultarPorRangoRating.
            restaurantesEncontrados = negocio.consultarPorRangoRating(3.0, 5.0);
            imprimirConsulta(
                    "Lista de restaurantes con rating entre 3 y 5 estrellas: ", 
                    restaurantesEncontrados
            );
            // 7. Prueba de método consultarPorCategoria.
            restaurantesEncontrados = negocio.consultarPorCategoria("Mariscos");
            imprimirConsulta(
                    "Lista de restaurantes con la categoria Mariscos: ", 
                    restaurantesEncontrados
            );
            // 8. Prueba de método buscarPorNombreRegex.
            restaurantesEncontrados = negocio.buscarPorNombreRegex(".*[oO].*");
            imprimirConsulta(
                    "Lista de restaurantes cuyo nombre tiene la letra O (mayuscula o minuscula): ", 
                    restaurantesEncontrados
            );
            // 9. Prueba de método buscarNombreIniciaCon.
            restaurantesEncontrados = negocio.buscarNombreIniciaCon("T");
            imprimirConsulta(
                    "Lista de restaurantes cuyo nombre incia con la letra T: ", 
                    restaurantesEncontrados
            );
            // 10. Prueba de método consultarPorFechaDesdeOrdenado.
            restaurantesEncontrados = negocio.consultarPorFechaDesdeOrdenado(2020, false);
            imprimirConsulta(
                    "Lista de restaurantes inaugurados despues del 2020: ", 
                    restaurantesEncontrados
            );
            // 11. Prueba de método top3RestaurantesPorCategoria.
            restaurantesEncontrados = negocio.top3RestaurantesPorCategoria("Familiar");
            imprimirConsulta(
                    "Top 3 restaurantes con categoria \"Familiar\": ", 
                    restaurantesEncontrados
            );
            // 12. Prueba de método sinCategorias.
            restaurantesEncontrados = negocio.sinCategorias();
            imprimirConsulta(
                    "Lista de restaurantes sin categorias: ", 
                    restaurantesEncontrados
            );
            // 13. Prueba de método consultarRestaurantePorNombreCompleto.
            RestauranteDTO restaurante = negocio.consultarRestaurantePorNombreCompleto("Mariscos El Berna");
            if(restaurante != null)
                System.out.println("\nRestaurante Mariscos El Berna encontrado.");
            else
                System.out.println("\nNo se ha encontrado el restaurante Mariscos El Berna.");
            // 14. Prueba de método actualizarRatingPorNombre.
            if(negocio.actualizarRatingPorNombre("Tortas SQL", 4.0))
                System.out.println("\nRating actualizado de restaurante SQL.");
            else
                System.out.println("\nNo se pudo actualizar el rating del restaurante Tortas SQL.");
            // 15. Prueba de método agregarCategoriaSinDuplicado.
            if(negocio.agregarCategoriaSinDuplicado("Mariscos Don Kamaron", "Fiesta"))
                System.out.println("\nCategoria \"Fiesta\" agregada al restaurante Mariscos Don Kamaron.");
            else
                System.out.println("\nNo se pudo agregar la categoria \"Fiesta\" al restaurante Mariscos Don Kamaron.");
            // 16. Prueba de método aumentarRatingPorCategoria
            if(negocio.aumentarRatingPorCategoria("Sushi", 0.3))
                System.out.println("\nRating aumentado en 0.3 para restaurantes con categoria Sushi.");
            else
                System.out.println("\nNo se pudo aumentar en 0.3 los restaurantes con categoria Sushi.");
            // 17. Prueba de método aumentarRatingPorNombreRestaurante.
            if(negocio.aumentarRatingPorNombreRestaurante("Little Caesars", 0.5))
                System.out.println("\nRating aumentado en 0.5 para el restaurante Little Caesars.");
            else
                System.out.println("\nNo se pudo aumentar en 0.5 el restaurante Little Caesars.");
            // 18. Prueba de método agregarCategoriasDondeFalten.
            int registrosModificados = negocio.agregarCategoriasDondeFalten(Arrays.asList("Alta Calidad", "Comida Basura"));
            if(registrosModificados > 0)
                System.out.println("\nNumero de restaurantes actualizados con nuevas categorias: " + registrosModificados);
            else
                System.out.println("\nNo se encontraron restaurantes sin categorias.");
            // 19. Prueba de método actualizarNombre.
            if(negocio.actualizarNombre("Tortas SQL", "Tortas MongoDB"))
                System.out.println("\nNombre actualizado del restaurante Tortas SQL a Tortas MongoDB");
            else
                System.out.println("\nNo se pudo actualizar el nombre del restaurante Tortas SQL.");
            // 20. Prueba de método actualizarCategorias.
            if(negocio.actualizarCategorias("Sushi Loco", Arrays.asList("Sushi", "Sabroso", "Asiatico")))
                System.out.println("\nCategorias actualizadas del restaurante Sushi Loco.");
            else
                System.out.println("\nNo se pudieron actualizar las categorias del restaurante Sushi Loco.");
            // 21. Prueba de método eliminarPorNombre.
            if(negocio.eliminarPorNombre("Mariscos Don Pulpo"))
                System.out.println("\nRestaurante Mariscos Don Pulpo eliminado con exito!");
            else
                System.out.println("\nNo se pudo eliminar el restaurante Mariscos Don Pulpo.");
            // 22. Prueba de método eliminarPorRatingMenorA.
            registrosModificados = negocio.eliminarPorRatingMenorA(3.0);
            if(registrosModificados > 0)
                System.out.println("\nCantidad de restaurantes eliminados cuyo rating es menor a 3 estrellas: " + registrosModificados);
            else
                System.out.println("\nNo existen restaurantes con rating menor a 3 estrellas.");
            // 23. Prueba de método eliminarPorCategoria.
            registrosModificados = negocio.eliminarPorCategoria("Sushi");
            if(registrosModificados > 0)
                System.out.println("\nCantidad de restaurantes con categoria Sushi eliminados: " + registrosModificados);
            else
                System.out.println("\nNo existen restaurantes con la categoria Sushi.");
            // 24. Prueba de método eliminarSinFechaInauguracion.
            registrosModificados = negocio.eliminarSinFechaInauguracion();
            if(registrosModificados > 0)
                System.out.println("\nCantidad de restaurantes sin fecha de inauguracion eliminados: " + registrosModificados);
            else
                System.out.println("\nNo existen restaurantes sin fecha de inauguracion.");
            // 23. Prueba de método eliminarPorID.
            if(negocio.eliminarPorID(restaurante.getId()))
                System.out.println("\nRestaurante con id " + restaurante.getId() + " eliminado con exito.");
            else
                System.out.println("\nNo existe un restaurante con el ID " + restaurante.getId() + ".");
            // FIN.
        } catch (NegocioException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Recorre la lista de restaurantes e imprime el
     * nombre de cada uno.
     * @param mensaje Mensaje personalizado.
     * @param restaurantes Lista de restaurantes.
     */
    private static void imprimirConsulta(String mensaje, List<RestauranteDTO> restaurantes){
        System.out.println("\n" + mensaje + "\n");
        if(restaurantes != null && !restaurantes.isEmpty()){
            for(RestauranteDTO restauranteEncontrado : restaurantes){
                System.out.println(restauranteEncontrado.getNombre());
            }
        } else
            System.out.println("Sin restaurantes!");
    }
}