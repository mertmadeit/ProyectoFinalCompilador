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
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ \t\r\n]+
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
{espacio} {/*Ignore*/}

/* Comentarios */
( "//"(.)* ) {/*Ignore*/}

/* Comillas */
( "\"" ) {return new Symbol(sym.Comillas, yyline, yycolumn, yytext());}

/* Tipos de datos */
( byte | char | long | float | double ) {return new Symbol(sym.T_dato, yyline, yycolumn, yytext());}

/* Tipo de dato Int (Para el main) */
( "int" ) {return new Symbol(sym.Int, yyline, yycolumn, yytext());}

/* Tipo de dato String */
( String ) {return new Symbol(sym.Cadena, yyline, yycolumn, yytext());}

/* Palabra reservada If */
( if ) {return new Symbol(sym.If, yyline, yycolumn, yytext());}

/* Palabra reservada Else */
( else ) {return new Symbol(sym.Else, yyline, yycolumn, yytext());}

/* Palabra reservada Do */
( do ) {return new Symbol(sym.Do, yyline, yycolumn, yytext());}

/* Palabra reservada While */
( while ) {return new Symbol(sym.While, yyline, yycolumn, yytext());}

/* Palabra reservada For */
( for ) {return new Symbol(sym.For, yyline, yycolumn, yytext());}

/* Operador Igual */
( "=" ) {return new Symbol(sym.Igual, yyline, yycolumn, yytext());}

/* Operador Suma */
( "+" ) {return new Symbol(sym.Suma, yyline, yycolumn, yytext());}

/* Operador Resta */
( "-" ) {return new Symbol(sym.Resta, yyline, yycolumn, yytext());}

/* Operador Multiplicacion */
( "*" ) {return new Symbol(sym.Multiplicacion, yyline, yycolumn, yytext());}

/* Operador Division */
( "/" ) {return new Symbol(sym.Division, yyline, yycolumn, yytext());}

/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|" ) {return new Symbol(sym.Op_logico, yyline, yycolumn, yytext());}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" | "<<" | ">>" ) {return new Symbol(sym.Op_relacional, yyline, yycolumn, yytext());}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" | "=" ) {return new Symbol(sym.Op_atribucion, yyline, yycolumn, yytext());}

/* Operadores Incremento y decremento */
( "++" | "--" ) {return new Symbol(sym.Op_incremento, yyline, yycolumn, yytext());}

/*Operadores Booleanos*/
( true | false ) {return new Symbol(sym.Op_booleano, yyline, yycolumn, yytext());}

/* Parentesis de apertura */
( "(" ) {return new Symbol(sym.Parentesis_a, yyline, yycolumn, yytext());}

/* Parentesis de cierre */
( ")" ) {return new Symbol(sym.Parentesis_c, yyline, yycolumn, yytext());}

/* Llave de apertura */
( "{" ) {return new Symbol(sym.Llave_a, yyline, yycolumn, yytext());}

/* Llave de cierre */
( "}" ) {return new Symbol(sym.Llave_c, yyline, yycolumn, yytext());}

/* Corchete de apertura */
( "[" ) {return new Symbol(sym.Corchete_a, yyline, yycolumn, yytext());}

/* Corchete de cierre */
( "]" ) {return new Symbol(sym.Corchete_c, yyline, yycolumn, yytext());}

/* Marcador de inicio de algoritmo */
( "main" ) {return new Symbol(sym.Main, yyline, yycolumn, yytext());}

/* Punto y coma */
( ";" ) {return new Symbol(sym.P_coma, yyline, yycolumn, yytext());}

/* Dos puntos */
( ":" ) {return new Symbol(sym.P_coma, yyline, yycolumn, yytext());}

/* Identificador */
{L}({L}|{D})* {return new Symbol(sym.Identificador, yyline, yycolumn, yytext());}

/* Numero */
("(-"{D}+")")|{D}+ {return new Symbol(sym.Numero, yyline, yycolumn, yytext());}

/* Error de analisis */
 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
