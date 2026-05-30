package proyectofinalcompilador.Semantico;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Tabla de simbolos para el analisis semantico.
 */
public class TablaSimbolos {
    private final Map<String, Simbolo> simbolosPorNombre;
    private final List<String> errores;

    public TablaSimbolos() {
        simbolosPorNombre = new LinkedHashMap<>();
        errores = new ArrayList<>();
    }

    /**
     * Agrega un nuevo simbolo a la tabla y valida redeclaraciones.
     */
    public void agregar(Simbolo s) {
        if (simbolosPorNombre.containsKey(s.getNombre())) {
            errores.add("Error semantico en linea " + s.getLinea()
                    + ": La variable '" + s.getNombre() + "' ya ha sido declarada.");
            return;
        }
        simbolosPorNombre.put(s.getNombre(), s);
    }

    /**
     * Busca un simbolo por nombre.
     */
    public Simbolo buscar(String nombre) {
        return simbolosPorNombre.get(nombre);
    }

    /**
     * Registra un error si se usa una variable no declarada.
     */
    public void verificarExistencia(String nombre, int linea) {
        if (buscar(nombre) == null) {
            errores.add("Error semantico en linea " + linea
                    + ": La variable '" + nombre + "' no ha sido declarada.");
        }
    }

    /**
     * Actualiza el valor de un simbolo declarado.
     */
    public void asignarValor(String nombre, String valor, int linea) {
        Simbolo simbolo = buscar(nombre);
        if (simbolo == null) {
            errores.add("Error semantico en linea " + linea
                    + ": La variable '" + nombre + "' no ha sido declarada.");
            return;
        }
        simbolo.setValor(valor);
    }

    public List<Simbolo> getSimbolos() {
        return new ArrayList<>(simbolosPorNombre.values());
    }

    public List<String> getErrores() {
        return errores;
    }

    public void limpiar() {
        simbolosPorNombre.clear();
        errores.clear();
    }
}
