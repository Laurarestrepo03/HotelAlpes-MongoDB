package uniandes.edu.co.hoteles.modelo;

public class Consumo {

    private int id;
    private int servicio;
    private String cliente;
    private String fecha;
    private String hora;

    public Consumo(){;}

    public Consumo(int id, int servicio, String cliente, String fecha, String hora) {
        this.id = id;
        this.servicio = servicio;
        this.cliente = cliente;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
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
}
