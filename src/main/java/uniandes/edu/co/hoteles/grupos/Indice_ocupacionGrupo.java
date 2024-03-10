package uniandes.edu.co.hoteles.grupos;

public class Indice_ocupacionGrupo {

    int habitacion;
    Double porcentaje;
    
    public Indice_ocupacionGrupo(int habitacion, Double porcentaje) {
        this.habitacion = habitacion;
        this.porcentaje = porcentaje;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
}
