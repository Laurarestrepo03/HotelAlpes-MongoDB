package uniandes.edu.co.hoteles.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="servicios")
public class Servicio {

    @Id
    private int id;
    private String nombre;
    private String tipo_cobro;
    private Double precio;
    private int capacidad;
    private String estilo;
    private Double costo_adicional;
    private List<Producto> productos;
    
    public Servicio(){;}

    public Servicio(int id, String nombre, String tipo_cobro, Double precio, int capacidad, String estilo, Double costo_adicional) {
        this.id = id;
        this.nombre = nombre;
        this.tipo_cobro = tipo_cobro;
        this.precio = precio;
        this.capacidad = capacidad;
        this.estilo = estilo;
        this.costo_adicional = costo_adicional;
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

    public String getTipo_cobro() {
        return tipo_cobro;
    }

    public void setTipo_cobro(String tipo_cobro) {
        this.tipo_cobro = tipo_cobro;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public Double getCosto_adicional() {
        return costo_adicional;
    }

    public void setCosto_adicional(Double costo_adicional) {
        this.costo_adicional = costo_adicional;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
}

