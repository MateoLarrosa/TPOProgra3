package SolucionMisionUada.tests;

import MisionUada.Decision;
import SolucionMisionUada.EncontrarRecorridoUadaImp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Ejecutor principal que corre todos los casos de prueba y genera reportes
 */
public class EjecutorPruebas {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    SUITE DE PRUEBAS - ALGORITMO BACKTRACKING UADA          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // Crear carpeta de resultados
        crearCarpetaResultados();

        // Obtener todos los casos
        ArrayList<CasoDePrueba> casos = GeneradorCasosPrueba.obtenerTodosCasos();

        // Resultados generales
        StringBuilder resumen = new StringBuilder();
        resumen.append("RESUMEN DE EJECUCION - TODOS LOS CASOS\n");
        resumen.append("=".repeat(80)).append("\n\n");

        int casosExitosos = 0;
        int casosFallidos = 0;

        // Ejecutar cada caso
        for (int i = 0; i < casos.size(); i++) {
            CasoDePrueba caso = casos.get(i);

            System.out.println("\n" + "=".repeat(80));
            caso.imprimirResumen();

            // Ejecutar
            EncontrarRecorridoUadaImp algoritmo = new EncontrarRecorridoUadaImp();

            long inicio = System.currentTimeMillis();
            ArrayList<Decision> solucion = algoritmo.encontrarSecuenciaRecorridoUada(
                    caso.getBateriaInicial(),
                    caso.getOrigen(),
                    caso.getLugaresDisponibles(),
                    caso.getLugaresObligatorios(),
                    caso.getDesplazamientos()
            );
            long fin = System.currentTimeMillis();
            long tiempoEjecucion = fin - inicio;

            // Analizar resultado
            boolean exitoso = !solucion.isEmpty();
            if (exitoso) {
                casosExitosos++;
            } else {
                casosFallidos++;
            }

            // Mostrar resultado en consola
            mostrarResultado(caso, solucion, tiempoEjecucion);

            // Guardar en archivo
            guardarResultado(caso, solucion, tiempoEjecucion, i + 1);

            // Agregar al resumen
            resumen.append(generarLineaResumen(caso, solucion, tiempoEjecucion));
        }

        // Resumen final
        resumen.append("\n").append("=".repeat(80)).append("\n");
        resumen.append(String.format("TOTAL DE CASOS: %d\n", casos.size()));
        resumen.append(String.format("EXITOSOS: %d\n", casosExitosos));
        resumen.append(String.format("FALLIDOS: %d\n", casosFallidos));
        resumen.append("=".repeat(80)).append("\n");

        // Guardar resumen
        guardarResumen(resumen.toString());

        // Mostrar resumen final
        System.out.println("\n\n" + resumen.toString());
        System.out.println("\n✅ Todos los resultados guardados en la carpeta 'resultados/'");
    }

    private static void mostrarResultado(CasoDePrueba caso, ArrayList<Decision> solucion, long tiempoMs) {
        System.out.println("\n" + "-".repeat(60));
        System.out.println("RESULTADO:");
        System.out.println("-".repeat(60));
        System.out.println("Tiempo de ejecucion: " + tiempoMs + " ms");

        if (solucion.isEmpty()) {
            System.out.println("❌ NO SE ENCONTRO SOLUCION");
        } else {
            System.out.println("✅ SOLUCION ENCONTRADA");
            System.out.println("Decisiones: " + solucion.size());

            Decision ultima = solucion.get(solucion.size() - 1);
            System.out.println("Tiempo total recorrido: " + ultima.getTiempoAcumulado() + " segundos");
            System.out.println("Bateria final: " + ultima.getBateriaRemanente() + "%");

            // Mostrar recorrido resumido
            System.out.println("\nRecorrido:");
            for (int i = 0; i < solucion.size(); i++) {
                Decision d = solucion.get(i);
                System.out.println(String.format("  %d. %s -> %s (%s, bat: %d%%)",
                        i + 1,
                        d.getOrigen().getNombre(),
                        d.getDestino().getNombre(),
                        d.getMovimientoEmpleado(),
                        d.getBateriaRemanente()
                ));
            }
        }
    }

    private static void guardarResultado(CasoDePrueba caso, ArrayList<Decision> solucion, long tiempoMs, int numeroCaso) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("═".repeat(80)).append("\n");
        contenido.append("CASO DE PRUEBA #").append(numeroCaso).append("\n");
        contenido.append("═".repeat(80)).append("\n\n");

        contenido.append("NOMBRE: ").append(caso.getNombre()).append("\n");
        contenido.append("DESCRIPCION: ").append(caso.getDescripcion()).append("\n");
        contenido.append("BATERIA INICIAL: ").append(caso.getBateriaInicial()).append("%\n\n");

        contenido.append("LUGARES OBLIGATORIOS:\n");
        caso.getLugaresObligatorios().forEach(e ->
                contenido.append("  - ").append(e.getNombre()).append("\n")
        );

        contenido.append("\nGRAFO:\n");
        caso.getDesplazamientos().forEach(d ->
                contenido.append(String.format("  %s -> %s (%d seg)\n",
                        d.getOrigen().getNombre(),
                        d.getDestino().getNombre(),
                        d.getTiempoBase()
                ))
        );

        contenido.append("\n").append("-".repeat(80)).append("\n");
        contenido.append("RESULTADO\n");
        contenido.append("-".repeat(80)).append("\n");
        contenido.append("Tiempo de ejecucion: ").append(tiempoMs).append(" ms\n\n");

        if (solucion.isEmpty()) {
            contenido.append("❌ NO SE ENCONTRO SOLUCION\n");
        } else {
            Decision ultima = solucion.get(solucion.size() - 1);
            contenido.append("✅ SOLUCION ENCONTRADA\n");
            contenido.append("Decisiones: ").append(solucion.size()).append("\n");
            contenido.append("Tiempo total: ").append(ultima.getTiempoAcumulado()).append(" segundos\n");
            contenido.append("Bateria final: ").append(ultima.getBateriaRemanente()).append("%\n\n");

            contenido.append("SECUENCIA DE DECISIONES:\n");
            contenido.append("-".repeat(80)).append("\n");

            for (int i = 0; i < solucion.size(); i++) {
                Decision d = solucion.get(i);
                contenido.append(String.format("\nDecision #%d:\n", i + 1));
                contenido.append(String.format("  Origen: %s\n", d.getOrigen().getNombre()));
                contenido.append(String.format("  Destino: %s\n", d.getDestino().getNombre()));
                contenido.append(String.format("  Movimiento: %s\n", d.getMovimientoEmpleado()));
                contenido.append(String.format("  Bateria remanente: %d%%\n", d.getBateriaRemanente()));
                contenido.append(String.format("  Tiempo acumulado: %d segundos\n", d.getTiempoAcumulado()));
            }
        }

        String nombreArchivo = String.format("caso%d_%s.txt",
                numeroCaso,
                caso.getNombre().toLowerCase().replaceAll("[^a-z0-9]", "_")
        );

        guardarEnArchivo(nombreArchivo, contenido.toString());
    }

    private static String generarLineaResumen(CasoDePrueba caso, ArrayList<Decision> solucion, long tiempoMs) {
        StringBuilder linea = new StringBuilder();

        linea.append(String.format("%-30s | ", caso.getNombre()));

        if (solucion.isEmpty()) {
            linea.append("❌ Sin solucion");
        } else {
            Decision ultima = solucion.get(solucion.size() - 1);
            linea.append(String.format("✅ %d decisiones | %d seg | Bat: %d%% | %d ms",
                    solucion.size(),
                    ultima.getTiempoAcumulado(),
                    ultima.getBateriaRemanente(),
                    tiempoMs
            ));
        }

        linea.append("\n");
        return linea.toString();
    }

    private static void crearCarpetaResultados() {
        File carpeta = new File("resultados");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    private static void guardarEnArchivo(String nombreArchivo, String contenido) {
        String ruta = Paths.get("resultados", nombreArchivo).toString();
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al guardar " + nombreArchivo + ": " + e.getMessage());
        }
    }

    private static void guardarResumen(String contenido) {
        guardarEnArchivo("resumen.txt", contenido);
    }
}