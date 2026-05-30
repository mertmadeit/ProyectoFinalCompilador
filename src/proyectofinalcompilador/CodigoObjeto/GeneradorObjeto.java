package proyectofinalcompilador.CodigoObjeto;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Generador de codigo objeto (MIPS simplificado).
 */
public class GeneradorObjeto {

    private final StringBuilder assembly;
    private final Set<String> variables;
    private final Map<String, String> literalesCadena;
    private int contadorCadenas;

    public GeneradorObjeto() {
        assembly = new StringBuilder();
        variables = new LinkedHashSet<>();
        literalesCadena = new LinkedHashMap<>();
        contadorCadenas = 0;
    }

    public String generarCodigo(String codigoIntermedio) {
        if (codigoIntermedio == null || codigoIntermedio.trim().isEmpty()) {
            return "";
        }

        assembly.setLength(0);
        variables.clear();
        literalesCadena.clear();
        contadorCadenas = 0;

        String[] lineas = codigoIntermedio.split("\\R");
        for (String linea : lineas) {
            analizarLineaDatos(linea.trim());
        }

        escribirSeccionData();
        escribirSeccionTexto(lineas);
        return assembly.toString();
    }

    private void analizarLineaDatos(String linea) {
        if (linea.isEmpty()) {
            return;
        }

        if (linea.contains(" = ")) {
            String[] partes = linea.split(" = ", 2);
            variables.add(partes[0].trim());
            registrarOperandos(partes[1].trim());
            return;
        }

        if (linea.startsWith("READ ")) {
            variables.add(linea.substring(5).trim());
            return;
        }

        if (linea.startsWith("PRINT ")) {
            registrarOperandos(linea.substring(6).trim());
        }
    }

    private void registrarOperandos(String expr) {
        if (expr.isEmpty()) {
            return;
        }

        if (esLiteralCadena(expr)) {
            registrarCadena(expr);
            return;
        }

        String[] tokens = expr.split("\\s+");
        for (String token : tokens) {
            if (token.isEmpty() || esOperador(token) || esNumero(token)) {
                continue;
            }
            if (esLiteralCadena(token)) {
                registrarCadena(token);
                continue;
            }
            variables.add(token);
        }
    }

    private void escribirSeccionData() {
        assembly.append(".data\n");
        assembly.append("    salto_linea: .asciiz \"\\n\"\n");

        for (String var : variables) {
            if (!esNumero(var) && !esLiteralCadena(var)) {
                assembly.append("    ").append(var).append(": .word 0\n");
            }
        }

        for (Map.Entry<String, String> entry : literalesCadena.entrySet()) {
            assembly.append("    ").append(entry.getValue()).append(": .asciiz ").append(entry.getKey()).append("\n");
        }
    }

    private void escribirSeccionTexto(String[] lineas) {
        assembly.append("\n.text\n");
        assembly.append(".globl main\n\n");
        assembly.append("main:\n");

        for (String raw : lineas) {
            String linea = raw.trim();
            if (linea.isEmpty()) {
                continue;
            }
            traducirLinea(linea);
        }

        assembly.append("\n    li $v0, 10\n");
        assembly.append("    syscall\n");
    }

    private void traducirLinea(String linea) {
        assembly.append("    # ").append(linea).append("\n");

        if (linea.contains(" = ")) {
            traducirAsignacion(linea);
            return;
        }

        if (linea.startsWith("READ ")) {
            String var = linea.substring(5).trim();
            assembly.append("    li $v0, 5\n");
            assembly.append("    syscall\n");
            assembly.append("    sw $v0, ").append(var).append("\n");
            return;
        }

        if (linea.startsWith("PRINT ")) {
            traducirPrint(linea.substring(6).trim());
            return;
        }

        if (linea.startsWith("PARAM ") || linea.startsWith("CALL ")) {
            assembly.append("    # Compatibilidad con version anterior\n");
        }
    }

    private void traducirAsignacion(String linea) {
        String[] partes = linea.split(" = ", 2);
        String destino = partes[0].trim();
        String derecha = partes[1].trim();

        String[] tokens = derecha.split("\\s+");
        if (tokens.length == 3 && esOperador(tokens[1])) {
            cargarEnRegistro(tokens[0], "$t0");
            cargarEnRegistro(tokens[2], "$t1");
            switch (tokens[1]) {
                case "+":
                    assembly.append("    add $t2, $t0, $t1\n");
                    break;
                case "-":
                    assembly.append("    sub $t2, $t0, $t1\n");
                    break;
                case "*":
                    assembly.append("    mul $t2, $t0, $t1\n");
                    break;
                case "/":
                    assembly.append("    div $t0, $t1\n");
                    assembly.append("    mflo $t2\n");
                    break;
                default:
                    assembly.append("    # Operador no soportado: ").append(tokens[1]).append("\n");
                    break;
            }
            assembly.append("    sw $t2, ").append(destino).append("\n");
            return;
        }

        cargarEnRegistro(derecha, "$t0");
        assembly.append("    sw $t0, ").append(destino).append("\n");
    }

    private void traducirPrint(String arg) {
        if (esLiteralCadena(arg)) {
            String label = registrarCadena(arg);
            assembly.append("    la $a0, ").append(label).append("\n");
            assembly.append("    li $v0, 4\n");
            assembly.append("    syscall\n");
        } else if (esNumero(arg)) {
            assembly.append("    li $a0, ").append(valorEnteroMips(arg)).append("\n");
            assembly.append("    li $v0, 1\n");
            assembly.append("    syscall\n");
        } else {
            assembly.append("    lw $a0, ").append(arg).append("\n");
            assembly.append("    li $v0, 1\n");
            assembly.append("    syscall\n");
        }

        assembly.append("    la $a0, salto_linea\n");
        assembly.append("    li $v0, 4\n");
        assembly.append("    syscall\n");
    }

    private void cargarEnRegistro(String op, String reg) {
        if (esNumero(op)) {
            assembly.append("    li ").append(reg).append(", ").append(valorEnteroMips(op)).append("\n");
        } else if (esLiteralCadena(op)) {
            String label = registrarCadena(op);
            assembly.append("    la ").append(reg).append(", ").append(label).append("\n");
        } else {
            assembly.append("    lw ").append(reg).append(", ").append(op).append("\n");
        }
    }

    private boolean esOperador(String token) {
        return "+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token);
    }

    private boolean esNumero(String token) {
        return token.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?");
    }

    private boolean esLiteralCadena(String token) {
        return token.length() >= 2 && token.startsWith("\"") && token.endsWith("\"");
    }

    private String registrarCadena(String literal) {
        if (literalesCadena.containsKey(literal)) {
            return literalesCadena.get(literal);
        }
        String label = "str_" + (++contadorCadenas);
        literalesCadena.put(literal, label);
        return label;
    }

    private String valorEnteroMips(String numero) {
        if (numero.matches("-?\\d+")) {
            return numero;
        }
        int convertido = (int) Math.round(Double.parseDouble(numero));
        return Integer.toString(convertido);
    }
}
