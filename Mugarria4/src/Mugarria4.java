import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Mugarria4 extends JFrame implements ActionListener {

    private JComboBox <String> opciones;

    private static final String carpetaFotos = "fotos";

    private ImageIcon imageIcon;

    private JLabel labelFoto;

    private JPanel panelMain;

    private JCheckBox checkBox;

    private JTextArea textArea;

    private JButton save;

    public Mugarria4() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mugarria4");
        setSize(500, 500);

        this.panelMain = new JPanel();

        this.imageIcon = new ImageIcon("./fotos/foto1.png");
        Image foto = this.imageIcon.getImage();
        Image newfoto = foto.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon newimageIcon = new ImageIcon(newfoto);
        this.labelFoto = new JLabel(newimageIcon);

        this.opciones = new JComboBox<String>();
        load_combo();
        this.opciones.addActionListener(new CargarImagen(this.opciones, this.labelFoto));

        this.checkBox = new JCheckBox("Save your comment",true);

        this.textArea = new JTextArea();
        this.textArea.setPreferredSize(new Dimension(300, 200));

        this.checkBox = new JCheckBox("Save your comment",true);

        this.save = new JButton("SAVE");

        //Eventos

        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedImage = (String) opciones.getSelectedItem();
                String comment = textArea.getText();
                OutputStream outputStream= null;

                if (checkBox.isSelected()) {
                    String fileName = selectedImage.substring(0, selectedImage.lastIndexOf(".")) + ".txt";
                    try {
                        outputStream = new FileOutputStream(fileName, true);
                        outputStream.write(comment.getBytes());
                        outputStream.write("\n".getBytes());

                    }
                    catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }finally {
                        try {
                            outputStream.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });


        //agregar elementos al panel

        this.panelMain.setLayout(new BoxLayout ( this.panelMain, BoxLayout.Y_AXIS));
        this.panelMain.add(this.opciones);
        this.panelMain.add(this.labelFoto);
        this.panelMain.add(this.checkBox);
        this.panelMain.add(this.textArea);
        this.panelMain.add(this.save);
        this.add(panelMain);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
    private void load_combo() {
        File carpeta = new File("./" + this.carpetaFotos);
        File[] archivos = carpeta.listFiles();
        for (File archivo : archivos) {
            if (archivo.isFile()) {
                opciones.addItem(archivo.getName());
            }
        }
    }

    public static void main(String[] args) {

        // Ask for password
        String input = JOptionPane.showInputDialog(null, "Pasahitza sartu:");

        // Check password
        if (input == null || !input.equals("damocles")) {
            ImageIcon image = new ImageIcon("./Egoitz.jpg");

            JOptionPane.showMessageDialog(null, "Pssahitza gaizki jarri duzu, Egoitz", "Error", JOptionPane.INFORMATION_MESSAGE, image);
            System.exit(0);
        }

        // Password is correct, continue with the main window
        Mugarria4 ventana = new Mugarria4();
        ventana.setVisible(true);

        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "Bye!");
            }
        });
    }
}


