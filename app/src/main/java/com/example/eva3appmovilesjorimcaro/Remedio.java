package com.example.eva3appmovilesjorimcaro;

public class Remedio {
    private int id;                  // ID único del remedio
    private String nombre;           // Nombre del remedio
    private int cantidad;            // Cantidad disponible
    private String fechaVencimiento; // Fecha de vencimiento
    private Integer miligramos;      // Miligramos (opcional)
    private String presentacion;     // Presentación (Comprimido, Líquido, etc.)
    private String descripcion;      // Descripción opcional

    // Constructor completo
    public Remedio(int id, String nombre, int cantidad, String fechaVencimiento, Integer miligramos, String presentacion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.miligramos = miligramos;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
    }

    // Constructor sin ID (para insertar nuevos remedios)
    public Remedio(String nombre, int cantidad, String fechaVencimiento, Integer miligramos, String presentacion, String descripcion) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.miligramos = miligramos;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getMiligramos() {
        return miligramos;
    }

    public void setMiligramos(Integer miligramos) {
        this.miligramos = miligramos;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método para representar el remedio como texto (opcional)
    @Override
    public String toString() {
        return "Remedio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", miligramos=" + (miligramos != null ? miligramos : "N/A") +
                ", presentacion='" + presentacion + '\'' +
                ", descripcion='" + (descripcion != null ? descripcion : "N/A") + '\'' +
                '}';
    }
}

