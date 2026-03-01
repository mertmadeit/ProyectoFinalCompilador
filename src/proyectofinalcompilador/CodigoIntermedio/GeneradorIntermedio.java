package proyectofinalcompilador.CodigoIntermedio;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de Código Intermedio (Tres Direcciones)
 */
public class GeneradorIntermedio {
    private List<String> instrucciones;
    private int contadorTemporales;

    public GeneradorIntermedio() {
        instrucciones = new ArrayList<>();
        contadorTemporales = 0;
    }

    public String nuevaTemporal() {
        return "T" + (++contadorTemporales);
    }

    public void agregarInstruccion(String inst) {
        instrucciones.add(inst);
    }

    public void agregarAsignacion(String destino, String origen) {
        instrucciones.add(destino + " = " + origen);
    }

    public void agregarOperacion(String destino, String op1, String operador, String op2) {
        instrucciones.add(destino + " = " + op1 + " " + operador + " " + op2);
    }

    public void agregarLlamada(String funcion, String arg) {
        instrucciones.add("PARAM " + arg);
        instrucciones.add("CALL " + funcion + ", 1");
    }

    public String getCodigoCompleto() {
        StringBuilder sb = new StringBuilder();
        for (String inst : instrucciones) {
            sb.append(inst).append("\n");
        }
        return sb.toString();
    }

    public void limpiar() {
        instrucciones.clear();
        contadorTemporales = 0;
    }
}
