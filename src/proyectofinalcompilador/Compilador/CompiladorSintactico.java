package proyectofinalcompilador.Compilador;

import proyectofinalcompilador.Lexico.LexerCup;
import proyectofinalcompilador.Sintactico.sintax;
import proyectofinalcompilador.Semantico.TablaSimbolos;
import proyectofinalcompilador.CodigoIntermedio.GeneradorIntermedio;
import java.io.StringReader;

/**
 * Compilador Sintáctico y Semántico - Realiza el análisis del código fuente
 */
public class CompiladorSintactico {

    private String resultado;
    private String error;
    private String codigoIntermedio;
    private TablaSimbolos tablaSimbolos;

    public CompiladorSintactico() {
        resultado = "";
        error = "";
        codigoIntermedio = "";
        tablaSimbolos = new TablaSimbolos();
    }

    /**
     * Realiza el análisis sintáctico, semántico e intermedio del código fuente
     * 
     * @param codigoFuente Código a analizar
     * @return true si el análisis fue exitoso, false si hay errores
     */
    public boolean analizar(String codigoFuente) {
        sintax s = new sintax(new LexerCup(new StringReader(codigoFuente)));

        try {
            System.out.println("Iniciando análisis sintáctico...");
            java_cup.runtime.Symbol sres = s.parse();
            tablaSimbolos = s.getTabla();
            codigoIntermedio = s.getGenInter().getCodigoCompleto();

            Object rawRes = s.getResultado();
            if (rawRes == null && sres != null) {
                rawRes = sres.value;
            }

            System.out.println("Análisis completado. rawRes: " + (rawRes != null ? "no nulo" : "nulo"));

            if (rawRes != null) {
                String strRes = rawRes.toString();
                // Usamos una división más segura para evitar problemas con regex
                int index = strRes.lastIndexOf("::::");
                if (index != -1) {
                    resultado = strRes.substring(0, index);
                } else {
                    resultado = strRes;
                }
            } else {
                resultado = "Error: El árbol no pudo ser generado.";
            }

            System.out.println("Resultado final preparado. Longitud: " + resultado.length());

            // Si hay errores semánticos, los agregamos
            if (!tablaSimbolos.getErrores().isEmpty()) {
                StringBuilder semanticErrors = new StringBuilder();
                for (String err : tablaSimbolos.getErrores()) {
                    semanticErrors.append(err).append("\n");
                }
                error = semanticErrors.toString();
                return false;
            }

            return true;
        } catch (Exception e) {
            java_cup.runtime.Symbol sym = s.getS();
            if (sym != null) {
                int linea = sym.left + 1;
                int columna = sym.right + 1;
                String lexema = (sym.value != null) ? sym.value.toString() : "EOF";
                error = "Error sintáctico en línea " + linea + ", columna " + columna + ": Token inesperado '" + lexema
                        + "'";
            } else {
                error = "Error crítico: " + e.getMessage();
                e.printStackTrace();
            }
            return false;
        }
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

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }
}
