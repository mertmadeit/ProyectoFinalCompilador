package proyectofinalcompilador.GUI;


import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

@SuppressWarnings({ "serial", "this-escape" })
public class TextLineNumber extends JPanel
        implements CaretListener, DocumentListener {
    private static final long serialVersionUID = 1L;

    private final JTextArea textArea;

    public TextLineNumber(JTextArea textArea) {
        this.textArea = textArea;
        textArea.getDocument().addDocumentListener(this);
        textArea.addCaretListener(this);
        setPreferredSize(new Dimension(40, Integer.MAX_VALUE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fm = textArea.getFontMetrics(textArea.getFont());
        int lineHeight = fm.getHeight();
        int start = textArea.viewToModel2D(new Point(0, 0));
        int end = textArea.viewToModel2D(new Point(0, getHeight()));
        Element root = textArea.getDocument().getDefaultRootElement();
        int startLine = root.getElementIndex(start);
        int endLine = root.getElementIndex(end);

        for (int i = startLine; i <= endLine; i++) {
            String lineNumber = String.valueOf(i + 1);
            int y = (i + 1) * lineHeight;
            g.drawString(lineNumber, 5, y - 5);
        }
    }

    @Override public void caretUpdate(CaretEvent e) { repaint(); }
    @Override public void insertUpdate(DocumentEvent e) { repaint(); }
    @Override public void removeUpdate(DocumentEvent e) { repaint(); }
    @Override public void changedUpdate(DocumentEvent e) { repaint(); }
}
