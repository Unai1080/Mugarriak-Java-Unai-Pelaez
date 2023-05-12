package Model;

import java.text.SimpleDateFormat;

public class Pictures {
    private int pictureId;
    private String title;
    private String date;
    private String file;
    private int visits;
    private int photographerId;

    public Pictures(int pictureId, String title, String date, String file, int visits, int photographerId) {
        this.pictureId = pictureId;
        this.title = title;
        this.date = date;
        this.file = file;
        this.visits = visits;
        this.photographerId = photographerId;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getFile() {
        return file;
    }

    public int getVisits() {
        return visits;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }
}
