package uniandes.edu.co.hoteles.grupos;

public class ConsumoGrupo {

    int id;
    String servicio;
    String cliente;
    Double costo;
    String fecha;
    String hora;
    int habitacion;

    public ConsumoGrupo(){;}
    
    public ConsumoGrupo(int id, String servicio, String cliente, Double costo, String fecha, String hora, int habitacion) {
        this.id = id;
        this.servicio = servicio;
        this.cliente = cliente;
        this.costo = costo;
        this.fecha = fecha;
        this.hora = hora;
        this.habitacion = habitacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }
}