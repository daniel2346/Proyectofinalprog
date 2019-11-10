package me.parzibyte.crudsqlite.modelos;

public class Producto {

    private String nombre;
    private String tipo;
    private Double velocidad;
    private Double precio;
    private Double nucleos;
    private long id; // El ID de la BD

    public Producto(String nombre, String tipo, Double velocidad, Double precio, Double nucleos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.velocidad = velocidad;
        this.precio = precio;
        this.nucleos = nucleos;

    }
    public Producto(String nombre, String tipo, Double velocidad, Double precio, Double nucleos, long id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.velocidad = velocidad;
        this.precio = precio;
        this.nucleos = nucleos;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getNucleos() {
        return nucleos;
    }

    public void setNucleos(Double nucleos) {
        this.nucleos = nucleos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                "tipo='" + tipo + '\'' +
                "velocidad='" + velocidad + '\'' +
                "precio='" + precio + '\'' +
                ", nucleos=" + nucleos +
                '}';
    }
}
