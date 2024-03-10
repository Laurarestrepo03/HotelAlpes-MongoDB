package uniandes.edu.co.hoteles.modelo;

public class Cliente {

    private String num_documento;
    private String tipo_documento;
    private String nombre;
    private String correo;
    
    public Cliente(String num_documento, String tipo_documento, String nombre, String correo) {
        this.num_documento = num_documento;
        this.tipo_documento = tipo_documento;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNum_documento() {
        return num_documento;
    }

    public void setNum_documento(String num_documento) {
        this.num_documento = num_documento;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }    
}