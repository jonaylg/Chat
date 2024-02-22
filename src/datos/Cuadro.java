package datos;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cuadro extends JFrame {
    private JTextField mensajeField;
    private JTextArea chatArea;

    public Cuadro() {
        super("Cliente de Chat");

        // Configuración de la ventana
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creación de componentes
        mensajeField = new JTextField(30);
        JButton enviarButton = new JButton("Enviar");
        chatArea = new JTextArea();
        chatArea.setEditable(false);

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        panel.add(mensajeField, BorderLayout.SOUTH);
        panel.add(enviarButton, BorderLayout.EAST);

        // Agregar panel al frame
        add(panel);

        // Acción del botón de enviar
        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });

        // Escucha del campo de texto para enviar al presionar Enter
        mensajeField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });
    }

    // Método para enviar mensaje
    public void enviarMensaje() {
        String mensaje = mensajeField.getText();
        chatArea.append(mensaje + "\n");
        mensajeField.setText("");
        // Aquí podrías enviar el mensaje al servidor si lo deseas
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Cuadro clienteGUI = new Cuadro();
                clienteGUI.setVisible(true);
            }
        });
    }*/
}
