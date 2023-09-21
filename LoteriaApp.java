import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;

public class LoteriaApp extends JFrame {
    private JTextField nombreField, cedulaField, apuestaField, numeroField;
    private JButton jugarButton, cerrarButton, volverAJugarButton;
    private JComboBox<String> loteriaComboBox;
    private JLabel resultadoLabel, numeroMaquinaLabel;

    private double ganancias;

    public LoteriaApp() {
        setTitle("Loteria App");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Crear un panel para la parte izquierda con el logo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setPreferredSize(new Dimension(300, 750));

        ImageIcon logo = new ImageIcon("/Users/juanjo/Downloads/loteria.png");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        panelIzquierdo.add(logoLabel);
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);

        // Crear un panel para la parte derecha con los campos y botones
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridLayout(8, 2));

        panelDerecho.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panelDerecho.add(nombreField);

        panelDerecho.add(new JLabel("Cedula (8-10 números):"));
        cedulaField = new JTextField();
        panelDerecho.add(cedulaField);

        panelDerecho.add(new JLabel("Loteria:"));
        String[] loterias = {"4 números", "6 números", "8 números"};
        loteriaComboBox = new JComboBox<>(loterias);
        panelDerecho.add(loteriaComboBox);

        panelDerecho.add(new JLabel("Tu número:"));
        numeroField = new JTextField();
        panelDerecho.add(numeroField);

        panelDerecho.add(new JLabel("Valor de apuesta ($):"));
        apuestaField = new JTextField();
        panelDerecho.add(apuestaField);

        jugarButton = new JButton("Jugar");
        panelDerecho.add(jugarButton);

        cerrarButton = new JButton("Cerrar");
        panelDerecho.add(cerrarButton);

        resultadoLabel = new JLabel();
        panelDerecho.add(resultadoLabel);

        numeroMaquinaLabel = new JLabel();
        panelDerecho.add(numeroMaquinaLabel);

        volverAJugarButton = new JButton("Volver a Jugar");
        panelDerecho.add(volverAJugarButton);
        volverAJugarButton.setEnabled(false);

        jugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugarLoteria();
            }
        });

        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        volverAJugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });

        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    private void jugarLoteria() {
        String nombre = nombreField.getText();
        String cedula = cedulaField.getText();
        String numeroText = numeroField.getText();
        String loteriaSeleccionada = (String) loteriaComboBox.getSelectedItem();
        String apuestaText = apuestaField.getText().replace(",", ""); // Elimina las comas de miles

        if (!nombre.matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cedula.matches("^[0-9]{8,10}$")) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener entre 8 y 10 números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!numeroText.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "El número debe contener solo números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!apuestaText.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(this, "El valor de apuesta debe contener solo números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int numeroJugador = Integer.parseInt(numeroText);
        double apuesta = Double.parseDouble(apuestaText);

        int numeroLoteria;
        int digitosLoteria;
        switch (loteriaSeleccionada) {
            case "4 números":
                numeroLoteria = new Random().nextInt(10000);
                digitosLoteria = 4;
                break;
            case "6 números":
                numeroLoteria = new Random().nextInt(1000000);
                digitosLoteria = 6;
                break;
            case "8 números":
                numeroLoteria = new Random().nextInt(100000000);
                digitosLoteria = 8;
                break;
            default:
                numeroLoteria = 0;
                digitosLoteria = 0;
                break;
        }

        numeroMaquinaLabel.setText("Número de la lotería de " + digitosLoteria + " números: " + numeroLoteria);

        if (numeroJugador == numeroLoteria) {
            ganancias = apuesta * (loteriaSeleccionada.equals("4 números") ? 3 : (loteriaSeleccionada.equals("6 números") ? 6 : 20));
            DecimalFormat df = new DecimalFormat("#,###");
            resultadoLabel.setText("¡Felicidades! Has ganado $" + df.format(ganancias) + ".");
            volverAJugarButton.setEnabled(true);
        } else {
            resultadoLabel.setText("Perdiste. Inténtalo de nuevo.");
            volverAJugarButton.setEnabled(true);
        }
    }

    private void reiniciarJuego() {
        numeroMaquinaLabel.setText("");
        resultadoLabel.setText("");
        nombreField.setText("");
        cedulaField.setText("");
        numeroField.setText("");
        apuestaField.setText("");
        loteriaComboBox.setSelectedIndex(0);
        volverAJugarButton.setEnabled(false);
        ganancias = 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoteriaApp().setVisible(true);
            }
        });
    }
}
