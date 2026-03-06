package com.medicine.medication_reminder.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// @Entity betyder att denna klass ska sparas i databasen som en tabell
@Entity
public class Medicine implements Comparable<Medicine>{

    // @Id betyder primärnyckel i databasen
    @Id
    private String id;

    private String name;
    private LocalTime time;
    private String description;

    // sparar vilket datum medicinen togs senast
    private LocalDate takenDate;

    // tom konstruktor krävs av JPA / Hibernate
    public Medicine() {
    }

    // vanlig konstruktor som används när vi skapar ny medicin i appen
    public Medicine(String name, LocalTime time, String description){

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("name must not be empty");
        }

        if(time == null){
            throw new IllegalArgumentException("time must not be null");
        }

        this.id = UUID.randomUUID().toString();
        this.name = name.trim();
        this.description = description == null ? "" : description.trim();
        this.time = time;
        this.takenDate = null; // från början inte tagen
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

    public String getDescription(){
        return description;
    }

    // true bara om medicinen är tagen idag
    public boolean isTakenToday(){
        return takenDate != null && takenDate.equals(LocalDate.now());
    }

    // markerar medicinen som tagen idag eller inte tagen
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