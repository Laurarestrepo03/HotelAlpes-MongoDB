package uniandes.edu.co.hoteles.grupos;

public class HabitacionGrupo {

    int id; //num_habitacion
    String tipo;
    Double costo_noche;

    public HabitacionGrupo(int id, String tipo, Double costo_noche) {
        this.id = id;
        this.tipo = tipo;
        this.costo_noche = costo_noche;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCosto_noche() {
        return costo_noche;
    }
    
    public void setCosto_noche(Double costo_noche) {
        this.costo_noche = costo_noche;
    }
}
