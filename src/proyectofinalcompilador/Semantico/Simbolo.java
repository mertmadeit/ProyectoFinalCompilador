package proyectofinalcompilador.Semantico;

/**
 * Representa un símbolo (variable) en el programa
 */
public class Simbolo {
    private String nombre;
    private String tipo;
    private String valor;
    private String alcance; // Scope: local, global, etc.
    private int linea;

    public Simbolo(String nombre, String tipo, String valor, String alcance, int linea) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.alcance = alcance;
        this.linea = linea;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public String getAlcance() {
        return alcance;
    }

    public int getLinea() {
        return linea;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
