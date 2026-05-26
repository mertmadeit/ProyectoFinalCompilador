package proyectofinalcompilador.Main;

import proyectofinalcompilador.GUI.Interfaz;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // FlatLaf (evita dependencia en compilación usando el nombre de clase).
            // Requiere que lib/flatlaf-3.4.jar esté en el classpath en runtime.
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");

            // Para usar tema oscuro, usa:
            // UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");

        } catch (Exception e) {
            // Si FlatLaf no está disponible, seguimos con el LookAndFeel por defecto.
            System.err.println("No se pudo establecer FlatLaf (se usará el tema por defecto): " + e);
        }

        Interfaz interfaz = new Interfaz();
        interfaz.setVisible(true);
        interfaz.setLocationRelativeTo(null); // Centrar la ventana
    }
}