package uniandes.edu.co.hoteles.grupos;
import uniandes.edu.co.hoteles.modelo.Cliente;

public class Cliente_consumoGrupo {

    int veces;
    int servicio;
    String fecha;
    Cliente cliente;
    
    public Cliente_consumoGrupo(int veces, int servicio, String fecha, Cliente cliente) {
        this.veces = veces;
        this.servicio = servicio;
        this.fecha = fecha;
        this.cliente = cliente;
    }

    public int getVeces() {
        return veces;
    }

    public void setVeces(int veces) {
        this.veces = veces;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
