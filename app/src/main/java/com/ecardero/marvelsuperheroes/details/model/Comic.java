package com.ecardero.marvelsuperheroes.details.model;

/**
 * Created by Enrique Cardero on 03/12/2016.
 */

public class Comic {
    private String Image;
    private String Title;
    private String Description;

    public Comic(String image, String title, String description) {
        Image = image;
        Title = title;
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
