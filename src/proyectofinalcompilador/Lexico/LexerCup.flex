package proyectofinalcompilador.Lexico;
import java_cup.runtime.Symbol;
import proyectofinalcompilador.Sintactico.sym;
%%
%class LexerCup
%public
%type java_cup.runtime.Symbol
%cup
%full
%line
%char
%column
LETRA=[A-Za-z]
DIGITO=[0-9]
ID={LETRA}({LETRA}|{DIGITO}|"_")*
NUMERO_IDENT={DIGITO}+({LETRA}|"_")({LETRA}|{DIGITO}|"_")*
REAL_IDENT=(({DIGITO}+"."{DIGITO}*)|("."{DIGITO}+))({LETRA}|"_")({LETRA}|{DIGITO}|"_")*
ENTERO={DIGITO}+
DECIMAL=({DIGITO}+"."{DIGITO}*)|("."{DIGITO}+)
EXP=([eE][+-]?{DIGITO}+)
NUMERO=({DECIMAL}{EXP}?)|({ENTERO}{EXP}?)
ESPACIO=[ \t\r\n\f]+
COMENTARIO_LINEA=("//"[^\r\n]*)
COMENTARIO_BLOQUE=("/*"([^*]|[\r\n]|"*"[^/])*"*/")
CADENA=\"([^\"\\\n]|\\.)*\"
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%% 

/* Espacios en blanco */
{ESPACIO} {/* Ignore */}

/* Comentarios */
{COMENTARIO_LINEA} {/* Ignore */}
{COMENTARIO_BLOQUE} {/* Ignore */}

/* Comillas */
("\"") { return symbol(sym.Comillas, yytext()); }

/* Texto entre comillas */
{CADENA} { return symbol(sym.Cadena, yytext().substring(1, yytext().length()-1)); }

/* Palabras reservadas definidas para el lenguaje */
("begin") { return symbol(sym.Begin, yytext()); }
("end") { return symbol(sym.End, yytext()); }
("endif") { return symbol(sym.End, yytext()); }
("java3") { return symbol(sym.Main, yytext()); }
("public") { return symbol(sym.Public, yytext()); }
("private") { return symbol(sym.Private, yytext()); }
("void") { return symbol(sym.Void, yytext()); }
("si") { return symbol(sym.If, yytext()); }
("either") { return symbol(sym.Else, yytext()); }
("repeat") { return symbol(sym.For, yytext()); }
("do") { return symbol(sym.Do, yytext()); }
("while") { return symbol(sym.While, yytext()); }
("endwhile") { return symbol(sym.EndWhile, yytext()); }
("select") { return symbol(sym.Switch, yytext()); }
("op") { return symbol(sym.Case, yytext()); }
("__") { return symbol(sym.Break, yytext()); }
("defu") { return symbol(sym.Default, yytext()); }
("println") { return symbol(sym.Println, yytext()); }
("in") { return symbol(sym.In, yytext()); }
("then") { return symbol(sym.Then, yytext()); }
("ret") { return symbol(sym.Return, yytext()); }
("template") { return symbol(sym.Class, yytext()); }
("constant") { return symbol(sym.Static, yytext()); }
("text") { return symbol(sym.String_type, yytext()); }

/* Tipos de datos soportados */
("int"|"INT") { return symbol(sym.Int, yytext()); }
("entero"|"real"|"integer"|"float"|"char"|"varchar"|"boolean"|"byte"|"long"|"double") {
    return symbol(sym.T_dato, yytext());
}

/* Valores booleanos */
("true"|"false") { return symbol(sym.Op_booleano, yytext()); }

/* Operadores logicos */
("&&"|"||"|"!"|"&"|"|") { return symbol(sym.Op_logico, yytext()); }

/*Operadores Relacionales */
("<>"|">="|"<="|"=="|"!="|">"|"<") { return symbol(sym.Op_relacional, yytext()); }

/* Operadores Atribucion */
(":="|"+="|"-="|"*="|"/="|"%=") { return symbol(sym.Op_atribucion, yytext()); }

/* Operadores Incremento y decremento */
("++"|"--") { return symbol(sym.Op_incremento, yytext()); }

/* Operador Igual */
("=") { return symbol(sym.Igual, yytext()); }

/* Operador Suma */
("+") { return symbol(sym.Suma, yytext()); }

/* Operador Resta */
("-") { return symbol(sym.Resta, yytext()); }

/* Operador Multiplicacion */
("*") { return symbol(sym.Multiplicacion, yytext()); }

/* Operador Division */
("/") { return symbol(sym.Division, yytext()); }

/* Parentesis de apertura */
("(") { return symbol(sym.Parentesis_a, yytext()); }

/* Parentesis de cierre */
(")") { return symbol(sym.Parentesis_c, yytext()); }

/* Llave de apertura */
("{") { return symbol(sym.Llave_a, yytext()); }

/* Llave de cierre */
("}") { return symbol(sym.Llave_c, yytext()); }

/* Corchete de apertura */
("[") { return symbol(sym.Corchete_a, yytext()); }

/* Corchete de cierre */
("]") { return symbol(sym.Corchete_c, yytext()); }

/* Delimitador de instruccion */
("$") { return symbol(sym.Delimitador, yytext()); }

/* Coma */
(",") { return symbol(sym.Coma, yytext()); }

/* Dos puntos */
(":") { return symbol(sym.DosPuntos, yytext()); }

/* Punto */
(".") { return symbol(sym.Punto, yytext()); }

/* Identificador */
{ID} {
    if (yytext().length() > 10) {
        return symbol(sym.ERROR, yytext());
    }
    return symbol(sym.Identificador, yytext());
}

/* Numero (entero, decimal y notación científica) */
{NUMERO} { return symbol(sym.Numero, yytext()); }

/* Errores especificos del lexico */
{REAL_IDENT} { return symbol(sym.ERROR, yytext()); }
{NUMERO_IDENT} { return symbol(sym.ERROR, yytext()); }

/* Error de analisis */
. { return symbol(sym.ERROR, yytext()); }
