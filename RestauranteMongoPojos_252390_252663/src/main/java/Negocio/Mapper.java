package Negocio;

import Dominio.Restaurante;

/**
 * Clase que convierte objetos Restaurante a 
 * RestauranteDTO, y viceversa.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class Mapper {
    /**
     * Convierte una entidad Restaurante a un objeto RestauranteDTO.
     * @param r Entidad Restaurante a mapear.
     * @return Objeto RestauranteDTO mapeado.
     */
    public static RestauranteDTO toDTO (Restaurante r){
        return new RestauranteDTO(
                r.getNombre(),
                r.getFechaInauguracion(),
                r.getRating(),
                r.getCategorias()
        );
    }
    /**
     * Convierte un objeto RestauranteDTO a una entidad Restaurante.
     * @param dto Objeto RestauranteDTO a mapear.
     * @return Entidad Restaurante mapeada.
     */
    public static Restaurante toEntity (RestauranteDTO dto){
        Restaurante r = new Restaurante();
            r.setNombre(dto.getNombre());
            r.setFechaInauguracion(dto.getFechaInauguracion());
            r.setRating(dto.getRating());
            r.setCategorias(dto.getCategorias());
        return r;
    }    
}