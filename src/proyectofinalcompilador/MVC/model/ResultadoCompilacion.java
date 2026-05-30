package proyectofinalcompilador.MVC.model;

import java.util.ArrayList;
import java.util.List;

import proyectofinalcompilador.Compilador.CompiladorLexico;
import proyectofinalcompilador.Semantico.Simbolo;

/**
 * Modelo con el resultado consolidado de una compilacion.
 */
public class ResultadoCompilacion {
    public boolean exitoLexico;
    public boolean exitoSintacticoSemantico;
    public String resultadoSintactico = "";
    public String codigoIntermedio = "";
    public String codigoObjeto = "";
    public String salida = "";
    public String errorSintacticoSemantico = "";
    public List<String> erroresLexicos = new ArrayList<>();
    public List<CompiladorLexico.TokenInfo> tokens = new ArrayList<>();
    public List<Simbolo> simbolosSemanticos = new ArrayList<>();
    public List<String> tokensVisuales = new ArrayList<>();
    public List<String> simbolosVisuales = new ArrayList<>();
    public String produccionesUtilizadas = "";
    public String estadoPila = "";

    public boolean exitoGeneral() {
        return exitoLexico && exitoSintacticoSemantico;
    }
}
