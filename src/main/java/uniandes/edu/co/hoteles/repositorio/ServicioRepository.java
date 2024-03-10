package uniandes.edu.co.hoteles.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.DeleteQuery;

import uniandes.edu.co.hoteles.modelo.Producto;
import uniandes.edu.co.hoteles.modelo.Servicio;

public interface ServicioRepository extends MongoRepository<Servicio, Integer> {

    //Read - Get all services
    @Query("{}")
    List<Servicio> buscar();
    
    //Read - Get one service
    @Query("{_id: ?0}")
    Servicio buscarPorId(int id);

    //Read - Get one service by name
    @Query("{nombre: ?0}")
    Servicio buscarPorNombre(String nombre);

    //Read - Get all products
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}", 
        "{$unwind: '$productos'}",
        "{$replaceRoot: {newRoot: '$productos'}}"
    })
    List<Producto> buscaProductos(int id);

    //Read - Get one product
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}",
        "{$unwind: '$productos'}",
        "{$match: {'productos._id': ?1}}", 
        "{$project: {'_id': '$productos._id', nombre: '$productos.nombre', precio: '$productos.precio'}}"
    })
    Producto buscarProductoPorId(int id, int id_prodcto);

    //Create a service
    default void insertar(Servicio servicio) {
        save(servicio);
    }

    //Create a product
    @Query("{_id: ?0}")
    @Update("{$push:{productos:{_id: ?1, nombre: ?2, precio: ?3}}}")
    void insertarProducto(int id, int id_producto, String nombre, Double precio);

    //Update a service
    @Query("{_id: ?0}")
    @Update("{$set:{nombre: ?1, tipo_cobro: ?2, precio: ?3, capacidad: ?4, estilo: ?5, costo_adicional: ?6}}")
    void actualizar(int id, String nombre, String tipo_cobro, Double precio, int capacidad, String estilo, Double costo_adicional);

    //Update a product
    @Query("{_id: ?0, 'productos._id': ?1}")
    @Update("{$set:{'productos.$.nombre': ?2, 'productos.$.precio': ?3}}")
    void actualizarProducto(int id, int id_producto, String nombre, Double precio);

    //Delete a service
    @DeleteQuery("{'_id' : ?0}")
    void eliminar(int id);

    //Delete a product
    @Query("{_id: ?0}")
    @Update("{$pull:{productos:{_id: ?1}}}")
    void eliminarProducto(int id, int id_producto);
    
}
