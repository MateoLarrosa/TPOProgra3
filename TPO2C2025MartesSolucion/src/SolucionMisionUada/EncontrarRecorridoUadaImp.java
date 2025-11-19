package SolucionMisionUada;

import MisionUada.Decision;
import MisionUada.Desplazamiento;
import MisionUada.EncontrarRecorridoUada;
import MisionUada.Estacion;
import MisionUada.Movimiento;

import java.util.ArrayList;
import java.util.HashSet;

public class EncontrarRecorridoUadaImp implements EncontrarRecorridoUada {

    // Clase auxiliar para representar un vecino con su movimiento
    private static class VecinoConMovimiento {
        Estacion destino;
        Movimiento movimiento;
        float tiempoBase;

        public VecinoConMovimiento(Estacion destino, Movimiento movimiento, float tiempoBase) {
            this.destino = destino;
            this.movimiento = movimiento;
            this.tiempoBase = tiempoBase;
        }
    }

    // Clase para almacenar la mejor solucion encontrada
    private static class SolucionOptima {
        float tiempoTotal;
        ArrayList<Decision> camino;

        public SolucionOptima() {
            this.tiempoTotal = Float.MAX_VALUE;
            this.camino = new ArrayList<>();
        }
    }

    @Override
    public ArrayList<Decision> encontrarSecuenciaRecorridoUada(
            int bateriaInicial,
            Estacion origen,
            ArrayList<Estacion> lugaresDisponibles,
            ArrayList<Estacion> lugaresObligatorios,
            ArrayList<Desplazamiento> desplazamientos) {

        // Inicializar estructuras
        SolucionOptima mejor = new SolucionOptima();
        ArrayList<Decision> caminoActual = new ArrayList<>();
        HashSet<String> visitadosCamino = new HashSet<>();
        HashSet<String> obligatoriosPendientes = new HashSet<>();

        // Convertir lugares obligatorios a conjunto de IDs
        for (Estacion est : lugaresObligatorios) {
            obligatoriosPendientes.add(est.getNombre());
        }

        // Agregar el origen como visitado
        visitadosCamino.add(origen.getNombre());

        // Llamar al backtracking
        encontrarRecorridoUADA(
                origen,
                origen, // El objetivo es volver al origen (633)
                obligatoriosPendientes,
                bateriaInicial,
                0.0f,
                mejor,
                caminoActual,
                desplazamientos,
                visitadosCamino
        );

        return mejor.camino;
    }

    /**
     * Funcion principal de backtracking que explora todos los caminos posibles
     */
    private void encontrarRecorridoUADA(
            Estacion actual,
            Estacion objetivo,
            HashSet<String> lugaresObligatoriosPend,
            float bateria,
            float tiempoAcumulado,
            SolucionOptima mejor,
            ArrayList<Decision> camino,
            ArrayList<Desplazamiento> desplazamientos,
            HashSet<String> visitadosCamino) {

        // Obtener todos los vecinos con sus movimientos permitidos
        ArrayList<VecinoConMovimiento> vecinos = vecinosConMovimientos(actual, desplazamientos);

        // Explorar cada vecino
        for (VecinoConMovimiento vecinoActual : vecinos) {
            Estacion destino = vecinoActual.destino;
            Movimiento mov = vecinoActual.movimiento;
            float tBase = vecinoActual.tiempoBase;

            // Copiar el conjunto de obligatorios para poder restaurarlo despues
            HashSet<String> obligatoriosInicial = copiarConjunto(lugaresObligatoriosPend);

            // Calcular tiempo y bateria usados
            float[] costos = costoTiempoYBateria(mov, tBase);
            float tUso = costos[0];
            float bUso = costos[1];
            float nuevaBateria = bateria - bUso;
            float tiempoAcumNuevo = tiempoAcumulado + tUso;

            // PODA 1: Bateria agotada en estacion no recargable (PRIMER IF del pseudocodigo)
            // si NO (nuevaBateria == 0 Y No actual.esAula Y destino.id <> objetivo.id entonces)
            if ( true ) {//!(nuevaBateria == 0.0 && !actual.getEsAula() && !destino.getNombre().equals(objetivo.getNombre()))) {

                // PODAS 2, 3 y 4: Condiciones principales de validez del camino (SEGUNDO IF del pseudocodigo)
                // PODA 2: tiempoAcumNuevo < mejor.tiempoTotal (peor tiempo)
                // PODA 3: nuevaBateria >= 0.0 (bateria negativa)
                // PODA 4: no (destino.id pertenece visitadosCamino) (repeticion de estacion)
                //         EXCEPCION: se permite volver al objetivo (633) aunque este visitado
                if (tiempoAcumNuevo < mejor.tiempoTotal &&
                        nuevaBateria >= 0.0 &&
                        (destino.getNombre().equals(objetivo.getNombre()) || !visitadosCamino.contains(destino.getNombre()))) {
                // Si llegamos aca, es un camino valido para explorar

                    // Eliminar el actual de obligatorios si corresponde
                    if (lugaresObligatoriosPend.contains(actual.getNombre())) {
                        lugaresObligatoriosPend.remove(actual.getNombre());
                    }

                    // Si el actual es un aula, recargar bateria
                    if (actual.getEsAula()) {
                        nuevaBateria = recargaAula(actual, nuevaBateria);
                    }

                    // Agregar la decision al camino
                    agregarDecision(camino, actual, destino, mov, tiempoAcumNuevo, nuevaBateria);

                    // Marcar destino como visitado
                    visitadosCamino.add(destino.getNombre());

                    // Verificar si llegamos al objetivo
                    if (destino.getNombre().equals(objetivo.getNombre()) && lugaresObligatoriosPend.isEmpty()) {
                        // SOLUCION ENCONTRADA
                        if (tiempoAcumNuevo < mejor.tiempoTotal) {
                            mejor.tiempoTotal = tiempoAcumNuevo;
                            mejor.camino = copiarCamino(camino);
                        }
                    } else {
                        // Continuar explorando recursivamente
                        encontrarRecorridoUADA(
                                destino,
                                objetivo,
                                copiarConjunto(lugaresObligatoriosPend),
                                nuevaBateria,
                                tiempoAcumNuevo,
                                mejor,
                                camino,
                                desplazamientos,
                                visitadosCamino
                        );
                    }

                    // Deshacer cambios
                    eliminarDecision(camino);
                    visitadosCamino.remove(destino.getNombre());
                    lugaresObligatoriosPend = copiarConjunto(obligatoriosInicial);
                }
            }
        }
    }

    /**
     * Suma los digitos de un numero
     * Complejidad: O(d) donde d es la cantidad de digitos, considerado O(1) para numeros de aulas
     */
    private int sumarDigitos(int n) {
        int suma = 0;
        while (n > 0) {
            suma += n % 10;
            n /= 10;
        }
        return suma;
    }

    /**
     * Calcula la recarga de bateria en un aula
     * Complejidad: O(1)
     */
    private float recargaAula(Estacion e, float bateria) {
        float batCargada = bateria + (sumarDigitos(e.getIdentificadorNumerico()) / 5.0f);
        return Math.min(100.0f, batCargada);
    }

    /**
     * Calcula el tiempo usado y el consumo de bateria segun el movimiento
     * Complejidad: O(1)
     *
     * @return array [tiempoUsado, bateriaUsada]
     */
    private float[] costoTiempoYBateria(Movimiento mov, float tBase) {
        float tMin = tBase / 60.0f;
        float tiempoUsado, bateriaUsada;

        switch (mov) {
            case CAMINAR:
                tiempoUsado = tBase;
                bateriaUsada = 1.0f * tMin;
                break;
            case SALTAR:
                tiempoUsado = tBase / 2.0f;
                bateriaUsada = 2.2f * tMin; // 2.2x el consumo de caminar en mitad de tiempo
                break;
            case PATAS_ARRIBA:
                tiempoUsado = 2.0f * tBase;
                bateriaUsada = 0.45f * tMin; // 55% menos que caminar, en doble tiempo
                break;
            default:
                tiempoUsado = tBase;
                bateriaUsada = tMin;
                break;
        }

        return new float[]{tiempoUsado, bateriaUsada};
    }

    /**
     * Obtiene todos los vecinos alcanzables desde una estacion con sus movimientos permitidos
     * Complejidad: O(n2) en el peor caso
     */
    private ArrayList<VecinoConMovimiento> vecinosConMovimientos(
            Estacion origen,
            ArrayList<Desplazamiento> desplazamientos) {

        ArrayList<VecinoConMovimiento> vecinos = new ArrayList<>();

        // Recorrer todos los desplazamientos
        for (Desplazamiento d : desplazamientos) {
            // Verificar si el desplazamiento sale desde el origen actual
            if (d.getOrigen().getNombre().equals(origen.getNombre())) {
                // Para cada movimiento permitido, crear un vecino
                for (Movimiento m : d.getMovimientosPermitidos()) {
                    vecinos.add(new VecinoConMovimiento(
                            d.getDestino(),
                            m,
                            d.getTiempoBase()
                    ));
                }
            }
        }

        return vecinos;
    }

    /**
     * Copia un camino (array de decisiones)
     * Complejidad: O(L) donde L es la longitud del camino
     */
    private ArrayList<Decision> copiarCamino(ArrayList<Decision> camino) {
        return new ArrayList<>(camino);
    }

    /**
     * Copia un conjunto de strings
     * Complejidad: O(S) donde S es el tamano del conjunto, pero S <= 3 en este problema -> O(1)
     */
    private HashSet<String> copiarConjunto(HashSet<String> conjunto) {
        return new HashSet<>(conjunto);
    }

    /**
     * Agrega una decision al final del camino
     * Complejidad: O(1)
     */
    private void agregarDecision(
            ArrayList<Decision> camino,
            Estacion origen,
            Estacion destino,
            Movimiento mov,
            float tiempoAcumulado,
            float bateriaRemanente) {

        //  Cast temporal a int - sacarlo cuando la libreria sea corregida
        Decision nuevaDecision = new Decision(
                origen,
                destino,
                mov,
                bateriaRemanente,      // Cast temporal
                tiempoAcumulado        // Cast temporal
        );
        camino.add(nuevaDecision);
    }

    /**
     * Elimina la ultima decision del camino
     * Complejidad: O(1)
     */
    private void eliminarDecision(ArrayList<Decision> camino) {
        if (!camino.isEmpty()) {
            camino.remove(camino.size() - 1);
        }
    }
}