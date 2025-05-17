package es.upm.backend.domain.entities;

public enum TipoTarifa {

    DIARIA_KILOMETRAJE("Diaria_Kilometraje",1.2),
    DIARIA_ILIMITADO("Diaria_Ilimitado",1.3),
    SEMANAL("Semanal",1.1),
    FIN_DE_SEMANA("Fin_de_Semana",1.5),
    LARGA_DURACION("Larga_Duracion",1.05);

    private final String name;
    private final double precio;

    TipoTarifa(String name, double precio) {
        this.name = name;
        this.precio = precio;
    }

    public String getNombre() {
        return name;
    }

    public double getPrecio() {
        return precio;
    }



}
