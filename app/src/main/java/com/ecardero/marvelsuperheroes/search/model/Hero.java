package com.ecardero.marvelsuperheroes.search.model;

/**
 * Created by Enrique Cardero on 02/12/2016.
 */

public class Hero {
    private String Id;
    private String Name;
    private String Description;
    private String Image;

    public Hero(String id, String name, String description, String image) {
        Id = id;
        Name = name;
        Description = description;
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String _Id) {
        this.Id = _Id;
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
