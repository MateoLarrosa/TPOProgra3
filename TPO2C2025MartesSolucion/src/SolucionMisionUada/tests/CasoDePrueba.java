package SolucionMisionUada.tests;

import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import java.util.ArrayList;

/**
 * Clase que representa un caso de prueba completo con todos sus datos
 */
public class CasoDePrueba {
    private String nombre;
    private String descripcion;
    private int bateriaInicial;
    private Estacion origen;
    private ArrayList<Estacion> lugaresDisponibles;
    private ArrayList<Estacion> lugaresObligatorios;
    private ArrayList<Desplazamiento> desplazamientos;

    public CasoDePrueba(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugaresDisponibles = new ArrayList<>();
        this.lugaresObligatorios = new ArrayList<>();
        this.desplazamientos = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getBateriaInicial() {
        return bateriaInicial;
    }

    public void setBateriaInicial(int bateriaInicial) {
        this.bateriaInicial = bateriaInicial;
    }

    public Estacion getOrigen() {
        return origen;
    }

    public void setOrigen(Estacion origen) {
        this.origen = origen;
    }

    public ArrayList<Estacion> getLugaresDisponibles() {
        return lugaresDisponibles;
    }

    public ArrayList<Estacion> getLugaresObligatorios() {
        return lugaresObligatorios;
    }

    public ArrayList<Desplazamiento> getDesplazamientos() {
        return desplazamientos;
    }

    /**
     * Imprime un resumen del caso de prueba
     */
    public void imprimirResumen() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║ CASO: " + nombre);
        System.out.println("║ " + descripcion);
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║ Bateria inicial: " + bateriaInicial + "%");
        System.out.println("║ Origen: " + origen.getNombre());
        System.out.println("║ Lugares disponibles: " + lugaresDisponibles.size());
        System.out.println("║ Lugares obligatorios: " + lugaresObligatorios.size());
        System.out.println("║ Desplazamientos: " + desplazamientos.size());
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        System.out.println("\nLugares obligatorios:");
        for (Estacion e : lugaresObligatorios) {
            System.out.println("  - " + e.getNombre());
        }

        System.out.println("\nGrafo de desplazamientos:");
        for (Desplazamiento d : desplazamientos) {
            System.out.println("  " + d.getOrigen().getNombre() + " -> " +
                    d.getDestino().getNombre() +
                    " (" + d.getTiempoBase() + "seg, " +
                    d.getMovimientosPermitidos().size() + " movimientos)");
        }
        System.out.println();
    }
}