package com.ecardero.marvelsuperheroes.details.model;

/**
 * Created by Enrique Cardero on 03/12/2016.
 */

public class Event {
    private String Image;
    private String Name;
    private String Description;

    public Event(String name, String description, String image) {
        Name = name;
        Description = description;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
