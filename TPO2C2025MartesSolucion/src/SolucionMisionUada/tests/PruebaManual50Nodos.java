package SolucionMisionUada.tests;

import MisionUada.Decision;
import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import MisionUada.Movimiento;
import SolucionMisionUada.EncontrarRecorridoUadaImp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * DATASET MANUAL - 50 NODOS / 5 OBLIGATORIOS
 * Estructura clara y modular para agregar/quitar nodos facilmente
 */
public class PruebaManual50Nodos {
    public static void main(String[] args) {

        System.out.println("═════════════════════════════════════════════════");
        System.out.println("    DATASET MANUAL - 50 NODOS / 5 OBLIGATORIOS");
        System.out.println("═════════════════════════════════════════════════\n");

        int bateriaInicial = 100;
        ArrayList<Estacion> lugaresDisponibles = new ArrayList<>();
        ArrayList<Estacion> lugaresObligatorios = new ArrayList<>();

        // ════════════════════════════════════════════════════════
        // ORIGEN (SIEMPRE AULA 633)
        // ════════════════════════════════════════════════════════
        Estacion origen = new Estacion("Aula 633", 633, true);

        // ════════════════════════════════════════════════════════
        // 24 AULAS ADICIONALES (25 total con origen)
        // ════════════════════════════════════════════════════════
        Estacion aula101 = new Estacion("Aula 101", 101, true);
        Estacion aula201 = new Estacion("Aula 201", 201, true);
        Estacion aula301 = new Estacion("Aula 301", 301, true);
        Estacion aula401 = new Estacion("Aula 401", 401, true);
        Estacion aula501 = new Estacion("Aula 501", 501, true);

        Estacion aula110 = new Estacion("Aula 110", 110, true);
        Estacion aula210 = new Estacion("Aula 210", 210, true);
        Estacion aula310 = new Estacion("Aula 310", 310, true);
        Estacion aula410 = new Estacion("Aula 410", 410, true);
        Estacion aula510 = new Estacion("Aula 510", 510, true);

        Estacion aula120 = new Estacion("Aula 120", 120, true);
        Estacion aula220 = new Estacion("Aula 220", 220, true);
        Estacion aula320 = new Estacion("Aula 320", 320, true);
        Estacion aula420 = new Estacion("Aula 420", 420, true);
        Estacion aula520 = new Estacion("Aula 520", 520, true);

        Estacion aula710 = new Estacion("Aula 710", 710, true);
        Estacion aula810 = new Estacion("Aula 810", 810, true);
        Estacion aula910 = new Estacion("Aula 910", 910, true);

        Estacion aula720 = new Estacion("Aula 720", 720, true);
        Estacion aula820 = new Estacion("Aula 820", 820, true);
        Estacion aula920 = new Estacion("Aula 920", 920, true);

        Estacion aula730 = new Estacion("Aula 730", 730, true);
        Estacion aula830 = new Estacion("Aula 830", 830, true);
        Estacion aula930 = new Estacion("Aula 930", 930, true);


        Estacion aula640 = new Estacion("Aula 640", 640, true);
        Estacion aula650 = new Estacion("Aula 650", 650, true);
        Estacion aula660 = new Estacion("Aula 660", 660, true);
        Estacion aula670 = new Estacion("Aula 670", 670, true);
        Estacion aula680 = new Estacion("Aula 680", 680, true);

        // ════════════════════════════════════════════════════════
        // 25 LUGARES COMUNES (NO RECARGABLES)
        // ════════════════════════════════════════════════════════
        Estacion starbucks = new Estacion("Starbucks PB", 1, false);
        Estacion molinete1 = new Estacion("Molinete L1", 2, false);
        Estacion molinete2 = new Estacion("Molinete L2", 3, false);
        Estacion recepcion = new Estacion("Recepcion", 4, false);
        Estacion buffet = new Estacion("Buffet", 5, false);

        Estacion pasillo1 = new Estacion("Pasillo P1", 6, false);
        Estacion pasillo2 = new Estacion("Pasillo P2", 7, false);
        Estacion pasillo3 = new Estacion("Pasillo P3", 8, false);
        Estacion pasillo4 = new Estacion("Pasillo P4", 9, false);
        Estacion pasillo5 = new Estacion("Pasillo P5", 10, false);

        Estacion biblioteca1 = new Estacion("Biblioteca P1", 11, false);
        Estacion biblioteca2 = new Estacion("Biblioteca P2", 12, false);
        Estacion biblioteca3 = new Estacion("Biblioteca P3", 13, false);

        Estacion laboratorio1 = new Estacion("Laboratorio P1", 14, false);
        Estacion laboratorio2 = new Estacion("Laboratorio P2", 15, false);

        Estacion gimnasio = new Estacion("Gimnasio P7", 16, false);
        Estacion terraza = new Estacion("Terraza P9", 17, false);
        Estacion auditorio = new Estacion("Auditorio P8", 18, false);

        Estacion cafeteria = new Estacion("Cafeteria Central", 19, false);
        Estacion secretaria = new Estacion("Secretaria", 20, false);

        Estacion sala1 = new Estacion("Sala Reunion 1", 21, false);
        Estacion sala2 = new Estacion("Sala Reunion 2", 22, false);
        Estacion sala3 = new Estacion("Sala Reunion 3", 23, false);

        Estacion copiado = new Estacion("Centro Copiado", 24, false);
        Estacion deposito = new Estacion("Deposito", 25, false);



        Estacion printCenter = new Estacion("Print Center 2", 26, false);
        Estacion vending = new Estacion("Vending Machine P4", 27, false);
        Estacion hallCentral = new Estacion("Hall Central", 28, false);
        Estacion escaleraNorte = new Estacion("Escalera Norte", 29, false);
        Estacion terrazaTech = new Estacion("Terraza Tech P10", 30, false);

        // ════════════════════════════════════════════════════════
        // AGREGAR TODOS LOS NODOS A LUGARES DISPONIBLES
        // ════════════════════════════════════════════════════════
        // Aulas
        lugaresDisponibles.add(aula101);
        lugaresDisponibles.add(aula201);
        lugaresDisponibles.add(aula301);
        lugaresDisponibles.add(aula401);
        lugaresDisponibles.add(aula501);
        lugaresDisponibles.add(aula110);
        lugaresDisponibles.add(aula210);
        lugaresDisponibles.add(aula310);
        lugaresDisponibles.add(aula410);
        lugaresDisponibles.add(aula510);
        lugaresDisponibles.add(aula120);
        lugaresDisponibles.add(aula220);
        lugaresDisponibles.add(aula320);
        lugaresDisponibles.add(aula420);
        lugaresDisponibles.add(aula520);
        lugaresDisponibles.add(aula710);
        lugaresDisponibles.add(aula810);
        lugaresDisponibles.add(aula910);
        lugaresDisponibles.add(aula720);
        lugaresDisponibles.add(aula820);
        lugaresDisponibles.add(aula920);
        lugaresDisponibles.add(aula730);
        lugaresDisponibles.add(aula830);
        lugaresDisponibles.add(aula930);



        lugaresDisponibles.add(aula640);
        lugaresDisponibles.add(aula650);
        lugaresDisponibles.add(aula660);
        lugaresDisponibles.add(aula670);
        lugaresDisponibles.add(aula680);

        // Lugares comunes
        lugaresDisponibles.add(starbucks);
        lugaresDisponibles.add(molinete1);
        lugaresDisponibles.add(molinete2);
        lugaresDisponibles.add(recepcion);
        lugaresDisponibles.add(buffet);
        lugaresDisponibles.add(pasillo1);
        lugaresDisponibles.add(pasillo2);
        lugaresDisponibles.add(pasillo3);
        lugaresDisponibles.add(pasillo4);
        lugaresDisponibles.add(pasillo5);
        lugaresDisponibles.add(biblioteca1);
        lugaresDisponibles.add(biblioteca2);
        lugaresDisponibles.add(biblioteca3);
        lugaresDisponibles.add(laboratorio1);
        lugaresDisponibles.add(laboratorio2);
        lugaresDisponibles.add(gimnasio);
        lugaresDisponibles.add(terraza);
        lugaresDisponibles.add(auditorio);
        lugaresDisponibles.add(cafeteria);
        lugaresDisponibles.add(secretaria);
        lugaresDisponibles.add(sala1);
        lugaresDisponibles.add(sala2);
        lugaresDisponibles.add(sala3);
        lugaresDisponibles.add(copiado);
        lugaresDisponibles.add(deposito);


        lugaresDisponibles.add(printCenter);
        lugaresDisponibles.add(vending);
        lugaresDisponibles.add(hallCentral);
        lugaresDisponibles.add(escaleraNorte);
        lugaresDisponibles.add(terrazaTech);




        // ════════════════════════════════════════════════════════
        // 5 LUGARES OBLIGATORIOS (distribuidos estrategicamente)
        // ════════════════════════════════════════════════════════
        lugaresObligatorios.add(starbucks);      // Obligatorio 1
        lugaresObligatorios.add(biblioteca1);    // Obligatorio 2
        lugaresObligatorios.add(aula310);        // Obligatorio 3
        lugaresObligatorios.add(gimnasio);       // Obligatorio 4
        lugaresObligatorios.add(terraza);        // Obligatorio 5


        lugaresObligatorios.add(aula640);     // Obligatorio 6
        lugaresObligatorios.add(printCenter); // Obligatorio 7
        lugaresObligatorios.add(hallCentral); // Obligatorio 8
        lugaresObligatorios.add(aula660);     // Obligatorio 9
        lugaresObligatorios.add(terrazaTech); // Obligatorio 10


        // ════════════════════════════════════════════════════════
        // DESPLAZAMIENTOS - GRAFO CONECTADO EN CADENA
        // ════════════════════════════════════════════════════════
        ArrayList<Desplazamiento> desplazamientos = new ArrayList<>();

        ArrayList<Movimiento> todos = new ArrayList<>();
        todos.add(Movimiento.CAMINAR);
        todos.add(Movimiento.SALTAR);
        todos.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> caminosSaltos = new ArrayList<>();
        caminosSaltos.add(Movimiento.CAMINAR);
        caminosSaltos.add(Movimiento.SALTAR);

        // CADENA PRINCIPAL: 633 -> ... -> Terraza -> 633
        // Asegura que TODOS los obligatorios esten en el camino

        desplazamientos.add(new Desplazamiento(origen, starbucks, todos, 60));        // 633 -> Starbucks (OBLIG 1)
        desplazamientos.add(new Desplazamiento(starbucks, molinete1, todos, 50));
        desplazamientos.add(new Desplazamiento(molinete1, recepcion, todos, 45));
        desplazamientos.add(new Desplazamiento(recepcion, aula101, todos, 70));
        desplazamientos.add(new Desplazamiento(aula101, pasillo1, todos, 40));
        desplazamientos.add(new Desplazamiento(pasillo1, biblioteca1, todos, 55));    // -> Biblioteca (OBLIG 2)
        desplazamientos.add(new Desplazamiento(biblioteca1, aula201, todos, 65));
        desplazamientos.add(new Desplazamiento(aula201, pasillo2, todos, 50));
        desplazamientos.add(new Desplazamiento(pasillo2, aula301, todos, 60));
        desplazamientos.add(new Desplazamiento(aula301, aula310, todos, 35));         // -> Aula 310 (OBLIG 3)
        desplazamientos.add(new Desplazamiento(aula310, laboratorio1, todos, 75));
        desplazamientos.add(new Desplazamiento(laboratorio1, aula401, todos, 80));
        desplazamientos.add(new Desplazamiento(aula401, pasillo4, todos, 55));
        desplazamientos.add(new Desplazamiento(pasillo4, aula501, todos, 60));
        desplazamientos.add(new Desplazamiento(aula501, sala1, todos, 70));
        desplazamientos.add(new Desplazamiento(sala1, aula710, todos, 90));
        desplazamientos.add(new Desplazamiento(aula710, gimnasio, todos, 65));        // -> Gimnasio (OBLIG 4)
        desplazamientos.add(new Desplazamiento(gimnasio, aula810, todos, 75));
        desplazamientos.add(new Desplazamiento(aula810, auditorio, todos, 60));
        desplazamientos.add(new Desplazamiento(auditorio, aula910, todos, 70));
        desplazamientos.add(new Desplazamiento(aula910, terraza, todos, 85));         // -> Terraza (OBLIG 5)
        desplazamientos.add(new Desplazamiento(terraza, origen, todos, 200));         // Vuelta al origen

        // CONEXIONES ADICIONALES (para opciones alternativas)
        desplazamientos.add(new Desplazamiento(origen, aula101, caminosSaltos, 80));
        desplazamientos.add(new Desplazamiento(aula201, aula301, caminosSaltos, 90));
        desplazamientos.add(new Desplazamiento(aula401, aula501, caminosSaltos, 85));
        desplazamientos.add(new Desplazamiento(aula710, aula810, caminosSaltos, 95));
        desplazamientos.add(new Desplazamiento(aula810, aula910, caminosSaltos, 100));

        // Shortcuts al origen desde puntos intermedios
        desplazamientos.add(new Desplazamiento(laboratorio1, origen, todos, 250));
        desplazamientos.add(new Desplazamiento(gimnasio, origen, todos, 280));
        desplazamientos.add(new Desplazamiento(auditorio, origen, todos, 270));


        // Nueva conectividad para los nodos agregados
        desplazamientos.add(new Desplazamiento(aula501, aula640, todos, 90));
        desplazamientos.add(new Desplazamiento(aula640, aula650, todos, 40));
        desplazamientos.add(new Desplazamiento(aula650, aula660, todos, 35));
        desplazamientos.add(new Desplazamiento(aula660, aula670, todos, 50));
        desplazamientos.add(new Desplazamiento(aula670, aula680, todos, 45));
        desplazamientos.add(new Desplazamiento(aula680, escaleraNorte, todos, 60));
        desplazamientos.add(new Desplazamiento(escaleraNorte, hallCentral, todos, 55));
        desplazamientos.add(new Desplazamiento(hallCentral, printCenter, todos, 50));
        desplazamientos.add(new Desplazamiento(printCenter, vending, todos, 40));
        desplazamientos.add(new Desplazamiento(vending, terrazaTech, todos, 95));
        desplazamientos.add(new Desplazamiento(terrazaTech, origen, todos, 210));



        // ════════════════════════════════════════════════════════
        // EJECUTAR ALGORITMO
        // ════════════════════════════════════════════════════════
        System.out.println("Configuracion:");
        System.out.println("  - Total nodos: " + (lugaresDisponibles.size() + 1) + " (49 + origen)");
        System.out.println("  - Obligatorios: " + lugaresObligatorios.size());
        System.out.println("  - Desplazamientos: " + desplazamientos.size());
        System.out.println("  - Bateria inicial: " + bateriaInicial + "%\n");

        System.out.println("Lugares obligatorios:");
        for (int i = 0; i < lugaresObligatorios.size(); i++) {
            System.out.println("  " + (i+1) + ". " + lugaresObligatorios.get(i).getNombre());
        }
        System.out.println();

        System.out.println("Ejecutando algoritmo de backtracking...\n");

        EncontrarRecorridoUadaImp algoritmo = new EncontrarRecorridoUadaImp();

        long tiempoInicio = System.currentTimeMillis();
        ArrayList<Decision> solucion = algoritmo.encontrarSecuenciaRecorridoUada(
                bateriaInicial,
                origen,
                lugaresDisponibles,
                lugaresObligatorios,
                desplazamientos
        );
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        // ════════════════════════════════════════════════════════
        // MOSTRAR RESULTADOS
        // ════════════════════════════════════════════════════════
        System.out.println("═════════════════════════════════════════════════");
        System.out.println("                 RESULTADO");
        System.out.println("═════════════════════════════════════════════════");
        System.out.println("Tiempo de ejecucion: " + tiempoTotal + " ms (" + (tiempoTotal/1000.0) + " seg)\n");

        if (solucion.isEmpty()) {
            System.out.println("❌ NO SE ENCONTRO SOLUCION");
        } else {
            System.out.println("✅ SOLUCION ENCONTRADA");
            System.out.println("Decisiones: " + solucion.size());
            Decision ultima = solucion.get(solucion.size() - 1);
            System.out.println("Tiempo total recorrido: " + ultima.getTiempoAcumulado() + " seg");
            System.out.println("Bateria final: " + ultima.getBateriaRemanente() + "%");
            System.out.println("Bateria consumida: " + (bateriaInicial - ultima.getBateriaRemanente()) + "%");
        }

        guardarResultado(solucion, tiempoTotal, lugaresObligatorios.size());
    }

    private static void guardarResultado(ArrayList<Decision> solucion, long tiempo, int obligatorios) {
        StringBuilder sb = new StringBuilder();
        sb.append("═════════════════════════════════════════════════\n");
        sb.append("  DATASET MANUAL - 50 NODOS / ").append(obligatorios).append(" OBLIGATORIOS\n");
        sb.append("═════════════════════════════════════════════════\n\n");
        sb.append("Tiempo: ").append(tiempo).append(" ms\n\n");

        if (solucion.isEmpty()) {
            sb.append("❌ SIN SOLUCION\n");
        } else {
            sb.append("✅ SOLUCION ENCONTRADA\n\n");
            for (int i = 0; i < solucion.size(); i++) {
                Decision d = solucion.get(i);
                sb.append("Decision ").append(i+1).append(":\n");
                sb.append("  ").append(d.getOrigen().getNombre()).append(" -> ");
                sb.append(d.getDestino().getNombre()).append("\n");
                sb.append("  Movimiento: ").append(d.getMovimientoEmpleado()).append("\n");
                sb.append("  Bateria: ").append(d.getBateriaRemanente()).append("%\n");
                sb.append("  Tiempo: ").append(d.getTiempoAcumulado()).append(" seg\n\n");
            }
        }

        try (FileWriter w = new FileWriter("output_manual_50.txt")) {
            w.write(sb.toString());
            System.out.println("\n✅ Guardado en: output_manual_50.txt");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}