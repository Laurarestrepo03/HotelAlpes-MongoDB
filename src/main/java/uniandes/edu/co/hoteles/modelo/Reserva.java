package uniandes.edu.co.hoteles.modelo;

public class Reserva {

    private int codigo;
    private String fecha_entrada;
    private String fecha_salida;
    private String estado;
    private int num_huespedes;
    private Cliente cliente;

    public Reserva(){;}
    
    public Reserva(int codigo, String fecha_entrada, String fecha_salida, String estado, int num_huespedes, Cliente cliente) {
        this.codigo = codigo;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.estado = estado;
        this.num_huespedes = num_huespedes;
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo_reserva(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(String fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNum_huespedes() {
        return num_huespedes;
    }

    public void setNum_huespedes(int num_huespedes) {
        this.num_huespedes = num_huespedes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }    
}
