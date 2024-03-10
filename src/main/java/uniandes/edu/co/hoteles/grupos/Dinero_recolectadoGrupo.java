package uniandes.edu.co.hoteles.grupos;

public class Dinero_recolectadoGrupo {
    
    int habitacion;
    Double dinero;

    public Dinero_recolectadoGrupo(int habitacion, Double dinero) {
        this.habitacion = habitacion;
        this.dinero = dinero;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public Double getDinero() {
        return dinero;
    }
    
    public void setDinero_Recoelctado(Double dinero) {
        this.dinero = dinero;
    }
}
