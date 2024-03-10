package uniandes.edu.co.hoteles.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tipos_habitacion")
public class Tipo_habitacion {

    @Id
    private int id;
    private String nombre;
    private int capacidad;
    private List<Dotacion> dotacion_incluida;

    public Tipo_habitacion(){;}
    
    public Tipo_habitacion(int id, String nombre, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
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

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Dotacion> getDotacion_incluida() {
        return dotacion_incluida;
    }

    public void setDotacion_incluida(List<Dotacion> dotacion_incluida) {
        this.dotacion_incluida = dotacion_incluida;
    }
}