package uniandes.edu.co.hoteles.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="secuencias")
public class Secuencia {

    @Id
    private int id; 
    private String nombre;
    private int id_actual;
    
    public Secuencia(int id, String nombre, int id_actual) {
        this.id = id;
        this.nombre = nombre;
        this.id_actual = id_actual;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_actual() {
        return id_actual;
    }

    public void setId_actual(int id_actual) {
        this.id_actual = id_actual;
    }   
}
