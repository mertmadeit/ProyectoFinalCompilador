package proyectofinalcompilador.MVC.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import proyectofinalcompilador.Compilador.CodificadorTokensVisuales;
import proyectofinalcompilador.Compilador.CompiladorLexico;
import proyectofinalcompilador.Compilador.CompiladorSintactico;
import proyectofinalcompilador.MVC.model.ResultadoCompilacion;
import proyectofinalcompilador.Semantico.Simbolo;

/**
 * Controlador de compilacion: coordina analisis lexico, sintactico y semantico.
 */
public class CompiladorController {

    public ResultadoCompilacion compilar(String codigoFuente) {
        ResultadoCompilacion resultado = new ResultadoCompilacion();

        CompiladorLexico compiladorLex = new CompiladorLexico();
        resultado.exitoLexico = compiladorLex.compilar(codigoFuente);
        resultado.tokens = new ArrayList<>(compiladorLex.getTokens());
        resultado.erroresLexicos = new ArrayList<>(compiladorLex.getErrores());

        CodificadorTokensVisuales codificadorVisual = new CodificadorTokensVisuales();
        resultado.tokensVisuales = codificadorVisual.codificar(resultado.tokens);
        resultado.simbolosVisuales = construirSimbolosVisuales(resultado.tokens);

        CompiladorSintactico compiladorSintax = new CompiladorSintactico();
        resultado.exitoSintacticoSemantico = compiladorSintax.analizar(codigoFuente);
        resultado.resultadoSintactico = compiladorSintax.getResultado();
        resultado.codigoIntermedio = compiladorSintax.getCodigoIntermedio();
        resultado.codigoObjeto = compiladorSintax.getCodigoObjeto();
        resultado.errorSintacticoSemantico = compiladorSintax.getError();

        if (compiladorSintax.getTablaSimbolos() != null) {
            resultado.simbolosSemanticos = new ArrayList<>(compiladorSintax.getTablaSimbolos().getSimbolos());
        }

        resultado.produccionesUtilizadas = construirProduccionesTexto(resultado.resultadoSintactico);
        resultado.estadoPila = construirEstadoPilaTexto(resultado.tokensVisuales, resultado.exitoSintacticoSemantico);
        resultado.salida = construirMensajeSalida(resultado);

        return resultado;
    }

    private String construirMensajeSalida(ResultadoCompilacion resultado) {
        if (resultado.exitoGeneral()) {
            return "Compilación completada exitosamente.\nTokens encontrados: " + resultado.tokens.size();
        }

        StringBuilder mensajeError = new StringBuilder();
        if (!resultado.exitoLexico) {
            mensajeError.append("Errores léxicos:\n");
            for (String err : resultado.erroresLexicos) {
                mensajeError.append("• ").append(err).append("\n");
            }
        }
        if (!resultado.exitoSintacticoSemantico && resultado.errorSintacticoSemantico != null
                && !resultado.errorSintacticoSemantico.isEmpty()) {
            if (resultado.errorSintacticoSemantico.contains("Error semantico")
                    || resultado.errorSintacticoSemantico.contains("Error semántico")) {
                mensajeError.append("\nErrores semánticos:\n");
            } else {
                mensajeError.append("\nErrores sintácticos:\n");
            }
            mensajeError.append("• ").append(resultado.errorSintacticoSemantico).append("\n");
        }
        return mensajeError.toString().trim();
    }

    private List<String> construirSimbolosVisuales(List<CompiladorLexico.TokenInfo> tokens) {
        Set<String> simbolos = new LinkedHashSet<>();
        for (CompiladorLexico.TokenInfo token : tokens) {
            if ("Identificador".equals(token.tipo) || "Numero".equals(token.tipo) || "Cadena".equals(token.tipo)) {
                simbolos.add(token.lexema);
            }
        }
        return new ArrayList<>(simbolos);
    }

    private String construirProduccionesTexto(String resultadoSintactico) {
        if (resultadoSintactico == null || resultadoSintactico.trim().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String[] lineas = resultadoSintactico.split("\\R");
        int contador = 1;
        for (String linea : lineas) {
            String limpia = linea.trim();
            if (limpia.isEmpty()) {
                continue;
            }
            sb.append("P").append(contador++).append(": ").append(limpia).append('\n');
        }
        return sb.toString().trim();
    }

    private String construirEstadoPilaTexto(List<String> tokensVisuales, boolean exitoSintax) {
        StringBuilder sb = new StringBuilder();
        sb.append("Inicio\n");
        for (String token : tokensVisuales) {
            sb.append("Push ").append(token).append('\n');
        }
        sb.append(exitoSintax ? "Aceptado" : "Error");
        return sb.toString();
    }
}
