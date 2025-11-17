package SolucionMisionUada.tests;

import MisionUada.Decision;
import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import SolucionMisionUada.EncontrarRecorridoUadaImp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Ejecutor de pruebas con el dataset grande de 70 nodos
 * Mide tiempos de ejecucion y genera reportes detallados
 */
public class EjecutorPruebaGrande {

    public static void main(String[] args) {
        // Generar dataset primero para obtener el tamaño real
        DatasetGrande.generarDataset();
        int totalNodos = DatasetGrande.obtenerTodasLasEstaciones().size();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     PRUEBA EXHAUSTIVA - DATASET DE " + totalNodos + " NODOS                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝\n");

        // Mostrar estadísticas
        System.out.println("Dataset generado:");
        DatasetGrande.imprimirEstadisticas();
        System.out.println();

        // Obtener datos
        Estacion origen = DatasetGrande.obtenerOrigen();
        ArrayList<Estacion> todasEstaciones = DatasetGrande.obtenerTodasLasEstaciones();
        ArrayList<Desplazamiento> desplazamientos = DatasetGrande.obtenerTodosLosDesplazamientos();

        // ═══════════════════════════════════════════════════════════
        // CONFIGURACION DE LA PRUEBA (Modificar aqui)
        // ═══════════════════════════════════════════════════════════

        int CANTIDAD_OBLIGATORIOS = 5;  // ← CAMBIAR AQUI para probar con distintas cantidades
        int BATERIA_INICIAL = 100;

        System.out.println("══════════════════════════════════════════════════════════════");
        System.out.println("CONFIGURACION DE LA PRUEBA:");
        System.out.println("  • Cantidad de obligatorios: " + CANTIDAD_OBLIGATORIOS);
        System.out.println("  • Bateria inicial: " + BATERIA_INICIAL + "%");
        System.out.println("══════════════════════════════════════════════════════════════\n");

        // Seleccionar obligatorios
        ArrayList<Estacion> obligatorios = DatasetGrande.seleccionarObligatorios(CANTIDAD_OBLIGATORIOS);

        System.out.println("Lugares obligatorios seleccionados:");
        for (int i = 0; i < obligatorios.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + obligatorios.get(i).getNombre());
        }
        System.out.println();

        ArrayList<Estacion> todas = DatasetGrande.obtenerTodasLasEstaciones();
        ArrayList<Desplazamiento> desplazamientosCopia = DatasetGrande.obtenerTodosLosDesplazamientos();

        // Log completo del grafo + obligatorios
        DatasetGrande.guardarNodosEnArchivo(todas, desplazamientosCopia, obligatorios);

        // ═══════════════════════════════════════════════════════════
        // EJECUTAR ALGORITMO
        // ═══════════════════════════════════════════════════════════

        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("EJECUTANDO ALGORITMO DE BACKTRACKING...");
        System.out.println("════════════════════════════════════════════════════════════");

        EncontrarRecorridoUadaImp algoritmo = new EncontrarRecorridoUadaImp();

        // Medir tiempo de ejecucion
        long tiempoInicio = System.currentTimeMillis();

        ArrayList<Decision> solucion = algoritmo.encontrarSecuenciaRecorridoUada(
                BATERIA_INICIAL,
                origen,
                todasEstaciones,
                obligatorios,
                desplazamientos
        );

        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        // ═══════════════════════════════════════════════════════════
        // MOSTRAR RESULTADOS
        // ═══════════════════════════════════════════════════════════

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("                    RESULTADO FINAL");
        System.out.println("════════════════════════════════════════════════════════════");

        System.out.println("\n⏱️  TIEMPO DE EJECUCION: " + tiempoTotal + " ms");
        System.out.println("    (" + (tiempoTotal / 1000.0) + " segundos)");

        if (solucion.isEmpty()) {
            System.out.println("\n❌ NO SE ENCONTRO SOLUCION");
            System.out.println("\nPosibles causas:");
            System.out.println("  • Bateria insuficiente para completar el recorrido");
            System.out.println("  • No existe camino que conecte todos los obligatorios");
            System.out.println("  • Las podas descartaron todos los caminos posibles");
        } else {
            Decision ultima = solucion.get(solucion.size() - 1);

            System.out.println("\n✅ SOLUCION ENCONTRADA");
            System.out.println("══════════════════════════════════════════════════════════");
            System.out.println("\nESTADISTICAS DE LA SOLUCION:");
            System.out.println("  • Numero de decisiones (pasos): " + solucion.size());
            System.out.println("  • Tiempo total del recorrido: " + ultima.getTiempoAcumulado() + " segundos");
            System.out.println("  • Bateria inicial: " + BATERIA_INICIAL + "%");
            System.out.println("  • Bateria final: " + ultima.getBateriaRemanente() + "%");
            System.out.println("  • Bateria consumida: " + (BATERIA_INICIAL - ultima.getBateriaRemanente()) + "%");

            // Calcular recargas
            int recargasRealizadas = 0;
            for (Decision d : solucion) {
                if (d.getOrigen().getEsAula()) {
                    recargasRealizadas++;
                }
            }
            System.out.println("  • Recargas realizadas: " + recargasRealizadas);

            // Analizar movimientos usados
            int caminar = 0, saltar = 0, patasArriba = 0;
            for (Decision d : solucion) {
                switch (d.getMovimientoEmpleado()) {
                    case CAMINAR: caminar++; break;
                    case SALTAR: saltar++; break;
                    case PATAS_ARRIBA: patasArriba++; break;
                }
            }

            System.out.println("\nDISTRIBUCION DE MOVIMIENTOS:");
            System.out.println("  • CAMINAR: " + caminar + " veces");
            System.out.println("  • SALTAR: " + saltar + " veces");
            System.out.println("  • PATAS_ARRIBA: " + patasArriba + " veces");

            // Mostrar recorrido resumido
            System.out.println("\n════════════════════════════════════════════════════════════");
            System.out.println("RECORRIDO COMPLETO (Resumen):");
            System.out.println("════════════════════════════════════════════════════════════");

            for (int i = 0; i < Math.min(10, solucion.size()); i++) {
                Decision d = solucion.get(i);
                System.out.println(String.format("  %2d. %s -> %s (%s, bat: %.1f%%)",
                        i + 1,
                        d.getOrigen().getNombre(),
                        d.getDestino().getNombre(),
                        d.getMovimientoEmpleado(),
                        d.getBateriaRemanente()
                ));
            }

            if (solucion.size() > 10) {
                System.out.println("  ... (" + (solucion.size() - 10) + " pasos mas)");

                // Mostrar ultimos 3
                for (int i = Math.max(10, solucion.size() - 3); i < solucion.size(); i++) {
                    Decision d = solucion.get(i);
                    System.out.println(String.format("  %2d. %s -> %s (%s, bat: %.1f%%)",
                            i + 1,
                            d.getOrigen().getNombre(),
                            d.getDestino().getNombre(),
                            d.getMovimientoEmpleado(),
                            d.getBateriaRemanente()
                    ));
                }
            }
        }

        // ═══════════════════════════════════════════════════════════
        // GUARDAR RESULTADO EN ARCHIVO
        // ═══════════════════════════════════════════════════════════

        guardarResultado(
                CANTIDAD_OBLIGATORIOS,
                BATERIA_INICIAL,
                obligatorios,
                solucion,
                tiempoTotal
        );

        System.out.println("\n════════════════════════════════════════════════════════════");
        System.out.println("✅ Resultado guardado en: resultados/prueba_70_nodos.txt");
        System.out.println("════════════════════════════════════════════════════════════");
    }

    /**
     * Guarda el resultado completo en un archivo
     */
    private static void guardarResultado(
            int cantObligatorios,
            int bateriaInicial,
            ArrayList<Estacion> obligatorios,
            ArrayList<Decision> solucion,
            long tiempoMs) {

        StringBuilder contenido = new StringBuilder();

        contenido.append("═".repeat(80)).append("\n");
        contenido.append("PRUEBA EXHAUSTIVA - DATASET DE 70 NODOS\n");
        contenido.append("═".repeat(80)).append("\n\n");

        contenido.append("CONFIGURACION:\n");
        contenido.append("  • Total de nodos: 70 (35 aulas + 35 lugares comunes)\n");
        contenido.append("  • Lugares obligatorios: ").append(cantObligatorios).append("\n");
        contenido.append("  • Bateria inicial: ").append(bateriaInicial).append("%\n");
        contenido.append("  • Tiempo de ejecucion: ").append(tiempoMs).append(" ms (")
                .append(tiempoMs / 1000.0).append(" seg)\n\n");

        contenido.append("LUGARES OBLIGATORIOS:\n");
        for (int i = 0; i < obligatorios.size(); i++) {
            contenido.append("  ").append(i + 1).append(". ")
                    .append(obligatorios.get(i).getNombre()).append("\n");
        }
        contenido.append("\n");

        contenido.append("-".repeat(80)).append("\n");
        contenido.append("RESULTADO\n");
        contenido.append("-".repeat(80)).append("\n");

        if (solucion.isEmpty()) {
            contenido.append("❌ NO SE ENCONTRO SOLUCION\n");
        } else {
            Decision ultima = solucion.get(solucion.size() - 1);
            contenido.append("✅ SOLUCION ENCONTRADA\n\n");
            contenido.append("ESTADISTICAS:\n");
            contenido.append("  • Decisiones: ").append(solucion.size()).append("\n");
            contenido.append("  • Tiempo total recorrido: ").append(ultima.getTiempoAcumulado())
                    .append(" segundos\n");
            contenido.append("  • Bateria final: ").append(ultima.getBateriaRemanente()).append("%\n");
            contenido.append("  • Bateria consumida: ")
                    .append(bateriaInicial - ultima.getBateriaRemanente()).append("%\n\n");

            contenido.append("RECORRIDO COMPLETO:\n");
            contenido.append("-".repeat(80)).append("\n");

            for (int i = 0; i < solucion.size(); i++) {
                Decision d = solucion.get(i);
                contenido.append(String.format("\nDecision #%d:\n", i + 1));
                contenido.append(String.format("  Origen: %s\n", d.getOrigen().getNombre()));
                contenido.append(String.format("  Destino: %s\n", d.getDestino().getNombre()));
                contenido.append(String.format("  Movimiento: %s\n", d.getMovimientoEmpleado()));
                contenido.append(String.format("  Bateria: %.1f%%\n", d.getBateriaRemanente()));
                contenido.append(String.format("  Tiempo acumulado: %.1f seg\n", d.getTiempoAcumulado()));
            }
        }

        // Guardar archivo
        File carpeta = new File("resultados");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (FileWriter writer = new FileWriter("resultados/prueba_70_nodos.txt")) {
            writer.write(contenido.toString());
        } catch (IOException e) {
            System.err.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}