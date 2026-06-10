package proyectofinalcompilador.Compilador;

import java.util.ArrayList;
import java.util.List;

/**
 * Convierte tokens lexicos a una representacion visual compacta para UI.
 */
public class CodificadorTokensVisuales {

    public List<String> codificar(List<CompiladorLexico.TokenInfo> tokens) {
        List<String> resultado = new ArrayList<>();

        for (CompiladorLexico.TokenInfo token : tokens) {
            resultado.add(simbolo(token));
        }

        return resultado;
    }

    private String simbolo(CompiladorLexico.TokenInfo token) {
        String tipo = token.tipo;
        String lexema = token.lexema == null ? "" : token.lexema.toLowerCase();

        if ("Begin".equals(tipo)) {
            return "a";
        }
        if ("End".equals(tipo)) {
            return "b";
        }
        if ("Delimitador".equals(tipo)) {
            return "c";
        }
        if ("Int".equals(tipo) || ("Tipo de dato".equals(tipo)
                && ("entero".equals(lexema) || "integer".equals(lexema)))) {
            return "d";
        }
        if ("Tipo de dato".equals(tipo)
                && ("real".equals(lexema) || "float".equals(lexema) || "double".equals(lexema))) {
            return "e";
        }
        if ("Identificador".equals(tipo)) {
            return "f";
        }
        if ("Coma".equals(tipo)) {
            return "g";
        }
        if ("If".equals(tipo)) {
            return "h";
        }
        if ("Parentesis apertura".equals(tipo)) {
            return "i";
        }
        if ("Parentesis cierre".equals(tipo)) {
            return "j";
        }
        if ("Else".equals(tipo)) {
            return "k";
        }
        if ("Igual".equals(tipo) || ("Operador relacional".equals(tipo) && "==".equals(lexema))) {
            return "l";
        }
        if ("Operador relacional".equals(tipo)) {
            if ("<=".equals(lexema)) {
                return "m";
            }
            if (">=".equals(lexema)) {
                return "n";
            }
            if ("<>".equals(lexema) || "!=".equals(lexema)) {
                return "o";
            }
            if ("<".equals(lexema)) {
                return "p";
            }
            if (">".equals(lexema)) {
                return "q";
            }
        }
        if ("Numero".equals(tipo)) {
            return esReal(lexema) ? "s" : "r";
        }
        if ("While".equals(tipo)) {
            return "t";
        }
        if ("EndWhile".equals(tipo)) {
            return "u";
        }
        if ("Operador atribucion".equals(tipo) && ":=".equals(lexema)) {
            return "v";
        }
        if ("Suma".equals(tipo)) {
            return "w";
        }
        if ("Multiplicacion".equals(tipo)) {
            return "x";
        }
        if ("Resta".equals(tipo)) {
            return "y";
        }
        if ("Division".equals(tipo)) {
            return "z";
        }

        return "ε";
    }

    private boolean esReal(String lexema) {
        return lexema.contains(".") || lexema.contains("e");
    }
}
