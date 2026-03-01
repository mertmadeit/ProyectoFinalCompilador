package proyectofinalcompilador.Main;

import proyectofinalcompilador.GUI.Interfaz;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Usar FlatLaf Light (tema claro)
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Para usar tema oscuro, descomenta la siguiente línea:
            // UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception e) {
            System.err.println("Error al establecer FlatLaf: " + e);
        }

        Interfaz interfaz = new Interfaz();
        interfaz.setVisible(true);
    }
}