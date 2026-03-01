package proyectofinalcompilador.Semantico;

import java.util.ArrayList;
import java.util.List;

/**
 * Tabla de Símbolos para el análisis semántico
 */
public class TablaSimbolos {
    private List<Simbolo> simbolos;
    private List<String> errores; // Errores semánticos

    public TablaSimbolos() {
        simbolos = new ArrayList<>();
        errores = new ArrayList<>();
    }

    /**
     * Agrega un nuevo símbolo a la tabla.
     * Verifica redeclaraciones.
     */
    public void agregar(Simbolo s) {
        if (buscar(s.getNombre()) != null) {
            errores.add("Error semántico en línea " + s.getLinea() + ": La variable '" + s.getNombre()
                    + "' ya ha sido declarada.");
        } else {
            simbolos.add(s);
        }
    }

    /**
     * Busca un símbolo por nombre.
     */
    public Simbolo buscar(String nombre) {
        for (Simbolo s : simbolos) {
            if (s.getNombre().equals(nombre)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Registra un error si se usa una variable no declarada.
     */
    public void verificarExistencia(String nombre, int linea) {
        if (buscar(nombre) == null) {
            errores.add("Error semántico en línea " + linea + ": La variable '" + nombre + "' no ha sido declarada.");
        }
    }

    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void limpiar() {
        simbolos.clear();
        errores.clear();
    }
}
