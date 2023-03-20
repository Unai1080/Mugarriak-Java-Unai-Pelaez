import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Mugarria2 extends JFrame implements ActionListener {
    private JPanel panelIzquierda;
    private JPanel panelDerecha;
    private JComboBox <String> opciones;
    String[] lenguajes = {"python.txt", "c.txt", "java.txt"};
    JTextArea resultado;
    private JScrollPane scrollPane;
    private JButton clear;
    private JButton close;


    public Mugarria2() {
        //inicializar

        this.panelIzquierda = new JPanel();
        this.panelDerecha = new JPanel();
        this.opciones = new JComboBox<>(this.lenguajes);
        this.resultado = new JTextArea();
        this.scrollPane = new JScrollPane(this.resultado);
        this.clear = new JButton("Clear");
        this.close = new JButton("Close");


        //panel izquierda

        this.panelIzquierda.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.panelIzquierda.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.panelIzquierda.setAlignmentY(Component.TOP_ALIGNMENT);
        this.panelIzquierda.add(this.opciones);
        this.panelIzquierda.add(this.clear);

        //panel derecha

        this.panelDerecha.setLayout(new BoxLayout(this.panelDerecha, BoxLayout.Y_AXIS));
        this.scrollPane.setPreferredSize(new Dimension(300,300));
        this.resultado.setEditable(false);
        this.resultado.setLineWrap(true);
        this.resultado.setWrapStyleWord(true);
        this.panelDerecha.add(this.scrollPane);
        this.panelDerecha.add(this.close);

        // agregar los paneles

        this.setLayout(new BorderLayout());
        this.add(this.panelIzquierda, BorderLayout.WEST);
        this.add(this.panelDerecha, BorderLayout.EAST);

        // configurar el frame

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test Events: Files");
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(650,350));

        //Liesteners
        ActionListener listenerClear = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultado.setText("");
            }
        };
        this.clear.addActionListener(listenerClear);


        ActionListener listenerClose = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        this.close.addActionListener(listenerClose);

        ActionListener listenerComboBox = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String fileName = "./"+cb.getSelectedItem();
                resultado.setText(readFile(fileName));
            }
        };
        this.opciones.addActionListener(listenerComboBox);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public String readFile(String direccion){
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        String emaitza = " ";
        direccion="./c.txt";
        try {
            File archivo = new File(direccion);
            if (archivo.exists()) {
                System.out.println("existe");
            } else {
                System.out.println("no existe");
            }
            fileInputStream = new FileInputStream(direccion);
            dataInputStream  = new DataInputStream (fileInputStream);

            byte[] buffer = new byte[(int) fileInputStream.getChannel().size()];
            dataInputStream.readFully(buffer);
            emaitza = new String(buffer);


        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Archivo " + direccion + " no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                dataInputStream.close();
                fileInputStream.close();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cerrar el archivo " + direccion, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return emaitza;
    }
    public static void main(String[] args) {

        Mugarria2 frame = new Mugarria2();
    }

}



