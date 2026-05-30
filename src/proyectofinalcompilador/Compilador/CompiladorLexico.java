package proyectofinalcompilador.Compilador;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;
import proyectofinalcompilador.Lexico.LexerCup;
import proyectofinalcompilador.Sintactico.sym;

/**
 * Compilador lexico.
 */
public class CompiladorLexico {

    private final List<TokenInfo> tokens;
    private final List<String> errores;

    public CompiladorLexico() {
        tokens = new ArrayList<>();
        errores = new ArrayList<>();
    }

    /**
     * Ejecuta el analisis lexico del codigo fuente.
     */
    public boolean compilar(String codigoFuente) {
        tokens.clear();
        errores.clear();

        try {
            LexerCup lexer = new LexerCup(new StringReader(codigoFuente));
            Symbol symbol;
            while ((symbol = lexer.next_token()).sym != sym.EOF) {
                String tokenType = obtenerNombreToken(symbol.sym);
                String lexema = symbol.value == null ? "" : symbol.value.toString();
                int linea = symbol.left + 1;
                int columna = symbol.right + 1;

                if (symbol.sym == sym.ERROR) {
                    errores.add("Error lexico en linea " + linea + ", columna " + columna
                            + ": simbolo no reconocido '" + lexema + "'");
                } else {
                    tokens.add(new TokenInfo(tokenType, lexema, linea));
                }
            }
            lexer.yyclose();
            return errores.isEmpty();
        } catch (IOException e) {
            errores.add("Error de entrada/salida: " + e.getMessage());
            return false;
        }
    }

    private String obtenerNombreToken(int tokenCode) {
        switch (tokenCode) {
            case sym.Comillas:
                return "Comillas";
            case sym.T_dato:
                return "Tipo de dato";
            case sym.Int:
                return "Int";
            case sym.Cadena:
                return "Cadena";
            case sym.If:
                return "If";
            case sym.Else:
                return "Else";
            case sym.Do:
                return "Do";
            case sym.While:
                return "While";
            case sym.For:
                return "For";
            case sym.Begin:
                return "Begin";
            case sym.End:
                return "End";
            case sym.Private:
                return "Private";
            case sym.EndWhile:
                return "EndWhile";
            case sym.Switch:
                return "Switch";
            case sym.Case:
                return "Case";
            case sym.Break:
                return "Break";
            case sym.Default:
                return "Default";
            case sym.Println:
                return "Println";
            case sym.In:
                return "In";
            case sym.Then:
                return "Then";
            case sym.Return:
                return "Return";
            case sym.Igual:
                return "Igual";
            case sym.Suma:
                return "Suma";
            case sym.Resta:
                return "Resta";
            case sym.Multiplicacion:
                return "Multiplicacion";
            case sym.Division:
                return "Division";
            case sym.Op_logico:
                return "Operador logico";
            case sym.Op_incremento:
                return "Operador incremento";
            case sym.Op_relacional:
                return "Operador relacional";
            case sym.Op_atribucion:
                return "Operador atribucion";
            case sym.Op_booleano:
                return "Booleano";
            case sym.Parentesis_a:
                return "Parentesis apertura";
            case sym.Parentesis_c:
                return "Parentesis cierre";
            case sym.Llave_a:
                return "Llave apertura";
            case sym.Llave_c:
                return "Llave cierre";
            case sym.Corchete_a:
                return "Corchete apertura";
            case sym.Corchete_c:
                return "Corchete cierre";
            case sym.Main:
                return "Main";
            case sym.P_coma:
                return "Punto y coma";
            case sym.Coma:
                return "Coma";
            case sym.Identificador:
                return "Identificador";
            case sym.Numero:
                return "Numero";
            case sym.Punto:
                return "Punto";
            case sym.Public:
                return "Public";
            case sym.Class:
                return "Class";
            case sym.Static:
                return "Static";
            case sym.Void:
                return "Void";
            case sym.String_type:
                return "Tipo String";
            case sym.ERROR:
                return "ERROR";
            default:
                return "Desconocido";
        }
    }

    public List<TokenInfo> getTokens() {
        return tokens;
    }

    public List<String> getErrores() {
        return errores;
    }

    public boolean tieneErrores() {
        return !errores.isEmpty();
    }

    /**
     * Informacion de token.
     */
    public static class TokenInfo {
        public final String tipo;
        public final String lexema;
        public final int linea;

        public TokenInfo(String tipo, String lexema, int linea) {
            this.tipo = tipo;
            this.lexema = lexema;
            this.linea = linea;
        }
    }
}
