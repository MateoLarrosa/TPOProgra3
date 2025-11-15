package SolucionMisionUada.tests;

import MisionUada.Desplazamiento;
import MisionUada.Estacion;
import MisionUada.Movimiento;
import java.util.ArrayList;

/**
 * Factory que genera diferentes casos de prueba predefinidos
 * IMPORTANTE: UADA siempre arranca con 100% de bateria
 */
public class GeneradorCasosPrueba {

    /**
     * CASO 1: Prueba simple - Recorrido corto con bajo consumo
     */
    public static CasoDePrueba caso1Simple() {
        CasoDePrueba caso = new CasoDePrueba(
                "Caso 1 - Simple",
                "Recorrido corto. Consumo bajo de bateria"
        );

        caso.setBateriaInicial(100);

        // Estaciones
        Estacion aula633 = new Estacion("Aula 633", 633, true);
        Estacion starbucks = new Estacion("Starbucks", 1, false);
        Estacion molinete = new Estacion("Molinete", 3, false);
        Estacion aula613 = new Estacion("Aula 613", 613, true);

        caso.setOrigen(aula633);
        caso.getLugaresDisponibles().add(starbucks);
        caso.getLugaresDisponibles().add(molinete);
        caso.getLugaresDisponibles().add(aula613);

        caso.getLugaresObligatorios().add(starbucks);
        caso.getLugaresObligatorios().add(molinete);

        // Movimientos
        ArrayList<Movimiento> caminosSaltos = new ArrayList<>();
        caminosSaltos.add(Movimiento.CAMINAR);
        caminosSaltos.add(Movimiento.SALTAR);

        ArrayList<Movimiento> todos = new ArrayList<>();
        todos.add(Movimiento.CAMINAR);
        todos.add(Movimiento.SALTAR);
        todos.add(Movimiento.PATAS_ARRIBA);

        // Desplazamientos cortos (bajo consumo)
        caso.getDesplazamientos().add(new Desplazamiento(aula633, starbucks, caminosSaltos, 15));
        caso.getDesplazamientos().add(new Desplazamiento(starbucks, molinete, todos, 20));
        caso.getDesplazamientos().add(new Desplazamiento(molinete, aula613, caminosSaltos, 18));
        caso.getDesplazamientos().add(new Desplazamiento(aula613, aula633, todos, 10));

        return caso;
    }

    /**
     * CASO 2: Recorrido medio - Mayor consumo de bateria
     */
    public static CasoDePrueba caso2RecorridoMedio() {
        CasoDePrueba caso = new CasoDePrueba(
                "Caso 2 - Recorrido Medio",
                "Desplazamientos mas largos. Consumo moderado (30-40%)"
        );

        caso.setBateriaInicial(100);

        // Estaciones
        Estacion aula633 = new Estacion("Aula 633", 633, true);
        Estacion biblioteca = new Estacion("Biblioteca", 15, false);
        Estacion gimnasio = new Estacion("Gimnasio", 25, false);
        Estacion aula721 = new Estacion("Aula 721", 721, true);
        Estacion cafeteria = new Estacion("Cafeteria", 35, false);

        caso.setOrigen(aula633);
        caso.getLugaresDisponibles().add(biblioteca);
        caso.getLugaresDisponibles().add(gimnasio);
        caso.getLugaresDisponibles().add(aula721);
        caso.getLugaresDisponibles().add(cafeteria);

        caso.getLugaresObligatorios().add(biblioteca);
        caso.getLugaresObligatorios().add(gimnasio);
        caso.getLugaresObligatorios().add(cafeteria);

        // Movimientos
        ArrayList<Movimiento> caminosSaltos = new ArrayList<>();
        caminosSaltos.add(Movimiento.CAMINAR);
        caminosSaltos.add(Movimiento.SALTAR);

        ArrayList<Movimiento> todos = new ArrayList<>();
        todos.add(Movimiento.CAMINAR);
        todos.add(Movimiento.SALTAR);
        todos.add(Movimiento.PATAS_ARRIBA);

        // Desplazamientos mas largos (mayor consumo)
        caso.getDesplazamientos().add(new Desplazamiento(aula633, biblioteca, caminosSaltos, 300));      // 5 min
        caso.getDesplazamientos().add(new Desplazamiento(biblioteca, gimnasio, todos, 240));             // 4 min
        caso.getDesplazamientos().add(new Desplazamiento(gimnasio, aula721, caminosSaltos, 180));        // 3 min - AULA (recarga)
        caso.getDesplazamientos().add(new Desplazamiento(aula721, cafeteria, todos, 210));               // 3.5 min
        caso.getDesplazamientos().add(new Desplazamiento(cafeteria, aula633, caminosSaltos, 270));       // 4.5 min

        return caso;
    }

    /**
     * CASO 3: Recorrido largo - Alto consumo, necesita recargas estrategicas
     */
    public static CasoDePrueba caso3RecorridoLargo() {
        CasoDePrueba caso = new CasoDePrueba(
                "Caso 3 - Recorrido Largo",
                "Muchas estaciones. Alto consumo (60-70%). Debe aprovechar aulas para recargar"
        );

        caso.setBateriaInicial(100);

        // Estaciones
        Estacion aula633 = new Estacion("Aula 633", 633, true);
        Estacion recepcion = new Estacion("Recepcion", 10, false);
        Estacion aula515 = new Estacion("Aula 515", 515, true);   // Recarga: (5+1+5)/5 = 2.2
        Estacion laboratorio = new Estacion("Laboratorio", 45, false);
        Estacion aula999 = new Estacion("Aula 999", 999, true);   // Recarga: (9+9+9)/5 = 5.4
        Estacion auditorio = new Estacion("Auditorio", 55, false);
        Estacion terraza = new Estacion("Terraza", 65, false);

        caso.setOrigen(aula633);
        caso.getLugaresDisponibles().add(recepcion);
        caso.getLugaresDisponibles().add(aula515);
        caso.getLugaresDisponibles().add(laboratorio);
        caso.getLugaresDisponibles().add(aula999);
        caso.getLugaresDisponibles().add(auditorio);
        caso.getLugaresDisponibles().add(terraza);

        caso.getLugaresObligatorios().add(recepcion);
        caso.getLugaresObligatorios().add(laboratorio);
        caso.getLugaresObligatorios().add(auditorio);
        caso.getLugaresObligatorios().add(terraza);

        // Movimientos
        ArrayList<Movimiento> soloCaminar = new ArrayList<>();
        soloCaminar.add(Movimiento.CAMINAR);

        ArrayList<Movimiento> caminosSaltos = new ArrayList<>();
        caminosSaltos.add(Movimiento.CAMINAR);
        caminosSaltos.add(Movimiento.SALTAR);

        ArrayList<Movimiento> todos = new ArrayList<>();
        todos.add(Movimiento.CAMINAR);
        todos.add(Movimiento.SALTAR);
        todos.add(Movimiento.PATAS_ARRIBA);

        // Recorrido muy largo - fuerza a pasar por aulas para recargar
        caso.getDesplazamientos().add(new Desplazamiento(aula633, recepcion, soloCaminar, 420));         // 7 min (consume 7)
        caso.getDesplazamientos().add(new Desplazamiento(recepcion, aula515, caminosSaltos, 360));       // 6 min - AULA recarga
        caso.getDesplazamientos().add(new Desplazamiento(aula515, laboratorio, soloCaminar, 480));       // 8 min (consume 8)
        caso.getDesplazamientos().add(new Desplazamiento(laboratorio, aula999, todos, 300));             // 5 min - AULA recarga
        caso.getDesplazamientos().add(new Desplazamiento(aula999, auditorio, soloCaminar, 390));         // 6.5 min (consume 6.5)
        caso.getDesplazamientos().add(new Desplazamiento(auditorio, terraza, caminosSaltos, 420));       // 7 min
        caso.getDesplazamientos().add(new Desplazamiento(terraza, aula633, soloCaminar, 540));           // 9 min (consume 9)

        return caso;
    }

    /**
     * CASO 4: Recorrido critico - Consumo muy alto, bateria al limite
     */
    public static CasoDePrueba caso4RecorridoCritico() {
        CasoDePrueba caso = new CasoDePrueba(
                "Caso 4 - Recorrido Critico",
                "Desplazamientos MUY largos. Bateria al borde (80-95% consumo). Optimizacion critica"
        );

        caso.setBateriaInicial(100);

        // Estaciones
        Estacion aula633 = new Estacion("Aula 633", 633, true);
        Estacion puntoA = new Estacion("Punto Lejano A", 80, false);
        Estacion aula888 = new Estacion("Aula 888", 888, true);    // Recarga: (8+8+8)/5 = 4.8
        Estacion puntoB = new Estacion("Punto Lejano B", 90, false);
        Estacion aula777 = new Estacion("Aula 777", 777, true);    // Recarga: (7+7+7)/5 = 4.2
        Estacion destino = new Estacion("Destino Final", 95, false);

        caso.setOrigen(aula633);
        caso.getLugaresDisponibles().add(puntoA);
        caso.getLugaresDisponibles().add(aula888);
        caso.getLugaresDisponibles().add(puntoB);
        caso.getLugaresDisponibles().add(aula777);
        caso.getLugaresDisponibles().add(destino);

        caso.getLugaresObligatorios().add(puntoA);
        caso.getLugaresObligatorios().add(puntoB);
        caso.getLugaresObligatorios().add(destino);

        // Solo caminar (maximo consumo) o patas arriba (eficiente pero lento)
        ArrayList<Movimiento> soloEficiente = new ArrayList<>();
        soloEficiente.add(Movimiento.PATAS_ARRIBA);

        ArrayList<Movimiento> soloCaminar = new ArrayList<>();
        soloCaminar.add(Movimiento.CAMINAR);

        ArrayList<Movimiento> todos = new ArrayList<>();
        todos.add(Movimiento.CAMINAR);
        todos.add(Movimiento.SALTAR);
        todos.add(Movimiento.PATAS_ARRIBA);

        // Recorrido extremo - DEBE usar movimientos eficientes y pasar por aulas
        caso.getDesplazamientos().add(new Desplazamiento(aula633, puntoA, soloCaminar, 600));            // 10 min (consume 10!)
        caso.getDesplazamientos().add(new Desplazamiento(puntoA, aula888, todos, 540));                  // 9 min - AULA recarga
        caso.getDesplazamientos().add(new Desplazamiento(aula888, puntoB, soloCaminar, 660));            // 11 min (consume 11!)
        caso.getDesplazamientos().add(new Desplazamiento(puntoB, aula777, soloEficiente, 480));          // 8 min - AULA recarga
        caso.getDesplazamientos().add(new Desplazamiento(aula777, destino, todos, 420));                 // 7 min
        caso.getDesplazamientos().add(new Desplazamiento(destino, aula633, soloCaminar, 720));           // 12 min (consume 12!)

        return caso;
    }

    /**
     * CASO 5: Recorrido imposible - Bateria insuficiente incluso con recargas
     */
    public static CasoDePrueba caso5Imposible() {
        CasoDePrueba caso = new CasoDePrueba(
                "Caso 5 - Imposible",
                "Distancias extremas. NO hay solucion (consumiria >100% incluso con recargas)"
        );

        caso.setBateriaInicial(100);

        // Estaciones
        Estacion aula633 = new Estacion("Aula 633", 633, true);
        Estacion extremoA = new Estacion("Punto Extremo A", 5, false);
        Estacion aula111 = new Estacion("Aula 111", 111, true);    // Recarga minima: (1+1+1)/5 = 0.6
        Estacion extremoB = new Estacion("Punto Extremo B", 8, false);

        caso.setOrigen(aula633);
        caso.getLugaresDisponibles().add(extremoA);
        caso.getLugaresDisponibles().add(aula111);
        caso.getLugaresDisponibles().add(extremoB);

        caso.getLugaresObligatorios().add(extremoA);
        caso.getLugaresObligatorios().add(extremoB);

        // Solo caminar (maximo consumo)
        ArrayList<Movimiento> soloCaminar = new ArrayList<>();
        soloCaminar.add(Movimiento.CAMINAR);

        // Desplazamientos IMPOSIBLES de completar
        caso.getDesplazamientos().add(new Desplazamiento(aula633, extremoA, soloCaminar, 1200));         // 20 min (consume 20!)
        caso.getDesplazamientos().add(new Desplazamiento(extremoA, aula111, soloCaminar, 1080));         // 18 min - AULA (recarga 0.6)
        caso.getDesplazamientos().add(new Desplazamiento(aula111, extremoB, soloCaminar, 1200));         // 20 min (consume 20!)
        caso.getDesplazamientos().add(new Desplazamiento(extremoB, aula633, soloCaminar, 1440));         // 24 min (consume 24!)
        // Total: ~82 de consumo, con recarga de 0.6 -> IMPOSIBLE

        return caso;
    }

    /**
     * Retorna todos los casos de prueba
     */
    public static ArrayList<CasoDePrueba> obtenerTodosCasos() {
        ArrayList<CasoDePrueba> casos = new ArrayList<>();
        casos.add(caso1Simple());
        casos.add(caso2RecorridoMedio());
        casos.add(caso3RecorridoLargo());
        casos.add(caso4RecorridoCritico());
        casos.add(caso5Imposible());
        return casos;
    }
}