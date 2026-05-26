package proyectofinalcompilador.Compilador;

import proyectofinalcompilador.Lexico.LexerCup;
import proyectofinalcompilador.Lexico.Tokens;
import proyectofinalcompilador.Sintactico.sym;
import java_cup.runtime.Symbol;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Compilador Léxico - Realiza el análisis léxico del código fuente
 */
public class CompiladorLexico {

    private List<TokenInfo> tokens;
    private List<String> errores;

    public CompiladorLexico() {
        tokens = new ArrayList<>();
        errores = new ArrayList<>();
    }

    /**
     * Realiza el análisis léxico del código fuente
     * 
     * @param codigoFuente Código a analizar
     * @return true si el análisis fue exitoso, false si hay errores
     */
    public boolean compilar(String codigoFuente) {
        tokens.clear();
        errores.clear();

        try {
            StringReader reader = new StringReader(codigoFuente);
            LexerCup lexer = new LexerCup(reader);

            Symbol symbol;
            while ((symbol = lexer.next_token()).sym != sym.EOF) {
                String tokenType = obtenerNombreToken(symbol.sym);
                String lexema = (String) symbol.value;
                int linea = symbol.left + 1; // JFlex usa línea 0, hacemos que sea 1-based

                // Si es un error, agregamos a la lista de errores
                if (symbol.sym == sym.ERROR) {
                    errores.add("Error léxico en línea " + linea + ": símbolo no reconocido '" + lexema + "'");
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

    /**
     * Obtiene el nombre del token basado en su código
     */
    private String obtenerNombreToken(int tokenCode) {
        switch (tokenCode) {
            case sym.Comillas:
                return "Comillas";
            case sym.T_dato:
                return "Tipo de dato";
            case sym.Int:
                return "Int";
            case sym.Cadena:
                return "String";
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
                return "Multiplicación";
            case sym.Division:
                return "División";
            case sym.Op_logico:
                return "Operador Lógico";
            case sym.Op_incremento:
                return "Operador Incremento";
            case sym.Op_relacional:
                return "Operador Relacional";
            case sym.Op_atribucion:
                return "Operador Atribución";
            case sym.Op_booleano:
                return "Operador Booleano";
            case sym.Parentesis_a:
                return "Paréntesis Apertura";
            case sym.Parentesis_c:
                return "Paréntesis Cierre";
            case sym.Llave_a:
                return "Llave Apertura";
            case sym.Llave_c:
                return "Llave Cierre";
            case sym.Corchete_a:
                return "Corchete Apertura";
            case sym.Corchete_c:
                return "Corchete Cierre";
            case sym.Main:
                return "Main";
            case sym.P_coma:
                return "Punto y coma";
            case sym.Coma:
                return "Coma";
            case sym.Identificador:
                return "Identificador";
            case sym.Numero:
                return "Número";
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

    // Getters
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
     * Clase interna para guardar información del token
     */
    public static class TokenInfo {
        public String tipo;
        public String lexema;
        public int linea;

        public TokenInfo(String tipo, String lexema, int linea) {
            this.tipo = tipo;
            this.lexema = lexema;
            this.linea = linea;
        }
    }
}
