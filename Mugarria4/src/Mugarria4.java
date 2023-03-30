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
    private JPanel panelHorizontal;

    private JCheckBox checkBox;

    private JTextArea textArea;

    private JButton save;

    public Mugarria4() {
        //sistema
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mugarria4");
        setSize(500, 400);


        //inicializar componentes
        this.panelMain = new JPanel();
        this.panelHorizontal = new JPanel();
        //foto
        this.imageIcon = new ImageIcon("./fotos/foto1.png");
        Image foto = this.imageIcon.getImage();
        Image newfoto = foto.getScaledInstance(200,200,Image.SCALE_SMOOTH);
        ImageIcon newimageIcon = new ImageIcon(newfoto);
        //etiqueta
        this.labelFoto = new JLabel(newimageIcon);
        //combo_box
        this.opciones = new JComboBox<String>();
        this.opciones.setPreferredSize(new Dimension(400, 40));
        load_combo();//carga el combox de elementos
        //checkbox y text area
        this.checkBox = new JCheckBox("Save your comment",true);
        this.textArea = new JTextArea();
        this.textArea.setPreferredSize(new Dimension(300, 40));
        this.checkBox = new JCheckBox("Save your comment",true);
        //agregando al panel horizontal elementos
        this.panelHorizontal.setLayout(new FlowLayout());
        this.panelHorizontal.add(this.checkBox);
        this.panelHorizontal.add(this.textArea);
        //boton save
        this.save = new JButton("SAVE");

        //agregar elementos al panelMain
        this.panelMain.setLayout(new FlowLayout());
        this.panelMain.add(this.opciones);
        this.panelMain.add(this.labelFoto);
        this.panelMain.add(this.panelHorizontal);
        this.panelMain.add(this.save);
        this.add(panelMain);

        //Eventos

        //Evento para cargar la foto selecionada
        this.opciones.addActionListener(new CargarImagen(this.opciones, this.labelFoto));
        //Evento escribir en txt pulsando save

        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String argazkiarenIzena = (String) opciones.getSelectedItem();
                String testua = textArea.getText();
                OutputStream out= null;
                if (checkBox.isSelected()) {
                    String ArtxiboarenIzenFinala = argazkiarenIzena.substring(0, argazkiarenIzena.lastIndexOf(".")) + ".txt";
                    try {
                        out = new FileOutputStream(ArtxiboarenIzenFinala, true);
                        out.write(testua.getBytes()); //testua idatzi
                        out.write("\n".getBytes()); //Enter bat egin
                        textArea.setText("Saved");
                    }
                    catch (FileNotFoundException ex) {
                        System.out.println("Fitxategia ez da aurkitu");
                    } catch (IOException ex) {
                        System.out.println("In/Out errorea");
                    }finally {
                        try {
                            out.close();
                        } catch (IOException ex) {
                            System.out.println("Errorea ixterakoan fluxua");
                        }
                    }
                }
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {}

    //evento para cargar automatcamente los nombres de las fotos en el combobox
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

            JOptionPane.showMessageDialog(null, "Pasahitza gaizki jarri duzu, Egoitz", "Error", JOptionPane.INFORMATION_MESSAGE, image);
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


