package uniandes.edu.co.hoteles.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import uniandes.edu.co.hoteles.modelo.Secuencia;

public interface SecuenciaRepository extends MongoRepository<Secuencia, Integer> {
    
    //Read - Get all sequences
    @Query("{}")
    List<Secuencia> buscar();
    
    //Read - Get one sequence
    @Query("{_id: ?0}")
    Secuencia buscarPorId(int id);

    //Update a sequence
    @Query("{_id: ?0}")
    @Update("{$set:{id_actual: ?1}}")
    void actualizarIdActual(int id, int id_nuevo);

}
