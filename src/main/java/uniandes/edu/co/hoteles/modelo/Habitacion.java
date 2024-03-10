package uniandes.edu.co.hoteles.modelo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="habitaciones")
public class Habitacion {
    
    @Id
    private int id; //num_habitacion
    private Double costo_noche;
    private int tipo;
    private List<Consumo> consumos;
    private List<Reserva> reservas;

    public Habitacion(){;}
    
    public Habitacion(int id, Double costo_noche, int tipo) {
        this.id = id;
        this.costo_noche = costo_noche;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getCosto_noche() {
        return costo_noche;
    }

    public void setCosto_noche(Double costo_noche) {
        this.costo_noche = costo_noche;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(List<Consumo> consumos) {
        this.consumos = consumos;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
