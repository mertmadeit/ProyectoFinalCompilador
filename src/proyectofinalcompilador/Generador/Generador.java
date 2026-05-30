package proyectofinalcompilador.Generador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Mert
 */
public class Generador {
    public static void main(String[] args) throws Exception {
        String ruta1 = "src/proyectofinalcompilador/Lexico/LexerCup.flex";
        String ruta2 = "src/proyectofinalcompilador/Sintactico/Sintax.cup";
        String[] rutaS = { "-parser", "sintax", "-expect", "1", "-package", "proyectofinalcompilador.Sintactico", ruta2 };
        generar(ruta1, ruta2, rutaS);
    }

    public static void generar(String ruta1, String ruta2, String[] rutaS) throws Exception {
        File archivo;

        // Limpiar archivos generados previamente
        eliminarArchivosGenerados();

        // Generar con JFlex
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);

        // Generar con CUP
        java_cup.Main.main(rutaS);

        // Mover archivos generados
        Path symSource = Paths.get("sym.java");
        Path symDest = Paths.get("src/proyectofinalcompilador/Sintactico/sym.java");
        if (Files.exists(symDest)) {
            Files.delete(symDest);
        }
        if (Files.exists(symSource)) {
            Files.move(symSource, symDest);
        }

        Path sintaxSource = Paths.get("sintax.java");
        Path sintaxDest = Paths.get("src/proyectofinalcompilador/Sintactico/sintax.java");
        if (Files.exists(sintaxDest)) {
            Files.delete(sintaxDest);
        }
        if (Files.exists(sintaxSource)) {
            Files.move(sintaxSource, sintaxDest);
        }
    }

    public static void eliminarArchivosGenerados() throws IOException {
        // Eliminar archivos generados
        Path[] archivos = {
                Paths.get("src/proyectofinalcompilador/Sintactico/sym.java"),
                Paths.get("src/proyectofinalcompilador/Sintactico/sintax.java"),
                Paths.get("src/proyectofinalcompilador/Lexico/LexerCup.java")
        };

        for (Path archivo : archivos) {
            if (Files.exists(archivo)) {
                Files.delete(archivo);
            }
        }
    }

}
