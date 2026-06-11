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

        if ("Begin".equals(tipo)) return "KW_BEGIN";
        if ("End".equals(tipo)) return "KW_END";
        if ("EndIf".equals(tipo)) return "KW_ENDIF";
        if ("Delimitador".equals(tipo)) return "DELIM_DOLLAR";
        if ("Int".equals(tipo)) return "TYPE_INT";
        if ("Tipo entero".equals(tipo)) return "TYPE_ENTERO";
        if ("Tipo integer".equals(tipo)) return "TYPE_INTEGER";
        if ("Tipo real".equals(tipo)) return "TYPE_REAL";
        if ("Tipo float".equals(tipo)) return "TYPE_FLOAT";
        if ("Tipo double".equals(tipo)) return "TYPE_DOUBLE";
        if ("Tipo char".equals(tipo)) return "TYPE_CHAR";
        if ("Tipo varchar".equals(tipo)) return "TYPE_VARCHAR";
        if ("Tipo boolean".equals(tipo)) return "TYPE_BOOLEAN";
        if ("Tipo byte".equals(tipo)) return "TYPE_BYTE";
        if ("Tipo long".equals(tipo)) return "TYPE_LONG";
        if ("Tipo text".equals(tipo)) return "TYPE_TEXT";
        if ("Identificador".equals(tipo)) return "ID";
        if ("Numero".equals(tipo)) return esReal(lexema) ? "NUM_REAL" : "NUM_INT";
        if ("Cadena".equals(tipo)) return "STRING_LITERAL";
        if ("True".equals(tipo)) return "BOOL_TRUE";
        if ("False".equals(tipo)) return "BOOL_FALSE";

        if ("If".equals(tipo)) return "KW_IF";
        if ("Else".equals(tipo)) return "KW_ELSE";
        if ("Do".equals(tipo)) return "KW_DO";
        if ("While".equals(tipo)) return "KW_WHILE";
        if ("EndWhile".equals(tipo)) return "KW_ENDWHILE";
        if ("For".equals(tipo)) return "KW_REPEAT";
        if ("Main".equals(tipo)) return "KW_JAVA3";
        if ("Public".equals(tipo)) return "KW_PUBLIC";
        if ("Private".equals(tipo)) return "KW_PRIVATE";
        if ("Class".equals(tipo)) return "KW_TEMPLATE";
        if ("Static".equals(tipo)) return "KW_CONSTANT";
        if ("Void".equals(tipo)) return "KW_VOID";
        if ("Switch".equals(tipo)) return "KW_SELECT";
        if ("Case".equals(tipo)) return "KW_OP";
        if ("Break".equals(tipo)) return "KW_BREAK";
        if ("Default".equals(tipo)) return "KW_DEFU";
        if ("Println".equals(tipo)) return "KW_PRINTLN";
        if ("In".equals(tipo)) return "KW_IN";
        if ("Then".equals(tipo)) return "KW_THEN";
        if ("Return".equals(tipo)) return "KW_RET";

        if ("Igual".equals(tipo)) return "ASSIGN_EQUAL";
        if ("Suma".equals(tipo)) return "PLUS";
        if ("Resta".equals(tipo)) return "MINUS";
        if ("Multiplicacion".equals(tipo)) return "MULT";
        if ("Division".equals(tipo)) return "DIV";
        if ("Operador relacional".equals(tipo)) return operadorRelacional(lexema);
        if ("Operador atribucion".equals(tipo)) return operadorAtribucion(lexema);
        if ("Operador incremento".equals(tipo)) return "++".equals(lexema) ? "INC" : "DEC";
        if ("Operador logico".equals(tipo)) return operadorLogico(lexema);

        if ("Parentesis apertura".equals(tipo)) return "LPAREN";
        if ("Parentesis cierre".equals(tipo)) return "RPAREN";
        if ("Llave apertura".equals(tipo)) return "LBRACE";
        if ("Llave cierre".equals(tipo)) return "RBRACE";
        if ("Corchete apertura".equals(tipo)) return "LBRACKET";
        if ("Corchete cierre".equals(tipo)) return "RBRACKET";
        if ("Coma".equals(tipo)) return "COMMA";
        if ("Dos puntos".equals(tipo)) return "COLON";
        if ("Punto".equals(tipo)) return "DOT";
        if ("Comillas".equals(tipo)) return "QUOTE";

        return "UNKNOWN";
    }

    private String operadorRelacional(String lexema) {
        if ("==".equals(lexema)) return "REL_EQ";
        if ("<=".equals(lexema)) return "REL_LE";
        if (">=".equals(lexema)) return "REL_GE";
        if ("!=".equals(lexema)) return "REL_NE";
        if ("<>".equals(lexema)) return "REL_NE_ALT";
        if ("<".equals(lexema)) return "REL_LT";
        if (">".equals(lexema)) return "REL_GT";
        return "REL_UNKNOWN";
    }

    private String operadorAtribucion(String lexema) {
        if (":=".equals(lexema)) return "ASSIGN_COLON";
        if ("+=".equals(lexema)) return "ASSIGN_PLUS";
        if ("-=".equals(lexema)) return "ASSIGN_MINUS";
        if ("*=".equals(lexema)) return "ASSIGN_MULT";
        if ("/=".equals(lexema)) return "ASSIGN_DIV";
        if ("%=".equals(lexema)) return "ASSIGN_MOD";
        return "ASSIGN_UNKNOWN";
    }

    private String operadorLogico(String lexema) {
        if ("&&".equals(lexema)) return "LOG_AND";
        if ("||".equals(lexema)) return "LOG_OR";
        if ("!".equals(lexema)) return "LOG_NOT";
        if ("&".equals(lexema)) return "LOG_AND_SINGLE";
        if ("|".equals(lexema)) return "LOG_OR_SINGLE";
        return "LOG_UNKNOWN";
    }

    private boolean esReal(String lexema) {
        return lexema.contains(".") || lexema.contains("e");
    }
}
