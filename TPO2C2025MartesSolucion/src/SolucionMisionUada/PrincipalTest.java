package SolucionMisionUada;

import MisionUada.Decision;
import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import MisionUada.Movimiento;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PrincipalTest {
    public static void main(String[] args) {

        System.out.println("=== INICIANDO PRUEBA DEL ALGORITMO DE BACKTRACKING ===\n");

        // Batería inicial
        int bateriaInicial = 100;

        // ===== DEFINIR ESTACIONES =====
        ArrayList<Estacion> lugaresDisponibles = new ArrayList<>();
        ArrayList<Estacion> lugaresObligatorios = new ArrayList<>();

        // Estación origen y destino final (Aula 633)
        Estacion aula633 = new Estacion("Aula 633", 633, true);

        // Otras estaciones
        Estacion starbucks = new Estacion("Starbucks planta baja", 1, false);
        Estacion molinete = new Estacion("Molinete Lima 3", 3, false);
        Estacion aula613 = new Estacion("Aula 613", 613, true);

        // Lugares disponibles
        lugaresDisponibles.add(starbucks);
        lugaresDisponibles.add(molinete);
        lugaresDisponibles.add(aula613);

        // Lugares obligatorios (deben visitarse)
        lugaresObligatorios.add(starbucks);
        lugaresObligatorios.add(molinete);

        System.out.println("Lugares obligatorios a visitar:");
        for (Estacion est : lugaresObligatorios) {
            System.out.println("  - " + est.getNombre());
        }
        System.out.println();

        // ===== DEFINIR DESPLAZAMIENTOS (GRAFO) =====
        ArrayList<Desplazamiento> desplazamientos = new ArrayList<>();

        // Movimientos disponibles
        ArrayList<Movimiento> caminosSaltos = new ArrayList<>();
        caminosSaltos.add(Movimiento.CAMINAR);
        caminosSaltos.add(Movimiento.SALTAR);

        ArrayList<Movimiento> todosMovimientos = new ArrayList<>();
        todosMovimientos.add(Movimiento.CAMINAR);
        todosMovimientos.add(Movimiento.SALTAR);
        todosMovimientos.add(Movimiento.PATAS_ARRIBA);

        // Crear un ciclo completo:
        // 633 -> Starbucks -> Molinete -> Aula613 -> 633

        // 1. De Aula 633 a Starbucks (15 segundos)
        Desplazamiento d1 = new Desplazamiento(aula633, starbucks, caminosSaltos, 15);
        desplazamientos.add(d1);

        // 2. De Starbucks a Molinete (20 segundos)
        Desplazamiento d2 = new Desplazamiento(starbucks, molinete, todosMovimientos, 20);
        desplazamientos.add(d2);

        // 3. De Molinete a Aula 613 (18 segundos)
        Desplazamiento d3 = new Desplazamiento(molinete, aula613, caminosSaltos, 18);
        desplazamientos.add(d3);

        // 4. De Aula 613 de vuelta a Aula 633 (10 segundos)
        Desplazamiento d4 = new Desplazamiento(aula613, aula633, todosMovimientos, 10);
        desplazamientos.add(d4);

        System.out.println("Desplazamientos definidos:");
        for (Desplazamiento d : desplazamientos) {
            System.out.println("  - " + d.getOrigen().getNombre() + " -> " +
                    d.getDestino().getNombre() + " (" + d.getTiempoBase() + " seg)");
        }
        System.out.println();

        // ===== EJECUTAR ALGORITMO =====
        System.out.println("Ejecutando algoritmo de backtracking...\n");

        EncontrarRecorridoUadaImp recorridoUada = new EncontrarRecorridoUadaImp();

        long startTime = System.currentTimeMillis();
        ArrayList<Decision> secuenciaDecisiones = recorridoUada.encontrarSecuenciaRecorridoUada(
                bateriaInicial,
                aula633,
                lugaresDisponibles,
                lugaresObligatorios,
                desplazamientos
        );
        long endTime = System.currentTimeMillis();

        // ===== MOSTRAR RESULTADOS =====
        System.out.println("=== RESULTADO ===");
        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");

        if (secuenciaDecisiones.isEmpty()) {
            System.out.println("\n NO SE ENCONTRÓ SOLUCIÓN");
            System.out.println("Posibles causas:");
            System.out.println("  - No existe un camino que conecte todos los lugares obligatorios");
            System.out.println("  - La batería no es suficiente para completar el recorrido");
            System.out.println("  - El grafo no forma un ciclo completo hacia el origen");
        } else {
            System.out.println("\n SOLUCIÓN ENCONTRADA");
            System.out.println("Número de decisiones: " + secuenciaDecisiones.size());

            if (!secuenciaDecisiones.isEmpty()) {
                Decision ultima = secuenciaDecisiones.get(secuenciaDecisiones.size() - 1);
                System.out.println("Tiempo total: " + ultima.getTiempoAcumulado() + " segundos");
                System.out.println("Batería final: " + ultima.getBateriaRemanente() + "%");
            }

            System.out.println("\n--- SECUENCIA DE DECISIONES ---");
            imprimirSecuenciaDecisiones(secuenciaDecisiones);
        }
    }

    public static void imprimirSecuenciaDecisiones(ArrayList<Decision> secuenciaDecisiones) {
        StringBuilder sb = new StringBuilder();
        StringBuilder consola = new StringBuilder();

        for (int i = 0; i < secuenciaDecisiones.size(); i++) {
            Decision decision = secuenciaDecisiones.get(i);
            int indice = i + 1;

            String decisionString = String.format(
                    "Decisión #%d\n" +
                            "  Origen: %s\n" +
                            "  Destino: %s\n" +
                            "  Movimiento: %s\n" +
                            "  Batería remanente: %d%%\n" + // %.2f%% (cuando se cambie la libreria cambiar esto)
                            "  Tiempo acumulado: %d segundos\n\n", // %.2f segundos (cuando se cambie la libreria cambiar esto)
                    indice,
                    decision.getOrigen().getNombre(),
                    decision.getDestino().getNombre(),
                    decision.getMovimientoEmpleado().toString(),
                    decision.getBateriaRemanente(),
                    decision.getTiempoAcumulado()
            );

            sb.append(decisionString);
            consola.append(decisionString);
        }

        // Imprimir en consola
        System.out.println(consola.toString());

        // Guardar en archivo
        String rutaArchivo = Paths.get(System.getProperty("user.dir"), "output.txt").toString();

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(sb.toString());
            System.out.println("Solución guardada en: " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}