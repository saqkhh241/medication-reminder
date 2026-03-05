package com.medicine.medication_reminder.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Medicine implements Comparable<Medicine>{

    private String id;
    private String name;
    private LocalTime time;
    private String description;

    // NYTT: när den senast blev markerad som "taken"
    private LocalDate takenDate; // null = aldrig tagen

    public Medicine(String name, LocalTime time, String description){

        if(time == null){
            throw new IllegalArgumentException("time must not be null");
        }

        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
        this.description = description == null ? "" : description.trim();
        this.time = time;

        this.takenDate = null; // inte tagen från början
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public LocalTime getTime(){
        return time;
    }

    public String getDescription() {
        return description;
    }

    // NYTT: true bara om den är tagen IDAG
    public boolean isTakenToday(){
        return takenDate != null && takenDate.equals(LocalDate.now());
    }

    // NYTT: sätt taken för IDAG (true) eller avmarkera (false)
    public void markTakenToday(boolean taken){
        if(taken){
            this.takenDate = LocalDate.now();
        } else {
            this.takenDate = null;
        }
    }

    @Override
    public int compareTo(Medicine other){
        return this.time.compareTo(other.time);
    }

    @Override
    public String toString(){
        return time + " - " + name + " - " + description;
    }
}