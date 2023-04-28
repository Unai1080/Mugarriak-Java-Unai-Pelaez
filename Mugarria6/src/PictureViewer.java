import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PictureViewer extends JFrame {
    private JLabel photographerLabel;
    private JComboBox photographers;
    private JLabel dateLabel;
    private JXDatePicker data;
    private JList lista;
    private ImageIcon imageIcon;
    private JLabel imagen;

    public PictureViewer(){

        //disposcion panel
        this.setLayout(new GridLayout(2,2,20,20));
        this.setPreferredSize(new Dimension(1000,600));

        //inicializar JLabel
        this.photographerLabel = new JLabel("Photographer: ");
        this.dateLabel = new JLabel("Photos after");

        //combobox
        this.photographers = new JComboBox<>(loadCombo());

        photographers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadList();
            }
        });

        //lista
        this.lista = new JList<>();

        this.lista.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String nameImage = (String) lista.getSelectedValue();
                    selectPath(nameImage);

                }
            }
        });

        //fecha
        this.data = new JXDatePicker();

        //iincializar imagen
        this.imagen= new JLabel();

        //agregar componentes al panel
        JPanel arribaIzquierda = new JPanel();
        arribaIzquierda.add(this.photographerLabel);
        arribaIzquierda.add(this.photographers);


        JPanel arribaDerecha = new JPanel();
        arribaDerecha.add(dateLabel);
        arribaDerecha.add(data);

        this.add(arribaIzquierda);

        this.add(arribaDerecha);

        this.add(this.lista);

        this.add(this.imagen);


        // Configura el JFrame
        setTitle("Grid Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PictureViewer();
    }

    public String[] loadCombo() {
        DBkonexioa connection = null;
        List<String> names = new ArrayList<>();
        try {
            connection = new DBkonexioa();
            ResultSet select = connection.select("select name from Photographers");
            while (select.next()) {
                names.add(select.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.cerrar();
            }
        }
        return names.toArray(new String[0]);
    }
    public void loadList() {
        String selectedPhotographer = (String) photographers.getSelectedItem();
        if (selectedPhotographer == null) {
            return;
        }
        DBkonexioa connection = null;
        DefaultListModel<String> model = new DefaultListModel<>();
        try {
            connection = new DBkonexioa();
            ResultSet selectId = connection.select("select PhotographerId from Photographers where name ='" + selectedPhotographer + "'");
            String photographerId = null;
            if (selectId.next()) {
                photographerId = selectId.getString("PhotographerId");
            }
            ResultSet select = null;
            if (data.getDate()!=null){
                SimpleDateFormat newdate = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = newdate.format(this.data.getDate());
                select = connection.select("select title from pictures where photographerId='" + photographerId + "' and date >'" + formattedDate + "'");

            }else{
                select = connection.select("select title from pictures where photographerId='" + photographerId + "'");
            }
            while (select.next()) {
                model.addElement(select.getString("title"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.cerrar();
            }
        }
        lista.setModel(model);
    }

    public void cargarImagen(String path){

        this.imageIcon = new ImageIcon(path);
        Image foto = this.imageIcon.getImage();
        Image newfoto = foto.getScaledInstance(250,250,Image.SCALE_SMOOTH);
        ImageIcon newlogo = new ImageIcon(newfoto);
        this.imagen.setIcon(newlogo);
        DBkonexioa connection = null;
        try {
            connection = new DBkonexioa();
            connection.update("update pictures set visits=visits+1 where file ='" + path + "'");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.cerrar();
            }
        }
    }

    public void selectPath(String namePicture){
        DBkonexioa connection = null;
        String path = null;
        try {
            connection = new DBkonexioa();
            ResultSet selectId = connection.select("select File from Pictures where title ='" + namePicture + "'");
            if (selectId.next()) {
                path = selectId.getString("File");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cargarImagen(path);
            if (connection != null) {
                connection.cerrar();
            }
        }
    }


}
