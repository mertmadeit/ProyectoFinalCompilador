package proyectofinalcompilador.CodigoIntermedio;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de Código Intermedio (Tres Direcciones)
 */
public class GeneradorIntermedio {
    private List<String> instrucciones;
    private int contadorTemporales;
    private int contadorEtiquetas;

    public GeneradorIntermedio() {
        instrucciones = new ArrayList<>();
        contadorTemporales = 0;
        contadorEtiquetas = 0;
    }

    public String nuevaTemporal() {
        return "T" + (++contadorTemporales);
    }

    public String nuevaEtiqueta() {
        return "L" + (++contadorEtiquetas);
    }

    public void agregarInstruccion(String inst) {
        instrucciones.add(inst);
    }

    public void insertarInstruccion(int indice, String inst) {
        if (indice < 0 || indice > instrucciones.size()) {
            instrucciones.add(inst);
            return;
        }
        instrucciones.add(indice, inst);
    }

    public int tamanoInstrucciones() {
        return instrucciones.size();
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

    public void agregarImpresion(String valor) {
        instrucciones.add("PRINT " + valor);
    }

    public void agregarLectura(String variable) {
        instrucciones.add("READ " + variable);
    }

    public void agregarEtiqueta(String etiqueta) {
        instrucciones.add(etiqueta + ":");
    }

    public void agregarSaltoCondicionalFalso(String condicion, String etiqueta) {
        instrucciones.add("IF_FALSE " + condicion + " GOTO " + etiqueta);
    }

    public void insertarSaltoCondicionalFalso(int indice, String condicion, String etiqueta) {
        insertarInstruccion(indice, "IF_FALSE " + condicion + " GOTO " + etiqueta);
    }

    public void agregarSaltoIncondicional(String etiqueta) {
        instrucciones.add("GOTO " + etiqueta);
    }

    public void insertarSaltoIncondicional(int indice, String etiqueta) {
        insertarInstruccion(indice, "GOTO " + etiqueta);
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
        contadorEtiquetas = 0;
    }
}
