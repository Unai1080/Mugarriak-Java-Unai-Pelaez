package Model;

import java.util.ArrayList;

public class Photographer {
    private int photographerId;
    private String name;
    private boolean awarded;


    public Photographer(String name, boolean awarded, int photographerId) {
        this.name = name;
        this.awarded = awarded;
        this.photographerId = photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public String getName() {
        return name;
    }

    public boolean isAwarded() {
        return awarded;
    }
}
