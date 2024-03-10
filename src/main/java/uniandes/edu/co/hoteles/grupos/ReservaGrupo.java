package uniandes.edu.co.hoteles.grupos;

import uniandes.edu.co.hoteles.modelo.Cliente;

public class ReservaGrupo {
    
    int codigo;
    String fecha_entrada;
    String fecha_salida;
    String estado;
    int num_huespedes;
    Cliente cliente;
    int habitacion;

    public ReservaGrupo(){;}

    public ReservaGrupo(int codigo, String fecha_entrada, String fecha_salida, String estado, int num_huespedes, Cliente cliente, int habitacion) {
        this.codigo = codigo;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
        this.estado = estado;
        this.num_huespedes = num_huespedes;
        this.cliente = cliente;
        this.habitacion = habitacion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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

    public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }
}
