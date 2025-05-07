package Negocio;

import Dominio.Restaurante;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.bson.Document;

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
    
    public static Document toDocument(Restaurante restaurante) {
        Date fechaEntity = (restaurante.getFechaInauguracion() != null) ?
                Date.from(restaurante.getFechaInauguracion().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        return new Document("nombre", restaurante.getNombre())
                .append("fechaInauguracion", fechaEntity)
                .append("rating", restaurante.getRating())
                .append("categorias", restaurante.getCategorias());
    }

    /**
     * Convierte un Document de MongoDB a un objeto Restaurante.
     * @param document Document de MongoDB a convertir.
     * @return Objeto Restaurante resultante.
     */
    public static Restaurante toEntity(Document document) {
        Date fechaDocument = document.getDate("fechaInauguracion");
        LocalDate fechaEntity = null;
        if (fechaDocument != null) {
            fechaEntity = Instant.ofEpochMilli(fechaDocument.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return new Restaurante(
                document.getString("nombre"),
                fechaEntity,
                document.getDouble("rating"),
                (List<String>) document.get("categorias")
        );
    }
}