import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class CargarImagen implements ActionListener {
    private JComboBox<String> comboBox;
    private JLabel labelFoto;

    public CargarImagen(JComboBox<String> comboBox, JLabel labelFoto) {
        this.comboBox = comboBox;
        this.labelFoto = labelFoto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtener el nombre de la imagen seleccionada
        String nombreImagen = (String) comboBox.getSelectedItem();

        // Cargar la imagen correspondiente
        ImageIcon imageIcon = new ImageIcon("./fotos/" + nombreImagen);
        Image foto = imageIcon.getImage();
        Image newfoto = foto.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon newimageIcon = new ImageIcon(newfoto);

        // Establecer la imagen en el JLabel
        labelFoto.setIcon(newimageIcon);
    }
}

