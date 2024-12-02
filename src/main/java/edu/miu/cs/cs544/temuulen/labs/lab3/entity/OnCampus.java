package edu.miu.cs.cs544.temuulen.labs.lab3.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("OnCampus")
public class OnCampus extends Course{

    private String room;
    private int capacity;

    protected OnCampus() {}

    public OnCampus(String title, Date startDate, String professorName, String room, int capacity) {
        super(title, startDate, professorName);
        this.room = room;
        this.capacity = capacity;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}