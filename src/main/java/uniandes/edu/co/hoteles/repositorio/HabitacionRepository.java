package uniandes.edu.co.hoteles.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.mongodb.repository.DeleteQuery;

import uniandes.edu.co.hoteles.grupos.HabitacionGrupo;
import uniandes.edu.co.hoteles.grupos.Indice_ocupacionGrupo;
import uniandes.edu.co.hoteles.grupos.ConsumoGrupo;
import uniandes.edu.co.hoteles.grupos.ReservaGrupo;
import uniandes.edu.co.hoteles.grupos.Dinero_recolectadoGrupo;
import uniandes.edu.co.hoteles.grupos.Cliente_consumoGrupo;

import uniandes.edu.co.hoteles.modelo.Habitacion;
import uniandes.edu.co.hoteles.modelo.Reserva;
import uniandes.edu.co.hoteles.modelo.Cliente;

public interface HabitacionRepository extends MongoRepository<Habitacion, Integer> {

    //Read - Get all rooms
    @Aggregation(pipeline = {
        "{$lookup: {from: 'tipos_habitacion', localField: 'tipo', foreignField: '_id', as: 'tipo'}}",
        "{$unwind: '$tipo'}",
        "{$project: {costo_noche: 1, tipo: '$tipo.nombre'}}",
        "{$sort: {_id: 1}}"
    })
    List<HabitacionGrupo> buscar();

    //Read - Get one room
    @Query("{_id: ?0}")
    Habitacion buscarPorId(int id);

    //Read - Get all consumptions
    @Aggregation(pipeline = {
        "{$unwind: '$consumos'}",
        "{$lookup: {from: 'servicios', localField: 'consumos.servicio', foreignField: '_id', as: 'servicio'}}",
        "{$unwind: '$servicio'}",
        "{$project: {'_id': '$consumos._id', 'servicio': '$servicio.nombre', 'cliente': '$consumos.cliente', 'habitacion': '$_id', 'costo': '$servicio.precio', 'fecha': '$consumos.fecha', 'hora': '$consumos.hora'}}",
        "{$sort: {_id: 1}}"
    })
    List<ConsumoGrupo> buscarConsumos();

    //Read - Get all consumptions of a room
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}",
        "{$unwind: '$consumos'}",
        "{$lookup: {from: 'servicios', localField: 'consumos.servicio', foreignField: '_id', as: 'servicio'}}",
        "{$unwind: '$servicio'}",
        "{$project: {'_id': '$consumos._id', 'servicio': '$servicio.nombre', 'cliente': '$consumos.cliente', 'habitacion': '$_id', 'costo': '$servicio.precio', 'fecha': '$consumos.fecha', 'hora': '$consumos.hora'}}",
    })
    List<ConsumoGrupo> buscarConsumosHabitacion(int id);

    //Read - Get one consumption
    @Aggregation(pipeline = {
        "{$unwind: '$consumos'}",
        "{$match: {'consumos._id': ?0}}", 
        "{$lookup: {from: 'servicios', localField: 'consumos.servicio', foreignField: '_id', as: 'servicio'}}",
        "{$unwind: '$servicio'}",
        "{$project: {'_id': '$consumos._id', servicio: '$servicio.nombre', cliente: '$consumos.cliente', 'habitacion': '$_id', 'costo': '$servicio.precio', fecha: '$consumos.fecha', hora: '$consumos.hora'}}"
    })
    ConsumoGrupo buscarConsumoPorId(int id_consumo);

    //Read - Get all reservations
    @Aggregation(pipeline = {
        "{$unwind: '$reservas'}",
        "{$project: {'_id': 0, 'habitacion': '$_id', 'codigo': '$reservas.codigo', 'fecha_entrada': '$reservas.fecha_entrada', 'fecha_salida': '$reservas.fecha_salida', 'estado': '$reservas.estado', 'num_huespedes': '$reservas.num_huespedes', 'cliente': '$reservas.cliente'}}",
        "{$sort: {codigo: 1}}"
    })
    List<ReservaGrupo> buscarReservas();

    //Read - Get all reservations of a room 
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}",
        "{$unwind: '$reservas'}",
        "{$project: {'_id': 0, 'habitacion': '$_id', 'codigo': '$reservas.codigo', 'fecha_entrada': '$reservas.fecha_entrada', 'fecha_salida': '$reservas.fecha_salida', 'estado': '$reservas.estado', 'num_huespedes': '$reservas.num_huespedes', 'cliente': '$reservas.cliente'}}"
    })
    List<ReservaGrupo> buscarReservasHabitacion(int id);
    
    //Read - Get one reservation
    @Aggregation(pipeline = {
        "{$unwind: '$reservas'}",
        "{$match: {'reservas.codigo': ?0}}", 
        "{$project: {'_id': 0, 'habitacion': '$_id', 'codigo': '$reservas.codigo', 'fecha_entrada': '$reservas.fecha_entrada', 'fecha_salida': '$reservas.fecha_salida', 'estado': '$reservas.estado', 'num_huespedes': '$reservas.num_huespedes', 'cliente': '$reservas.cliente'}}"
    })
    ReservaGrupo buscarReservaPorCodigo(int codigo);

    //Read - Get reservation client
    @Aggregation(pipeline = {
        "{$unwind: '$reservas'}",
        "{$match: {'reservas.codigo': ?0}}", 
        "{$project: {_id: 0, cliente: '$reservas.cliente'}}",
        "{$replaceWith: '$cliente'}"
    })
    Cliente darClienteReserva(int codigo);

    //Read - Check room availability
    @Aggregation(pipeline = {
        "{$match: {_id: ?0}}",
        "{$unwind: '$reservas'}",
        "{$match: {$and: [{'reservas.fecha_entrada': {$lte: ?2}}, {'reservas.fecha_salida': {$gte: ?1}}]}}",
        "{$replaceRoot: {newRoot: '$reservas'}}"
    })
    List<Reserva> verificarDisponibilidad(int id, String fecha_entrada, String fecha_salida);

    //Read - Get collected money by room (RFC1)
    @Aggregation(pipeline = {
        "{$unwind: {path: '$consumos', preserveNullAndEmptyArrays: true}}",
        "{$lookup: {from: 'servicios', localField: 'consumos.servicio', foreignField: '_id', as: 'servicio'}}",
        "{$unwind: {path: '$servicio', preserveNullAndEmptyArrays: true }}",
        "{$group: {_id: '$_id', dinero: {$sum: {$add: [{$cond: [{$and: [{$gte: ['$consumos.fecha', ?0]}, {$lte: ['$consumos.fecha', ?1]}]}, {$ifNull: ['$servicio.precio', 0]}, 0]}]}}, habitacion: {$first: '$_id'}}}",
        "{$project: {_id: 0, habitacion: '$habitacion', dinero: '$dinero'}}",
        "{$sort: {habitacion: 1}}"
    })
    List<Dinero_recolectadoGrupo> darDineroRecolectado(String fecha_inicio, String fecha_fin);

    //Read - Get occupation index by room (RFC2)
    @Aggregation(pipeline = {
        "{$unwind: {path: '$reservas', preserveNullAndEmptyArrays: true}}",
        "{$project: {" +
            "_id: 1," +
            "total_dias: { " +
            "$ifNull: [" +
                "{$divide: [ " +
                    "{ $add: [ " +
                        "{$cond: [{$and: [{$gte: [{$toDate: '$reservas.fecha_entrada'}, {$toDate: ?0}]}, {$lte: [{$toDate: '$reservas.fecha_salida'}, {$toDate: ?1}]}]}, {$subtract: [{$toDate: '$reservas.fecha_salida'}, {$toDate: '$reservas.fecha_entrada'}]}, 0]}," +
                        "{$cond: [{$and: [{$gt: [{$toDate: '$reservas.fecha_salida'}, {$toDate: ?1}]}, {$gte: [{$toDate: '$reservas.fecha_entrada'}, {$toDate: ?0}]}, {$lte: [{$toDate: '$reservas.fecha_entrada'}, {$toDate: ?1}]}]}, {$subtract: [{$toDate: ?1}, {$toDate: '$reservas.fecha_entrada'}]}, 0]}," +
                        "{$cond: [{$and: [{$lt: [{$toDate: '$reservas.fecha_entrada'}, {$toDate: ?0}]}, {$lte: [{$toDate: '$reservas.fecha_salida'}, {$toDate: ?1}]}, {$gte: [{$toDate: '$reservas.fecha_salida'}, {$toDate: ?0 }]}]}, {$subtract: [{$toDate: '$reservas.fecha_salida'}, {$toDate: ?0}]}, 0]}," +
                        "{$cond: [{$and: [{$lte: [{$toDate: '$reservas.fecha_entrada'}, {$toDate: ?0}]}, {$gte: [{$toDate: '$reservas.fecha_salida'}, {$toDate: ?1}]}]}, ?2, 0]} " +
                    "]}, 86400000]}, 0]}}}",
        "{$group: {_id: '$_id', total_dias: {$sum: '$total_dias'}}}",
        "{$project: {_id: 0, habitacion: '$_id', porcentaje: {$round: [{$multiply: [{$divide: ['$total_dias', ?2]}, 100]}, 2]}}}",
        "{$sort: {habitacion: 1}}"
    })
    List<Indice_ocupacionGrupo> darIndice_ocupacion(String fecha_inicio, String fecha_fin, long dias);

    //Read - Get client compsumtion (RFC3)
    @Aggregation(pipeline = {
        "{$unwind: '$consumos'}",
        "{$match: {'consumos.cliente': ?0, 'consumos.fecha': { $gte: ?1, $lte: ?2}}}}", 
        "{$lookup: {from: 'servicios', localField: 'consumos.servicio', foreignField: '_id', as: 'servicio'}}",
        "{$unwind: '$servicio'}",
        "{$project: {'_id': '$consumos._id', 'servicio': '$servicio.nombre', 'cliente': '$consumos.cliente', 'habitacion': '$_id', 'costo': '$servicio.precio', 'fecha': '$consumos.fecha', 'hora': '$consumos.hora'}}"
    })
    List<ConsumoGrupo> darConsumosCliente(String cliente, String fecha_inicio, String fecha_fin);

    //Read - Get compsumtion by traits (RFC4)
    @Aggregation({
        "{$unwind: '$reservas'}",
        "{$unwind: '$consumos'}",
        "{$match: {$expr: {$eq: ['$consumos.cliente', '$reservas.cliente.num_documento']}}}",
        "{$project: {_id: 0, cliente: '$reservas.cliente', fecha_consumo: '$consumos.fecha', servicio_consumo: '$consumos.servicio'}}",
        "{$group: {_id: {cliente: '$cliente', servicio: '$servicio_consumo', fecha: '$fecha_consumo'}, veces: {$sum: 1}}}",
        "{$project: {_id: 0, cliente: '$_id.cliente', servicio: '$_id.servicio', fecha: '$_id.fecha', veces: 1}}",
        "{$match: {servicio: ?0, fecha: {$regex: ?1}, $expr: {$cond: {if: {$eq: [?2, null]}, then: {}, else: {$eq: ['$veces', ?2]}}}, 'cliente.num_documento': {$regex: ?3}, 'cliente.tipo_documento': {$regex: ?4}, 'cliente.nombre': {$regex: ?5}, 'cliente.correo': {$regex: ?6}}}"
    })
    List<Cliente_consumoGrupo> darClientesConsumo(Integer servicio, String fecha, Integer veces, String num_documento, String tipo_documento, String nombre, String correo);
    
    //Create a room
    default void insertar(Habitacion habitacion) {
        save(habitacion);
    }

    //Create a consumption
    @Query("{_id: ?0}")
    @Update("{$push:{consumos:{_id: ?1, servicio: ?2, cliente: ?3, fecha: ?4, hora: ?5}}}")
    void insertarConsumo(int id, int id_consumo, int servicio, String cliente, String fecha, String hora);

    //Create a reservation
    @Query("{_id: ?0}")
    @Update("{$push:{reservas:{codigo: ?1, fecha_entrada: ?2, fecha_salida: ?3, estado: ?4, num_huespedes: ?5, cliente: {num_documento: ?6, tipo_documento: ?7, nombre: ?8, correo: ?9}}}}")
    void insertarReserva(int id, int codigo, String fecha_entrada, String fecha_salida, String estado, int num_huespedes, String num_documento, String tipo_documento, String nombre, String correo);

    //Update a room
    @Query("{_id: ?0}")
    @Update("{$set:{costo_noche: ?1, tipo: ?2}}")
    void actualizar(int id, Double cost_noche, int tipo);

    //Update a consumption
    @Query("{_id: ?0, 'consumos._id': ?1}")
    @Update("{$set:{'consumos.$.servicio': ?2, 'consumos.$.cliente': ?3, 'consumos.$.fecha': ?4, 'consumos.$.hora': ?5}}")
    void actualizarConsumo(int id, int id_consumo, int servicio, String cliente, String fecha, String hora);

    //Update a reservation
    @Query("{_id: ?0, 'reservas.codigo': ?1}")
    @Update("{$set:{'reservas.$.fecha_entrada': ?2, 'reservas.$.fecha_salida': ?3, 'reservas.$.estado': ?4, 'reservas.$.num_huespedes': ?5, 'reservas.$.cliente.num_documento': ?6, 'reservas.$.cliente.tipo_documento': ?7, 'reservas.$.cliente.nombre': ?8, 'reservas.$.cliente.correo': ?9}}")
    void actualizarReserva(int id, int codigo, String fecha_entrada, String fecha_salida, String estado, int num_huespedes, String num_documento, String tipo_documento, String nombre, String correo);

    //Delete a room
    @DeleteQuery("{'_id' : ?0}")
    void eliminar(int id);

    //Delete a consumption
    @Query("{_id: ?0}")
    @Update("{$pull:{consumos:{_id: ?1}}}")
    void eliminarConsumo(int id, int id_consumo);

    //Delete a reservation
    @Query("{_id: ?0}")
    @Update("{$pull:{reservas:{codigo: ?1}}}")
    void eliminarReserva(int id, int codigo);
    
}
