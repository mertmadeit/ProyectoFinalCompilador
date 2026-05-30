package proyectofinalcompilador.Compilador;

import java.io.StringReader;

import proyectofinalcompilador.CodigoObjeto.GeneradorObjeto;
import proyectofinalcompilador.Lexico.LexerCup;
import proyectofinalcompilador.Semantico.TablaSimbolos;
import proyectofinalcompilador.Sintactico.sintax;

/**
 * Compilador sintactico y semantico.
 */
public class CompiladorSintactico {

    private String resultado;
    private String error;
    private String codigoIntermedio;
    private String codigoObjeto;
    private TablaSimbolos tablaSimbolos;

    public CompiladorSintactico() {
        resultado = "";
        error = "";
        codigoIntermedio = "";
        codigoObjeto = "";
        tablaSimbolos = new TablaSimbolos();
    }

    /**
     * Analiza codigo fuente y construye resultado sintactico, tabla semantica y
     * codigo intermedio/objeto.
     */
    @SuppressWarnings("deprecation")
    public boolean analizar(String codigoFuente) {
        resultado = "";
        error = "";
        codigoIntermedio = "";
        codigoObjeto = "";
        tablaSimbolos = new TablaSimbolos();

        sintax parser = new sintax(new LexerCup(new StringReader(codigoFuente)));

        try {
            java_cup.runtime.Symbol parseResult = parser.parse();
            tablaSimbolos = parser.getTabla();

            if (parser.hayError()) {
                java_cup.runtime.Symbol simboloError = parser.getS();
                if (simboloError != null) {
                    int linea = simboloError.left + 1;
                    int columna = simboloError.right + 1;
                    String lexema = simboloError.value != null ? simboloError.value.toString() : "EOF";
                    error = "Error sintactico en linea " + linea + ", columna " + columna
                            + ": token inesperado '" + lexema + "'";
                } else {
                    error = "Error sintactico: no se pudo determinar el token causante.";
                }
                return false;
            }

            Object rawResult = parser.getResultado();
            if (rawResult == null && parseResult != null) {
                rawResult = parseResult.value;
            }

            if (rawResult != null) {
                resultado = limpiarResultado(rawResult.toString());
            } else {
                resultado = "Analisis finalizado sin arbol sintactico.";
            }

            if (!tablaSimbolos.getErrores().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (String err : tablaSimbolos.getErrores()) {
                    sb.append(err).append('\n');
                }
                error = sb.toString().trim();
                return false;
            }

            codigoIntermedio = parser.getGenInter().getCodigoCompleto();
            GeneradorObjeto generadorObjeto = new GeneradorObjeto();
            codigoObjeto = generadorObjeto.generarCodigo(codigoIntermedio);
            return true;
        } catch (Exception e) {
            java_cup.runtime.Symbol simboloError = parser.getS();
            if (simboloError != null) {
                int linea = simboloError.left + 1;
                int columna = simboloError.right + 1;
                String lexema = simboloError.value != null ? simboloError.value.toString() : "EOF";
                error = "Error sintactico en linea " + linea + ", columna " + columna
                        + ": token inesperado '" + lexema + "'";
            } else {
                error = "Error critico durante el analisis: " + e.getMessage();
            }
            return false;
        }
    }

    private String limpiarResultado(String raw) {
        int index = raw.lastIndexOf("::::");
        if (index == -1) {
            return raw;
        }
        return raw.substring(0, index);
    }

    public String getResultado() {
        return resultado;
    }

    public String getError() {
        return error;
    }

    public String getCodigoIntermedio() {
        return codigoIntermedio;
    }

    public String getCodigoObjeto() {
        return codigoObjeto;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }
}
