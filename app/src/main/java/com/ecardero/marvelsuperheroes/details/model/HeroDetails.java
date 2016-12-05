package com.ecardero.marvelsuperheroes.details.model;

import com.ecardero.marvelapi.api.objects.Url;
import com.ecardero.marvelsuperheroes.search.model.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enrique Cardero on 03/12/2016.
 */

public class HeroDetails extends Hero {
    private List<Url> Urls;
    private ArrayList<Comic> Comics = new ArrayList<>();
    private ArrayList<Event> Events = new ArrayList<>();
    private int TotalComics = 0;
    private int TotalEvents = 0;

    public HeroDetails(Hero hero){
        this(hero.getId(), hero.getName(), hero.getDescription(), hero.getImage());
    }

    public HeroDetails(String id, String name, String description, String image) {
        super(id, name, description, image);
    }

    public HeroDetails(Builder builder){
        super(builder.Hero.getId(), builder.Hero.getName(),builder.Hero.getDescription(),builder.Hero.getImage());
        Comics = builder.Comics;
        Events = builder.Events;
        Urls = builder.Urls;
        TotalComics = builder.TotalComics;
        TotalEvents = builder.TotalEvents;
    }

    public ArrayList<Comic> getComics() {
        return Comics;
    }

    public void setComics(ArrayList<Comic> comics) {
        Comics = comics;
    }

    public void addComic(Comic comic){
        Comics.add(comic);
    }

    public ArrayList<Event> getEvents() {
        return Events;
    }

    public void setEvents(ArrayList<Event> events) {
        Events = events;
    }

    public void addEvent(Event event){
        Events.add(event);
    }

    public List<Url> getUrls() {
        return Urls;
    }

    public void setUrls(List<Url> urls) {
        Urls = urls;
    }

    public int getTotalEvents() {
        return TotalEvents;
    }

    public int getTotalComics() {
        return TotalComics;
    }


    public static class Builder{
        private Hero Hero;
        private List<Url> Urls;
        private ArrayList<Comic> Comics = new ArrayList<>();
        private ArrayList<Event> Events = new ArrayList<>();
        private int TotalComics = 0;
        private int TotalEvents = 0;

        public Builder(){}

        public Builder withHero(Hero hero){
            Hero = hero;
            return this;
        }

        public Builder withUrls(List<Url> urls){
            Urls = urls;
            return this;
        }

        public Builder withComics(ArrayList<Comic> comics){
            Comics = comics;
            return this;
        }

        public Builder addComic(Comic comic){
            Comics.add(comic);
            return this;
        }

        public Builder withEvents(ArrayList<Event> events){
            Events = events;
            return this;
        }

        public Builder addEvent(Event event){
            Events.add(event);
            return this;
        }

        public Builder setTotalComics(int totalComics){
            TotalComics = totalComics;
            return this;
        }

        public Builder setTotalEvents(int totalEvents){
            TotalEvents = totalEvents;
            return this;
        }

        public HeroDetails build(){
            HeroDetails h = new HeroDetails(this);
            return h;
        }
    }
}
