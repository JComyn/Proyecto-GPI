package es.upm.backend.domain.entities;

// Definición del enum para las categorías de coches
public enum CategoriaCoche {
    ALTA("Alta",100),
    MEDIA("Media",70),
    BAJA("Baja",50);

    private final String nombre;
    private final int precio;

    CategoriaCoche(String nombre,int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }
}