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
( "\"" ) {return symbol(sym.Comillas, yytext());}

/* Texto entre comillas */
\" [^\"]* \" { return symbol(sym.Cadena, yytext().substring(1, yytext().length()-1)); }

/* Tipos de datos */
( byte | char | long | float | double ) {return symbol(sym.T_dato, yytext());}

/* Tipo de dato Int (Para el main) */
( "int" ) {return symbol(sym.Int, yytext());}

/* Palabra reservada Public */
( "public" ) {return symbol(sym.Public, yytext());}

/* Palabra reservada Class */
( "class" ) {return symbol(sym.Class, yytext());}

/* Palabra reservada Static */
( "static" ) {return symbol(sym.Static, yytext());}

/* Palabra reservada Void */
( "void" ) {return symbol(sym.Void, yytext());}

/* Tipo de dato String */
( "String" ) {return symbol(sym.String_type, yytext());}

/* Palabra reservada If */
( if ) {return symbol(sym.If, yytext());}

/* Palabra reservada Else */
( else ) {return symbol(sym.Else, yytext());}

/* Palabra reservada Do */
( do ) {return symbol(sym.Do, yytext());}

/* Palabra reservada While */
( while ) {return symbol(sym.While, yytext());}

/* Palabra reservada For */
( for ) {return symbol(sym.For, yytext());}

/* Operador Igual */
( "=" ) {return symbol(sym.Igual, yytext());}

/* Operador Suma */
( "+" ) {return symbol(sym.Suma, yytext());}

/* Operador Resta */
( "-" ) {return symbol(sym.Resta, yytext());}

/* Operador Multiplicacion */
( "*" ) {return symbol(sym.Multiplicacion, yytext());}

/* Operador Division */
( "/" ) {return symbol(sym.Division, yytext());}

/* Operadores logicos */
( "&&" | "||" | "!" | "&" | "|" ) {return symbol(sym.Op_logico, yytext());}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<=" | "<<" | ">>" ) {return symbol(sym.Op_relacional, yytext());}

/* Operadores Atribucion */
( "+=" | "-="  | "*=" | "/=" | "%=" | "=" ) {return symbol(sym.Op_atribucion, yytext());}

/* Operadores Incremento y decremento */
( "++" | "--" ) {return symbol(sym.Op_incremento, yytext());}

/*Operadores Booleanos*/
( true | false ) {return symbol(sym.Op_booleano, yytext());}

/* Parentesis de apertura */
( "(" ) {return symbol(sym.Parentesis_a, yytext());}

/* Parentesis de cierre */
( ")" ) {return symbol(sym.Parentesis_c, yytext());}

/* Llave de apertura */
( "{" ) {return symbol(sym.Llave_a, yytext());}

/* Llave de cierre */
( "}" ) {return symbol(sym.Llave_c, yytext());}

/* Corchete de apertura */
( "[" ) {return symbol(sym.Corchete_a, yytext());}

/* Corchete de cierre */
( "]" ) {return symbol(sym.Corchete_c, yytext());}

/* Marcador de inicio de algoritmo */
( "main" ) {return symbol(sym.Main, yytext());}

/* Punto y coma */
( ";" ) {return symbol(sym.P_coma, yytext());}

/* Dos puntos */
( ":" ) {return symbol(sym.P_coma, yytext());}

/* Punto */
( "." ) {return symbol(sym.Punto, yytext());}

/* Identificador */
{L}({L}|{D})* {return symbol(sym.Identificador, yytext());}

/* Numero */
("(-"{D}+")")|{D}+ {return symbol(sym.Numero, yytext());}

/* Error de analisis */
 . {return symbol(sym.ERROR, yytext());}
