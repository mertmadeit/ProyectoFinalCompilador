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
                    errores.add(construirErrorLexico(lexema, linea, columna));
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

    private String construirErrorLexico(String lexema, int linea, int columna) {
        String detalle = "simbolo no reconocido '" + lexema + "'";

        if (lexema.matches("[A-Za-z][A-Za-z0-9_]{10,}")) {
            detalle = "identificador excede la longitud maxima de 10 caracteres '" + lexema + "'";
        } else if (lexema.matches("([0-9]+\\.[0-9]*|\\.[0-9]+)[A-Za-z_][A-Za-z0-9_]*")) {
            detalle = "numero real mal formado o unido a identificador '" + lexema + "'";
        } else if (lexema.matches("[0-9]+[A-Za-z_][A-Za-z0-9_]*")) {
            detalle = "identificador no puede iniciar con numero '" + lexema + "'";
        }

        return "Error lexico en linea " + linea + ", columna " + columna + ": " + detalle;
    }

    private String obtenerNombreToken(int tokenCode) {
        switch (tokenCode) {
            case sym.Comillas:
                return "Comillas";
            case sym.Int:
                return "Int";
            case sym.Entero:
                return "Tipo entero";
            case sym.Real:
                return "Tipo real";
            case sym.Integer_type:
                return "Tipo integer";
            case sym.Float_type:
                return "Tipo float";
            case sym.Char_type:
                return "Tipo char";
            case sym.Varchar_type:
                return "Tipo varchar";
            case sym.Boolean_type:
                return "Tipo boolean";
            case sym.Byte_type:
                return "Tipo byte";
            case sym.Long_type:
                return "Tipo long";
            case sym.Double_type:
                return "Tipo double";
            case sym.True:
                return "True";
            case sym.False:
                return "False";
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
            case sym.EndIf:
                return "EndIf";
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
            case sym.Delimitador:
                return "Delimitador";
            case sym.Coma:
                return "Coma";
            case sym.DosPuntos:
                return "Dos puntos";
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
                return "Tipo text";
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
