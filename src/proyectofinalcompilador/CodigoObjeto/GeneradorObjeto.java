package proyectofinalcompilador.CodigoObjeto;

import java.util.*;

/**
 * Generador de Código Objeto (MIPS Assembly)
 */
public class GeneradorObjeto {

    private StringBuilder assembly;
    private Set<String> variables;

    public GeneradorObjeto() {
        assembly = new StringBuilder();
        variables = new HashSet<>();
    }

    public String generarCodigo(String codigoIntermedio) {
        if (codigoIntermedio == null || codigoIntermedio.trim().isEmpty()) {
            return "";
        }

        assembly = new StringBuilder();
        variables.clear();

        // Encabezado MIPS
        assembly.append(".data\n");

        // Extraer variables para declararlas en .data
        String[] lineas = codigoIntermedio.split("\n");
        for (String linea : lineas) {
            extraerVariables(linea);
        }

        for (String var : variables) {
            // Solo declarar si es una variable real o temporal, y no literal
            if (!var.startsWith("\"") && !var.matches("-?\\d+")) {
                assembly.append("    ").append(var).append(": .word 0\n");
            }
        }

        assembly.append("\n.text\n");
        assembly.append(".globl main\n\n");
        assembly.append("main:\n");

        for (String linea : lineas) {
            traducirLinea(linea.trim());
        }

        // Fin del programa
        assembly.append("\n    # Salir del programa\n");
        assembly.append("    li $v0, 10\n");
        assembly.append("    syscall\n");

        return assembly.toString();
    }

    private void extraerVariables(String linea) {
        if (linea.contains(" = ")) {
            String[] parts = linea.split(" = ");
            variables.add(parts[0].trim());

            String derecha = parts[1].trim();
            if (derecha.contains(" ")) { // Operacion: T1 = a + b
                String[] tokens = derecha.split("\\s+");
                for (int i = 0; i < tokens.length; i += 2) {
                    variables.add(tokens[i]);
                }
            } else { // Asignacion: a = 5
                variables.add(derecha);
            }
        } else if (linea.startsWith("PARAM ")) {
            variables.add(linea.substring(6).trim());
        }
    }

    private void traducirLinea(String linea) {
        if (linea.isEmpty())
            return;

        assembly.append("    # ").append(linea).append("\n");

        if (linea.contains(" = ")) {
            String[] parts = linea.split(" = ");
            String destino = parts[0].trim();
            String derecha = parts[1].trim();

            if (derecha.contains(" ")) { // Operacion: d = op1 OPERADOR op2
                String[] tokens = derecha.split("\\s+");
                String op1 = tokens[0];
                String oper = tokens[1];
                String op2 = tokens[2];

                cargarEnRegistro(op1, "$t0");
                cargarEnRegistro(op2, "$t1");

                switch (oper) {
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
                        assembly.append("    div $t0, $t1\n    mflo $t2\n");
                        break;
                }
                guardarRegistro(destino, "$t2");
            } else { // Asignacion simple: d = o
                cargarEnRegistro(derecha, "$t0");
                guardarRegistro(destino, "$t0");
            }
        } else if (linea.startsWith("PARAM ")) {
            String arg = linea.substring(6).trim();
            cargarEnRegistro(arg, "$a0");
        } else if (linea.startsWith("CALL ")) {
            String func = linea.split(",")[0].substring(5).trim();
            if (func.contains("System.out.println")) {
                assembly.append("    li $v0, 1\n"); // Print Integer (simplificado)
                // Se asume que el argumento ya esta en $a0 por PARAM
                assembly.append("    syscall\n");
                // Newline
                assembly.append("    li $a0, 10\n");
                assembly.append("    li $v0, 11\n");
                assembly.append("    syscall\n");
            } else {
                assembly.append("    jal ").append(func.replace(".", "_")).append("\n");
            }
        }
    }

    private void cargarEnRegistro(String op, String reg) {
        if (op.matches("-?\\d+")) {
            assembly.append("    li ").append(reg).append(", ").append(op).append("\n");
        } else if (op.startsWith("\"")) {
            // Manejo simplificado de cadenas no implementado aqui
            assembly.append("    # (String literal ignored: ").append(op).append(")\n");
        } else {
            assembly.append("    lw ").append(reg).append(", ").append(op).append("\n");
        }
    }

    private void guardarRegistro(String var, String reg) {
        assembly.append("    sw ").append(reg).append(", ").append(var).append("\n");
    }
}
