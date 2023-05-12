package Model;

import Connection.DBkonexioa;
import Controller.EventHandler;
import Controller.IPictureViewer;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PictureViewer extends JFrame implements IPictureViewer {
    private  HashMap<Integer, Integer> visitMap;
    private JLabel photographerLabel;
    private JComboBox photographers;
    private JLabel dateLabel;
    private JXDatePicker data;
    private JList lista;
    private ImageIcon imageIcon;
    private JLabel imagen;
    private JButton award;
    private JButton remove;

    ArrayList <Photographer> arrayPhtographers;

    ArrayList <Pictures> arrayPictures;

    EventHandler eventHandler ;



    public PictureViewer(){

        //disposcion panel
        this.setLayout(new GridLayout(3,2,20,20));
        this.setPreferredSize(new Dimension(1000,600));

        //inicializar JLabel
        this.photographerLabel = new JLabel("Model.Photographer: ");
        this.dateLabel = new JLabel("Photos after");

        //arrayList
        this.arrayPictures = new ArrayList<>();
        this.arrayPhtographers = new ArrayList<>();

        //evtHandler
        this.eventHandler= new EventHandler(this);

        //HashMap
        this.visitMap = new HashMap<>();

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

        //botones
        this.award = new JButton("AWARD");
        this.remove = new JButton("REMOVE");

        this.award.setPreferredSize(new Dimension(500,200));
        this.remove.setPreferredSize(new Dimension(500,200));

        this.award.addActionListener(eventHandler);
        this.remove.addActionListener(eventHandler);

        //paneles de arriba
        JPanel arribaIzquierda = new JPanel();
        arribaIzquierda.add(this.award);


        JPanel arribaDerecha = new JPanel();
        arribaDerecha.add(this.remove);



        //paneles del medio
        JPanel MedioarribaIzquierda = new JPanel();
        MedioarribaIzquierda.add(this.photographerLabel);
        MedioarribaIzquierda.add(this.photographers);


        JPanel MedioArribaDerecha = new JPanel();
        MedioArribaDerecha.add(dateLabel);
        MedioArribaDerecha.add(data);

        this.add(arribaIzquierda);

        this.add(arribaDerecha);

        this.add(MedioarribaIzquierda);

        this.add(MedioArribaDerecha);

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
            ResultSet select = connection.select("select * from Photographers");
            while (select.next()) {
                names.add(select.getString("name"));
                Photographer a = new Photographer(select.getString("name"), select.getBoolean("Awarded"), select.getInt("PhotographerId"));
                if (this.arrayPhtographers != null){
                    if (!this.arrayPhtographers.contains(a)){
                        this.arrayPhtographers.add(a);
                        System.out.println(a.toString());
                    }
                }else{
                    this.arrayPhtographers.add(a);
                }
            }
            select = connection.select("select * from pictures");
            while (select.next()) {

                Pictures a = new Pictures(select.getInt("Pictureld"), select.getString("Title"), select.getString("Date"), select.getString("File"), select.getInt("Visits"), select.getInt("PhotographerId"));
                if (this.arrayPhtographers!= null && !this.arrayPictures.contains(a)){
                    this.arrayPictures.add(a);
                }
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
        DefaultListModel<String> model = new DefaultListModel<>();
        int photographerId= -1;
        for ( Photographer a: this.arrayPhtographers){
            if (a.getName().equals(selectedPhotographer)) {
                photographerId = a.getPhotographerId();
                System.out.println(a.getPhotographerId());
            }
        }
        if (data.getDate()!=null){
            SimpleDateFormat newdate = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = newdate.format(this.data.getDate());
            for (Pictures a: this.arrayPictures){
                Date data1 = null;
                Date data2 = null;
                try {
                    data1 = newdate.parse(a.getDate());
                    data2 = newdate.parse((formattedDate));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if (a.getPhotographerId()==photographerId && data1.compareTo(data2)>0 ){
                    model.addElement(a.getTitle());
                }
            }
        }else{
            for (Pictures a: this.arrayPictures){
                if (a.getPhotographerId()==photographerId){
                    model.addElement(a.getTitle());
                }
        }
        lista.setModel(model);
        }
    }

    public void cargarImagen(String path){

        this.imageIcon = new ImageIcon(path);
        Image foto = this.imageIcon.getImage();
        Image newfoto = foto.getScaledInstance(250,250,Image.SCALE_SMOOTH);
        ImageIcon newlogo = new ImageIcon(newfoto);
        this.imagen.setIcon(newlogo);

    }

    public void selectPath(String namePicture){
        String path = null;
        for (Pictures a : this.arrayPictures){
            if (a.getTitle().equals(namePicture)){
                path = a.getFile();
                a.setVisits(a.getVisits()+1);
            }
        }
        cargarImagen(path);



    }


    @Override
    public void award() {
        this.visitMap = createVisitsMap();
        int minimumVisits = Integer.parseInt(JOptionPane.showInputDialog("Minimum no of visits for getting a prize"));
        for (Map.Entry<Integer,Integer> a: this.visitMap.entrySet()){
            int photographerId = -1;
            if (a.getValue() >= minimumVisits){
                photographerId = a.getKey();
                for (Photographer b : this.arrayPhtographers){
                    if (b.getPhotographerId() == photographerId){
                        b.setAwarded(true);
                        String message = b.getName() + " awarded ";
                        JOptionPane.showMessageDialog(null, message, "Awarded", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }

    @Override
    public void remove() {
        Iterator<Pictures> it = this.arrayPictures.iterator();
        while (it.hasNext()) {
            Pictures b = it.next();
            if (b.getVisits() == 0) {
                boolean removed = false;
                for (Photographer a : this.arrayPhtographers) {
                    if (!a.isAwarded()) {
                        int respuesta = JOptionPane.showConfirmDialog(null, "Do you want to delete " + b.getTitle() + " ?", "Delete", JOptionPane.YES_NO_OPTION);
                        boolean borrarPicture = (respuesta == JOptionPane.YES_OPTION);
                        if (borrarPicture && !removed) {
                            it.remove();
                            removed = true;
                        }
                    }
                }
            }
        }
    }



    public HashMap createVisitsMap(){
        HashMap<Integer,Integer> visitMap = new HashMap<>();
        for (Photographer a: this.arrayPhtographers){
            int visitsCount=0;
            for (Pictures b: this.arrayPictures){
                if (b.getPhotographerId()==a.getPhotographerId()){
                    visitsCount= visitsCount + b.getVisits();
                }
            }
            visitMap.put(a.getPhotographerId(),visitsCount);
        }
        return visitMap;
    }
}
