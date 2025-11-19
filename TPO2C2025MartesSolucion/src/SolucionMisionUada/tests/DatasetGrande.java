package SolucionMisionUada.tests;

import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import MisionUada.Movimiento;

import java.io.IOException;
import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;


/**
 * Dataset FIJO de 40 nodos para pruebas exhaustivas
 * - 20 aulas (recargables)
 * - 20 lugares comunes (no recargables)
 * - INCLUYE CONEXIONES BIDIRECCIONALES EN ALGUNOS NODOS PARA PROBAR PODA DE VISITADOS
 */
public class DatasetGrande {

    private static ArrayList<Estacion> todasLasEstaciones;
    private static ArrayList<Desplazamiento> todosLosDesplazamientos;

    /**
     * Genera el dataset completo de 40 nodos de forma DETERMINISTICA
     */
    public static void generarDataset() {
        todasLasEstaciones = new ArrayList<>();
        todosLosDesplazamientos = new ArrayList<>();

        // ===== CREAR 25 AULAS (con numeros 100-999) =====
        int[] numerosAulas = {
                // Piso 1
                101, 105, 110, 115,
                // Piso 2
                201, 205, 210, 215,
                // Piso 3
                301, 305, 310, 315,
                // Piso 6
                613, 633, 655, 689,
                // Piso 7
                710, 721, 745,
                // Piso 8
                815//, 837, 862,
                // Piso 9
                //918, 933, 955,
        };

        for (int num : numerosAulas) {
            todasLasEstaciones.add(new Estacion("Aula " + num, num, true));
        }

        // ===== CREAR 25 LUGARES COMUNES (no aulas) =====
        String[] lugaresComunes = {
                // Planta baja
                "Starbucks Planta Baja",
                "Molinete L1",
                "Molinete L3",
                "Recepcion Principal",
                "Buffet Planta Baja",
                "Rustica Cafe",

                // Pisos intermedios
                "Pasillo Norte Piso 1",
                "Sala de profesores piso 1",

                "Pasillo Norte Piso 2",
                "Biblioteca Piso 2",

                "Pasillo Norte Piso 3",
                "Auditorio Piso 3",
                "Laboratorio Piso 3",

                "Pasillo Norte Piso 6",
                "Pasillo Sur Piso 6",

                "Pasillo Norte Piso 7",
                "Pasillo Sur Piso 7",

                "Starbucks Piso 8",
                "Gimnasio Piso 8",

                "Secretaria Academica"
        };

        int idNoAula = 1;
        for (String nombre : lugaresComunes) {
            todasLasEstaciones.add(new Estacion(nombre, idNoAula++, false));
        }

        // ===== GENERAR DESPLAZAMIENTOS (GRAFO FIJO) =====
        generarDesplazamientos();
    }

    /**
     * Genera desplazamientos entre estaciones.
     * Los desplazamientos de los nodos existentes no cambian al agregar nuevos nodos
     * Solo se crean desplazamientos para los nodos nuevos
     * incluye 3 conexiones bidireccionales para testear poda de visitados
     */
    private static void generarDesplazamientos() {

        ArrayList<Movimiento> movimientosCaminosSaltos = new ArrayList<>();
        movimientosCaminosSaltos.add(Movimiento.CAMINAR);
        movimientosCaminosSaltos.add(Movimiento.SALTAR);

        ArrayList<Movimiento> movimientosTodos = new ArrayList<>();
        movimientosTodos.add(Movimiento.CAMINAR);
        movimientosTodos.add(Movimiento.SALTAR);
        movimientosTodos.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> movimientosSoloCaminar = new ArrayList<>();
        movimientosSoloCaminar.add(Movimiento.CAMINAR);

        int n = todasLasEstaciones.size();

        // =======================================================================
        // Cada nodo se conecta segun su POSICION ABSOLUTA.
        // Si el nodo i existe, sus conexiones van a ser SIEMPRE las mismas
        // independientemente de cuantos nodos tengamos en total
        // =======================================================================

        for (int i = 0; i < n; i++) {
            Estacion origen = todasLasEstaciones.get(i);

            // Conexion 1: Con el siguiente nodo (i+1) - SI EXISTE
            if (i + 1 < n) {
                Estacion destino = todasLasEstaciones.get(i + 1);
                int tiempo = 60 + (i % 5) * 20;
                todosLosDesplazamientos.add(new Desplazamiento(
                        origen, destino, movimientosTodos, tiempo
                ));
            }

            // Conexion 2: Con nodo i+3 - SI EXISTE
            if (i + 3 < n) {
                Estacion destino = todasLasEstaciones.get(i + 3);
                int tiempo = 90 + (i % 7) * 15;
                todosLosDesplazamientos.add(new Desplazamiento(
                        origen, destino, movimientosCaminosSaltos, tiempo
                ));
            }

            // Conexion 3: Con nodo i+10 - SI EXISTE
            if (i + 10 < n) {
                Estacion destino = todasLasEstaciones.get(i + 10);
                int tiempo = 150 + (i % 10) * 30;
                todosLosDesplazamientos.add(new Desplazamiento(
                        origen, destino, movimientosSoloCaminar, tiempo
                ));
            }

            // Conexion 4: Cada 5 nodos conecta con i+7 - SI EXISTE
            if (i % 5 == 0 && i + 7 < n) {
                Estacion destino = todasLasEstaciones.get(i + 7);
                int tiempo = 120 + (i % 8) * 25;
                todosLosDesplazamientos.add(new Desplazamiento(
                        origen, destino, movimientosTodos, tiempo
                ));
            }
        }

        // ═══════════════════════════════════════════════════════════════════════
        // CIERRE DEL CICLO: Conexiones de vuelta al origen
        // Estas tambien dependen solo de la posicion del nodo
        // ═══════════════════════════════════════════════════════════════════════

        Estacion primero = todasLasEstaciones.get(0); // Aula 101

        // Ultimo nodo -> origen
        if (n > 1) {
            Estacion ultimo = todasLasEstaciones.get(n - 1);
            todosLosDesplazamientos.add(new Desplazamiento(
                    ultimo, primero, movimientosTodos, 200
            ));

            // Ultimo -> segundo nodo (si existe)
            if (n > 2) {
                Estacion segundo = todasLasEstaciones.get(1);
                todosLosDesplazamientos.add(new Desplazamiento(
                        ultimo, segundo, movimientosCaminosSaltos, 180
                ));
            }
        }

        // Penultimo -> origen (si existe)
        if (n > 2) {
            Estacion penultimo = todasLasEstaciones.get(n - 2);
            todosLosDesplazamientos.add(new Desplazamiento(
                    penultimo, primero, movimientosTodos, 220
            ));
        }

        // Nodos en posiciones especificas -> origen
        // Solo conecta si ese indice existe
        int[] posicionesConectarOrigen = {16, 23, 30, 37, 44}; // Posiciones fijas
        for (int pos : posicionesConectarOrigen) {
            if (pos < n) {
                Estacion medio = todasLasEstaciones.get(pos);
                int tiempo = 250 + pos * 10;
                todosLosDesplazamientos.add(new Desplazamiento(
                        medio, primero, movimientosTodos, tiempo
                ));
            }
        }

        // =======================================================================
        // CONEXIONES BIDIRECCIONALES PARA TESTEAR PODA DE VISITADOS
        // =======================================================================

        System.out.println("\n CONEXIONES BIDIRECCIONALES AGREGADAS:");
        System.out.println("========================================");

        // Conexión bidireccional entre índices 5 (Aula 205) y 8 (Aula 301)
        if (n > 8) {
            Estacion nodo5 = todasLasEstaciones.get(5);
            Estacion nodo8 = todasLasEstaciones.get(8);

            // Ya existe 5 -> 8 (por i+3), ahora agregamos 8 -> 5
            todosLosDesplazamientos.add(new Desplazamiento(
                    nodo8, nodo5, movimientosCaminosSaltos, 85
            ));

            System.out.println("." + nodo5.getNombre() + " BIDIRECCIONAL CON " + nodo8.getNombre());
            System.out.println("    (Se podra volver de " + nodo8.getNombre() + " a " + nodo5.getNombre() + ")");
        }

        // Conexión bidireccional entre índices 12 (Aula 613) y 15 (Aula 689)
        if (n > 15) {
            Estacion nodo12 = todasLasEstaciones.get(12);
            Estacion nodo15 = todasLasEstaciones.get(15);

            // Ya existe 12 -> 15 (por i+3), ahora agregamos 15 -> 12
            todosLosDesplazamientos.add(new Desplazamiento(
                    nodo15, nodo12, movimientosTodos, 95
            ));

            System.out.println("." + nodo12.getNombre() + "BIDIRECCIONAL CON" + nodo15.getNombre());
            System.out.println("    (Se podra volver de " + nodo15.getNombre() + " a " + nodo12.getNombre() + ")");
        }

        // Conexión bidireccional entre índices 20 (Starbucks PB) y 22 (Molinete L3)
        if (n > 22) {
            Estacion nodo20 = todasLasEstaciones.get(20);
            Estacion nodo22 = todasLasEstaciones.get(22);

            // Ya existe 20 -> 22, ahora agregamos 22 -> 20
            todosLosDesplazamientos.add(new Desplazamiento(
                    nodo22, nodo20, movimientosSoloCaminar, 75
            ));

            System.out.println("  ✓ " + nodo20.getNombre() + " ⇄ " + nodo22.getNombre());
            System.out.println("    (Puedes volver de " + nodo22.getNombre() + " a " + nodo20.getNombre() + ")");
        }

        System.out.println("========================================================");
        System.out.println("Estas conexiones permitirán testear la condición de poda:");
        System.out.println("!visitadosCamino.contains(destino.getNombre())\n");
    }

    /**
     * Verifica si ya existe un desplazamiento entre origen y destino
     */
    private static boolean existeDesplazamiento(Estacion origen, Estacion destino) {
        for (Desplazamiento d : todosLosDesplazamientos) {
            if (d.getOrigen().getNombre().equals(origen.getNombre()) &&
                    d.getDestino().getNombre().equals(destino.getNombre())) {
                return true;
            }
        }
        return false;
    }

    /**
     * retorna todas las estaciones (40 nodos)
     */
    public static ArrayList<Estacion> obtenerTodasLasEstaciones() {
        if (todasLasEstaciones == null) {
            generarDataset();
        }
        return new ArrayList<>(todasLasEstaciones);
    }

    /**
     * retorna todos los desplazamientos
     */
    public static ArrayList<Desplazamiento> obtenerTodosLosDesplazamientos() {
        if (todosLosDesplazamientos == null) {
            generarDataset();
        }
        return new ArrayList<>(todosLosDesplazamientos);
    }

    /**
     * retorna el Aula 633 como origen (siempre va a existir en el dataset)
     */
    public static Estacion obtenerOrigen() {
        if (todasLasEstaciones == null) {
            generarDataset();
        }

        for (Estacion e : todasLasEstaciones) {
            if (e.getNombre().equals("Aula 633")) {
                return e;
            }
        }
        // si por algun motivo no existe, voy a devolver la primer estacion que tenga.
        return todasLasEstaciones.get(0);
    }

    /**
     * Selecciona N lugares obligatorios de forma fija.
     * Al aumentar cantidad, los anteriores se mantienen
     */
    public static ArrayList<Estacion> seleccionarObligatorios(int cantidad) {
        if (todasLasEstaciones == null) {
            generarDataset();
        }

        ArrayList<Estacion> obligatorios = new ArrayList<>();
        Estacion origen = obtenerOrigen();

        // Definir posiciones FIJAS para nodos obligatorios
        int[] posicionesFijas = {
                3, 8, 15, 22, 28, 35, 41, 47,  // Primeros 8 obligatorios
                5, 12, 19, 26, 33, 39, 45,     // Siguientes 7 (total 15)
                10, 17, 24, 31, 38, 43, 48     // Siguientes 7 (total 22)
                // Si necesitamos mas de 22 obligatorios los vamos agregando aca abajo
        };

        int agregados = 0;

        // Se seleccionan en orden fijo
        for (int i = 0; i < posicionesFijas.length && agregados < cantidad; i++) {
            int pos = posicionesFijas[i];

            // Verifica que el indice existe
            if (pos < todasLasEstaciones.size()) {
                Estacion candidata = todasLasEstaciones.get(pos);

                if (!candidata.getNombre().equals(origen.getNombre())) {
                    obligatorios.add(candidata);
                    agregados++;
                }
            }
        }

        // Si no llegamos a la cantidad (porque el dataset es muy chico),
        // agregamos los que faltan desde el principio (sin contar el origen)
        if (agregados < cantidad) {
            for (int i = 0; i < todasLasEstaciones.size() && agregados < cantidad; i++) {
                Estacion candidata = todasLasEstaciones.get(i);
                if (!candidata.getNombre().equals(origen.getNombre()) &&
                        !obligatorios.contains(candidata)) {
                    obligatorios.add(candidata);
                    agregados++;
                }
            }
        }

        return obligatorios;
    }

    /**
     * Imprimir estadisticas del dataset
     */
    public static void imprimirEstadisticas() {
        if (todasLasEstaciones == null) {
            generarDataset();
        }

        int aulas = 0;
        int noAulas = 0;

        for (Estacion e : todasLasEstaciones) {
            if (e.getEsAula()) {
                aulas++;
            } else {
                noAulas++;
            }
        }

        // Calcular promedio de vecinos
        int totalVecinos = 0;
        for (Estacion e : todasLasEstaciones) {
            int vecinos = 0;
            for (Desplazamiento d : todosLosDesplazamientos) {
                if (d.getOrigen().getNombre().equals(e.getNombre())) {
                    vecinos++;
                }
            }
            totalVecinos += vecinos;
        }
        double promedioVecinos = (double) totalVecinos / todasLasEstaciones.size();

        System.out.println("==========================================================");
        System.out.println("           ESTADISTICAS DEL DATASET                       ");
        System.out.println("==========================================================");
        System.out.println(" Total de estaciones: " + todasLasEstaciones.size());
        System.out.println(" Aulas (recargables): " + aulas);
        System.out.println(" Lugares comunes: " + noAulas);
        System.out.println(" Total desplazamientos: " + todosLosDesplazamientos.size());
        System.out.println(String.format(" Promedio vecinos por nodo: %.2f", promedioVecinos));
        System.out.println(" Estacion origen: " + obtenerOrigen().getNombre());
        System.out.println("==========================================================");
    }

    /**
     * Guardar la info del dataset:
     * - nombre de cada nodo
     * - a qué nodos puede ir y con qué movimiento
     */
    public static void guardarNodosEnArchivo(ArrayList<Estacion> todasLasEstaciones,
                                             ArrayList<Desplazamiento> todosLosDesplazamientos,
                                             ArrayList<Estacion> obligatorios) {
        File carpeta = new File("resultados");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        File archivo = new File(carpeta, "nodos.txt");

        try (FileWriter writer = new FileWriter(archivo, true)) {

            writer.write("=============================================\n");
            writer.write("EJECUCION DATASET: DatasetGrande (40 nodos)\n");
            writer.write("Total estaciones: " + todasLasEstaciones.size() + "\n");
            writer.write("Total desplazamientos: " + todosLosDesplazamientos.size() + "\n");
            writer.write("Total obligatorios: " + (obligatorios != null ? obligatorios.size() : 0) + "\n\n");

            for (Estacion est : todasLasEstaciones) {

                boolean esObligatorio = (obligatorios != null && obligatorios.contains(est));
                String flagObligatorio = esObligatorio ? "OBLIGATORIO" : "NO OBLIGATORIO";

                writer.write("Nodo: " + est.getNombre() + "  (" + flagObligatorio + ")\n");
                writer.write("Desplazamientos desde este nodo:\n");

                for (Desplazamiento d : todosLosDesplazamientos) {
                    if (d.getOrigen().equals(est)) {
                        writer.write("  -> " + d.getDestino().getNombre() + " [");

                        ArrayList<Movimiento> movs = d.getMovimientosPermitidos();
                        for (int i = 0; i < movs.size(); i++) {
                            writer.write(movs.get(i).name());
                            if (i < movs.size() - 1) {
                                writer.write(", ");
                            }
                        }

                        writer.write("] (tiempoBase=" + d.getTiempoBase() + ")\n");
                    }
                }

                writer.write("\n");
            }

            writer.write("=============================================\n\n");

        } catch (IOException e) {
            System.err.println("Error al guardar archivo de nodos: " + e.getMessage());
        }
    }
}