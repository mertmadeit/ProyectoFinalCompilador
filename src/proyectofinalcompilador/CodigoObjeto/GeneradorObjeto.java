package proyectofinalcompilador.CodigoObjeto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Genera codigo NASM x86_64 a partir de codigo intermedio de tres direcciones.
 */
public class GeneradorObjeto {

    private static final Pattern PATRON_SALTO_FALSO = Pattern.compile(
            "^IF_FALSE\\s+(.+?)\\s+GOTO\\s+([A-Za-z_][A-Za-z0-9_]*)$",
            Pattern.CASE_INSENSITIVE);

    private static final Pattern PATRON_COMPARACION = Pattern.compile(
            "^(.+?)\\s*(<=|>=|==|!=|<|>)\\s*(.+)$");

    public String generarCodigo(String codigoIntermedio) {
        if (codigoIntermedio == null || codigoIntermedio.trim().isEmpty()) {
            return "; Sin codigo intermedio para traducir.";
        }

        List<String> instrucciones = separarInstrucciones(codigoIntermedio);
        Set<String> variables = recolectarVariables(instrucciones);
        Map<String, String> constantesDecimales = recolectarConstantesDecimales(instrucciones);

        StringBuilder asm = new StringBuilder();
        asm.append("; Codigo objeto generado en NASM x86_64\n");
        asm.append("section .data\n");
        if (variables.isEmpty()) {
            asm.append("    _dummy dq 0\n");
        } else {
            for (String variable : variables) {
                asm.append("    ").append(variable).append(" dq 0\n");
            }
        }
        for (Map.Entry<String, String> entry : constantesDecimales.entrySet()) {
            asm.append("    ").append(entry.getValue()).append(" dq ").append(entry.getKey()).append("\n");
        }

        asm.append("\nsection .text\n");
        asm.append("    global _start\n\n");
        asm.append("_start:\n");

        for (String instruccion : instrucciones) {
            traducirInstruccion(asm, instruccion, constantesDecimales);
        }

        asm.append("\n    ; exit(0)\n");
        asm.append("    mov rax, 60\n");
        asm.append("    xor rdi, rdi\n");
        asm.append("    syscall\n");

        return asm.toString();
    }

    private List<String> separarInstrucciones(String codigoIntermedio) {
        String[] lineas = codigoIntermedio.split("\\R");
        List<String> instrucciones = new ArrayList<>();
        for (String linea : lineas) {
            String limpia = linea.trim();
            if (!limpia.isEmpty()) {
                instrucciones.add(limpia);
            }
        }
        return instrucciones;
    }

    private Set<String> recolectarVariables(List<String> instrucciones) {
        Set<String> variables = new LinkedHashSet<>();

        for (String instruccion : instrucciones) {
            if (instruccion.endsWith(":")) {
                continue;
            }

            if (instruccion.startsWith("IF_FALSE ")) {
                Matcher m = PATRON_SALTO_FALSO.matcher(instruccion);
                if (m.matches()) {
                    agregarOperandosDeCondicion(variables, m.group(1));
                }
                continue;
            }

            if (instruccion.startsWith("GOTO ") || instruccion.startsWith("PARAM ")
                    || instruccion.startsWith("CALL ")) {
                continue;
            }

            if (instruccion.startsWith("PRINT ")) {
                String op = instruccion.substring("PRINT ".length()).trim();
                agregarSiVariable(variables, op);
                continue;
            }

            if (instruccion.startsWith("READ ")) {
                String var = instruccion.substring("READ ".length()).trim();
                agregarSiVariable(variables, var);
                continue;
            }

            int igual = instruccion.indexOf('=');
            if (igual < 0) {
                continue;
            }

            String destino = instruccion.substring(0, igual).trim();
            String expr = instruccion.substring(igual + 1).trim();

            agregarSiVariable(variables, destino);
            agregarOperandosDeExpresion(variables, expr);
        }

        return variables;
    }

    private Map<String, String> recolectarConstantesDecimales(List<String> instrucciones) {
        Map<String, String> constantes = new LinkedHashMap<>();
        Pattern patronDecimal = Pattern.compile("(?<![A-Za-z0-9_])-?\\d+\\.\\d+(?![A-Za-z0-9_])");

        for (String instruccion : instrucciones) {
            Matcher m = patronDecimal.matcher(instruccion);
            while (m.find()) {
                String numero = m.group();
                if (!constantes.containsKey(numero)) {
                    constantes.put(numero, "CONST_D" + (constantes.size() + 1));
                }
            }
        }

        return constantes;
    }

    private void traducirInstruccion(StringBuilder asm, String instruccion, Map<String, String> constantesDecimales) {
        if (instruccion.endsWith(":")) {
            asm.append(instruccion).append("\n");
            return;
        }

        if (instruccion.startsWith("GOTO ")) {
            String etiqueta = instruccion.substring("GOTO ".length()).trim();
            asm.append("    jmp ").append(etiqueta).append("\n");
            return;
        }

        if (instruccion.startsWith("IF_FALSE ")) {
            traducirIfFalse(asm, instruccion, constantesDecimales);
            return;
        }

        if (instruccion.startsWith("PRINT ")) {
            asm.append("    ; ").append(instruccion).append(" (salida no implementada)\n");
            return;
        }

        if (instruccion.startsWith("READ ")) {
            asm.append("    ; ").append(instruccion).append(" (entrada no implementada)\n");
            return;
        }

        if (instruccion.startsWith("PARAM ") || instruccion.startsWith("CALL ")) {
            asm.append("    ; ").append(instruccion).append("\n");
            return;
        }

        int igual = instruccion.indexOf('=');
        if (igual < 0) {
            asm.append("    ; Instruccion no reconocida: ").append(instruccion).append("\n");
            return;
        }

        String destino = instruccion.substring(0, igual).trim();
        String expr = instruccion.substring(igual + 1).trim();

        String[] partes = separarOperacion(expr);
        if (partes == null) {
            cargarEnRegistro(asm, "rax", expr);
            asm.append("    mov [").append(destino).append("], rax\n");
            return;
        }

        String op1 = partes[0];
        String operador = partes[1];
        String op2 = partes[2];

        cargarEnRegistro(asm, "rax", op1);
        cargarEnRegistro(asm, "rbx", op2);

        switch (operador) {
            case "+":
                asm.append("    add rax, rbx\n");
                break;
            case "-":
                asm.append("    sub rax, rbx\n");
                break;
            case "*":
                asm.append("    imul rax, rbx\n");
                break;
            case "/":
                asm.append("    cqo\n");
                asm.append("    idiv rbx\n");
                break;
            default:
                asm.append("    ; Operador no soportado en: ").append(instruccion).append("\n");
                return;
        }

        asm.append("    mov [").append(destino).append("], rax\n");
    }

    private void traducirIfFalse(StringBuilder asm, String instruccion, Map<String, String> constantesDecimales) {
        Matcher salto = PATRON_SALTO_FALSO.matcher(instruccion);
        if (!salto.matches()) {
            asm.append("    ; IF_FALSE invalido: ").append(instruccion).append("\n");
            return;
        }

        String condicion = limpiarParentesisExternos(salto.group(1).trim());
        String etiqueta = salto.group(2).trim();

        Matcher comparacion = PATRON_COMPARACION.matcher(condicion);
        if (comparacion.matches()) {
            String op1 = comparacion.group(1).trim();
            String rel = comparacion.group(2).trim();
            String op2 = comparacion.group(3).trim();

            if (esComparacionDecimal(op1, op2)) {
                traducirComparacionDecimal(asm, op1, rel, op2, etiqueta, constantesDecimales);
                return;
            }

            cargarEnRegistro(asm, "rax", op1);
            cargarEnRegistro(asm, "rbx", op2);
            asm.append("    cmp rax, rbx\n");
            asm.append("    ").append(jumpPorFalso(rel)).append(" ").append(etiqueta).append("\n");
            return;
        }

        if ("false".equalsIgnoreCase(condicion)) {
            asm.append("    jmp ").append(etiqueta).append("\n");
            return;
        }

        if ("true".equalsIgnoreCase(condicion)) {
            asm.append("    ; IF_FALSE true: no salta\n");
            return;
        }

        cargarEnRegistro(asm, "rax", condicion);
        asm.append("    cmp rax, 0\n");
        asm.append("    je ").append(etiqueta).append("\n");
    }

    private void traducirComparacionDecimal(
            StringBuilder asm, String op1, String rel, String op2, String etiqueta, Map<String, String> constantesDecimales) {
        cargarEnXmmComoDouble(asm, "xmm0", op1, constantesDecimales);
        cargarEnXmmComoDouble(asm, "xmm1", op2, constantesDecimales);
        asm.append("    ucomisd xmm0, xmm1\n");

        if ("==".equals(rel)) {
            asm.append("    jp ").append(etiqueta).append("\n");
            asm.append("    jne ").append(etiqueta).append("\n");
            return;
        }

        switch (rel) {
            case "<":
                asm.append("    jae ").append(etiqueta).append("\n");
                break;
            case "<=":
                asm.append("    ja ").append(etiqueta).append("\n");
                break;
            case ">":
                asm.append("    jbe ").append(etiqueta).append("\n");
                break;
            case ">=":
                asm.append("    jb ").append(etiqueta).append("\n");
                break;
            case "!=":
                asm.append("    je ").append(etiqueta).append("\n");
                break;
            default:
                asm.append("    ; Operador relacional no soportado: ").append(rel).append("\n");
                asm.append("    jmp ").append(etiqueta).append("\n");
                break;
        }
    }

    private String jumpPorFalso(String operadorRelacional) {
        switch (operadorRelacional) {
            case "<":
                return "jge";
            case "<=":
                return "jg";
            case ">":
                return "jle";
            case ">=":
                return "jl";
            case "==":
                return "jne";
            case "!=":
                return "je";
            default:
                return "jne";
        }
    }

    private void agregarOperandosDeCondicion(Set<String> variables, String condicion) {
        String limpia = limpiarParentesisExternos(condicion.trim());

        Matcher comp = PATRON_COMPARACION.matcher(limpia);
        if (comp.matches()) {
            agregarSiVariable(variables, comp.group(1).trim());
            agregarSiVariable(variables, comp.group(3).trim());
            return;
        }

        agregarSiVariable(variables, limpia);
    }

    private void agregarOperandosDeExpresion(Set<String> variables, String expr) {
        String[] partes = separarOperacion(expr);
        if (partes == null) {
            agregarSiVariable(variables, expr.trim());
            return;
        }

        agregarSiVariable(variables, partes[0]);
        agregarSiVariable(variables, partes[2]);
    }

    private String[] separarOperacion(String expr) {
        String[] ops = { " + ", " - ", " * ", " / " };
        for (String op : ops) {
            int idx = expr.indexOf(op);
            if (idx > 0) {
                String izquierda = expr.substring(0, idx).trim();
                String derecha = expr.substring(idx + op.length()).trim();
                if (!izquierda.isEmpty() && !derecha.isEmpty()) {
                    return new String[] { izquierda, op.trim(), derecha };
                }
            }
        }
        return null;
    }

    private boolean esComparacionDecimal(String op1, String op2) {
        return esDecimal(limpiarParentesisExternos(op1.trim()))
                || esDecimal(limpiarParentesisExternos(op2.trim()));
    }

    private void cargarEnXmmComoDouble(
            StringBuilder asm, String registroXmm, String operando, Map<String, String> constantesDecimales) {
        String limpio = limpiarParentesisExternos(operando.trim());

        if (esDecimal(limpio)) {
            String etiquetaConstante = constantesDecimales.get(limpio);
            if (etiquetaConstante == null) {
                asm.append("    ; Decimal sin constante registrada: ").append(limpio).append("\n");
                asm.append("    xorpd ").append(registroXmm).append(", ").append(registroXmm).append("\n");
                return;
            }
            asm.append("    movsd ").append(registroXmm).append(", [").append(etiquetaConstante).append("]\n");
            return;
        }

        if (esEntero(limpio)) {
            asm.append("    mov rax, ").append(limpio).append("\n");
            asm.append("    cvtsi2sd ").append(registroXmm).append(", rax\n");
            return;
        }

        if ("true".equalsIgnoreCase(limpio) || "false".equalsIgnoreCase(limpio)) {
            asm.append("    mov rax, ").append("true".equalsIgnoreCase(limpio) ? "1" : "0").append("\n");
            asm.append("    cvtsi2sd ").append(registroXmm).append(", rax\n");
            return;
        }

        asm.append("    mov rax, [").append(limpio).append("]\n");
        asm.append("    cvtsi2sd ").append(registroXmm).append(", rax\n");
    }

    private void cargarEnRegistro(StringBuilder asm, String registro, String operando) {
        String limpio = limpiarParentesisExternos(operando.trim());

        if (esEntero(limpio)) {
            asm.append("    mov ").append(registro).append(", ").append(limpio).append("\n");
            return;
        }

        if (esDecimal(limpio)) {
            long truncado = (long) Double.parseDouble(limpio);
            asm.append("    ; Decimal ").append(limpio).append(" truncado a entero ").append(truncado).append("\n");
            asm.append("    mov ").append(registro).append(", ").append(truncado).append("\n");
            return;
        }

        if ("true".equalsIgnoreCase(limpio)) {
            asm.append("    mov ").append(registro).append(", 1\n");
            return;
        }

        if ("false".equalsIgnoreCase(limpio)) {
            asm.append("    mov ").append(registro).append(", 0\n");
            return;
        }

        asm.append("    mov ").append(registro).append(", [").append(limpio).append("]\n");
    }

    private void agregarSiVariable(Set<String> variables, String token) {
        String limpio = limpiarParentesisExternos(token.trim());

        if (limpio.isEmpty()) {
            return;
        }
        if (esEntero(limpio) || esDecimal(limpio)) {
            return;
        }
        String lower = limpio.toLowerCase(Locale.ROOT);
        if ("true".equals(lower) || "false".equals(lower)) {
            return;
        }
        if (limpio.startsWith("\"") && limpio.endsWith("\"") && limpio.length() >= 2) {
            return;
        }
        if (!esIdentificadorValido(limpio)) {
            return;
        }

        variables.add(limpio);
    }

    private boolean esIdentificadorValido(String token) {
        return token.matches("[A-Za-z_][A-Za-z0-9_]*");
    }

    private boolean esEntero(String token) {
        return token.matches("-?\\d+");
    }

    private boolean esDecimal(String token) {
        return token.matches("-?\\d+\\.\\d+");
    }

    private String limpiarParentesisExternos(String texto) {
        String actual = texto.trim();
        while (actual.startsWith("(") && actual.endsWith(")") && actual.length() > 1) {
            String interno = actual.substring(1, actual.length() - 1).trim();
            if (!parentesisBalanceados(interno)) {
                break;
            }
            actual = interno;
        }
        return actual;
    }

    private boolean parentesisBalanceados(String texto) {
        int balance = 0;
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    return false;
                }
            }
        }
        return balance == 0;
    }
}
