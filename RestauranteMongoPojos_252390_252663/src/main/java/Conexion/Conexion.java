package Conexion;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 *  Clase que gestiona la conexión con la base de datos MongoDB.
 * @author Leonardo Flores Leyva (252390)
 * @author Jesús Ernesto López Ibarra (252663)
 */
public class Conexion {
    // Atributo estático de la clase.
    private static MongoClient mongoClient = null;
    // URL de MongoDB local.
    private static final String URL = "mongodb://localhost:27017";
    // Nombre de la base de datos.
    private static final String DBName = "GestionRestaurantes"; 
    /**
     * Contructor por defecto.
     */
    private Conexion(){}
    /**
     * Retorna la instancia SingleTon de la clase.
     * Si la instancia aún no existe, es creada, estableciendo
     * las configuraciones necesarias para crear una conexión
     * con la base de datos.
     * @return Base de datos
     */
    public synchronized static MongoDatabase getDatabase(){
        if (mongoClient == null){
            CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );
    
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(new com.mongodb.ConnectionString(URL))
                    .codecRegistry(pojoCodecRegistry).build();

            mongoClient = MongoClients.create(clientSettings);
            return mongoClient.getDatabase(DBName).withCodecRegistry(pojoCodecRegistry);
        }
      return mongoClient.getDatabase(DBName);
    }   
}