package Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler implements ActionListener {
    private IPictureViewer iPictureViewer;

    public EventHandler(IPictureViewer iPictureViewer){
        this.iPictureViewer = iPictureViewer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botoia = (JButton) e.getSource();
        String botoi_izena = botoia.getText();
        String buttonName = botoia.getName();
        switch (botoi_izena){
            case "AWARD":
                this.iPictureViewer.award();
                break;
            case "REMOVE":
                this.iPictureViewer.remove();
            default:{
                System.out.println("Zeozer gaizki dabil, eventhanler kudeatu gabe");
            }
        }
    }
}

