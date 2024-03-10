package uniandes.edu.co.hoteles.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.DeleteQuery;

import uniandes.edu.co.hoteles.modelo.Dotacion;
import uniandes.edu.co.hoteles.modelo.Tipo_habitacion;

public interface Tipo_habitacionRepository extends MongoRepository<Tipo_habitacion, Integer> {

    //Read - Get all types of room
    @Query("{}")
    List<Tipo_habitacion> buscar();
    
    //Read - Get one type of room
    @Query("{_id: ?0}")
    Tipo_habitacion buscarPorId(int id);

    //Read - Get all endowments
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}", 
        "{$unwind: '$dotacion_incluida'}",
        "{$replaceRoot: {newRoot: '$dotacion_incluida'}}"
    })
    List<Dotacion> buscarDotaciones(int id);

    //Read - Get one endowment
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}",
        "{$unwind: '$dotacion_incluida'}",
        "{$match: {'dotacion_incluida._id': ?1}}", 
        "{$project: {'_id': '$dotacion_incluida._id', nombre: '$dotacion_incluida.nombre', cantidad: '$dotacion_incluida.cantidad'}}"
    })
    Dotacion buscarDotacionPorId(int id, int id_dotacion);

    //Create a type of room
    default void insertar(Tipo_habitacion tipoHabitacion) {
        save(tipoHabitacion);
    }

    //Create an endowment
    @Query("{_id: ?0}")
    @Update("{$push:{dotacion_incluida:{_id: ?1, nombre: ?2, cantidad: ?3}}}")
    void insertarDotacion(int id, int id_dotacion, String nombre, int cantidad);

    //Update a type of room
    @Query("{_id: ?0}")
    @Update("{$set:{nombre: ?1, capacidad: ?2}}")
    void actualizar(int id, String nombre, int capacidad);

    //Update an endowment
    @Query("{_id: ?0, 'dotacion_incluida._id': ?1}")
    @Update("{$set:{'dotacion_incluida.$.nombre': ?2, 'dotacion_incluida.$.cantidad': ?3}}")
    void actualizarDotacion(int id, int id_dotacion, String nombre, int cantidad);

    //Delete a type of room
    @DeleteQuery("{'_id' : ?0}")
    void eliminar(int id);

    //Delete an endowment
    @Query("{_id: ?0}")
    @Update("{$pull:{dotacion_incluida:{_id: ?1}}}")
    void eliminarDotacion(int id, int id_dotacion);
    
}
