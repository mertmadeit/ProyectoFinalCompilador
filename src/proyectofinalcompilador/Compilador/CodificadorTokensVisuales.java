package proyectofinalcompilador.Compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convierte tokens lexicos a una representacion visual compacta para UI.
 */
public class CodificadorTokensVisuales {

    public List<String> codificar(List<CompiladorLexico.TokenInfo> tokens) {
        List<String> resultado = new ArrayList<>();
        Map<String, Integer> contadores = new HashMap<>();

        for (CompiladorLexico.TokenInfo token : tokens) {
            String categoria = categoria(token);
            int siguiente = contadores.getOrDefault(categoria, 0) + 1;
            contadores.put(categoria, siguiente);
            resultado.add(categoria + siguiente);
        }

        return resultado;
    }

    private String categoria(CompiladorLexico.TokenInfo token) {
        String tipo = token.tipo;

        if ("Begin".equals(tipo) || "End".equals(tipo) || "EndWhile".equals(tipo)) {
            return "b";
        }
        if ("Identificador".equals(tipo)) {
            return "i";
        }
        if ("Numero".equals(tipo) || "Cadena".equals(tipo) || "Booleano".equals(tipo)) {
            return "n";
        }
        if ("If".equals(tipo) || "Else".equals(tipo) || "Do".equals(tipo) || "While".equals(tipo)
                || "For".equals(tipo) || "Then".equals(tipo) || "Switch".equals(tipo)
                || "Case".equals(tipo) || "Break".equals(tipo) || "Default".equals(tipo)
                || "Return".equals(tipo) || "Println".equals(tipo) || "In".equals(tipo)) {
            return "k";
        }
        if ("Int".equals(tipo) || "Tipo de dato".equals(tipo) || "Tipo String".equals(tipo)
                || "Class".equals(tipo) || "Public".equals(tipo) || "Private".equals(tipo)
                || "Static".equals(tipo) || "Void".equals(tipo) || "Main".equals(tipo)) {
            return "t";
        }
        if ("Operador logico".equals(tipo) || "Operador incremento".equals(tipo)
                || "Operador relacional".equals(tipo) || "Operador atribucion".equals(tipo)
                || "Igual".equals(tipo) || "Suma".equals(tipo) || "Resta".equals(tipo)
                || "Multiplicacion".equals(tipo) || "Division".equals(tipo)) {
            return "o";
        }
        if ("Parentesis apertura".equals(tipo) || "Parentesis cierre".equals(tipo)
                || "Llave apertura".equals(tipo) || "Llave cierre".equals(tipo)
                || "Corchete apertura".equals(tipo) || "Corchete cierre".equals(tipo)
                || "Punto y coma".equals(tipo) || "Coma".equals(tipo) || "Punto".equals(tipo)
                || "Comillas".equals(tipo)) {
            return "d";
        }
        if ("ERROR".equals(tipo)) {
            return "e";
        }

        return "x";
    }
}
