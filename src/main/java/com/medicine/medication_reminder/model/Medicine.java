package com.medicine.medication_reminder.model;

import java.time.LocalDate;//importerar LocalDate för att kunna spara vilket datum medicinen togs
import java.time.LocalTime;//importerar LocalTime för att kunna spara vilket  tid medicinen ska tas
import java.util.UUID; //importerar UUID för att kunna skapa ett unikt id för varje medicin

import jakarta.persistence.Entity; //importerar JPA-annotation som markerar att denna klass ska sparas i databasen
import jakarta.persistence.Id; //importerar JPA-annotation som markerar primärnyckeln i databasen

// @Entity betyder att denna klass ska sparas i databasen som en tabell
@Entity
public class Medicine implements Comparable<Medicine>{

    // @Id betyder primärnyckel i databasen
    @Id
    private String id;

    private String name;
    private LocalTime time;
    private String days;
    
    private String description;

    // sparar vilket datum medicinen togs senast
    private LocalDate takenDate;

    // tom konstruktor krävs av JPA / Hibernate
    //Databasen använder denna för att skapa objekt när data läses från tabellen
    public Medicine() {
    }

    // vanlig konstruktor som används när vi skapar ny medicin i appen
    public Medicine(String name, LocalTime time,String days,  String description){

        //kontrollerar att name inte är null eller tomt
        //isBlank betyder att strängen är tom eller bara innehåller mellanslag
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("name must not be empty");
        }

        //kontrollerar att time inte är null
        //utan tid går det inte att använda medicinen i  påminnelsesystemet
        if(time == null){
            throw new IllegalArgumentException("time must not be null");
        }

        this.id = UUID.randomUUID().toString();//skapar ett unikt id för vaje ny medicin
        //om description är null sparas en tom sträng istället 
        //Annars sparas beskrivningen utan extra mellanslag
        this.name = name.trim();
        this.days = days == null ? "" : days.trim();
        this.description = description == null ? "" : description.trim();
        this.time = time; //sparar tiden för medicinen
        this.takenDate = null; // från början inte tagen
    }

    //returnerar medicinens id
    public String getId(){
        return id;
    }

    //returnerar medicinens namn
    public String getName(){
        return name;
    }

    //returnerar tiden då medicinen ska tas
    public LocalTime getTime(){
        return time;
    }

    public String getDays(){
    return days;
}

    //returnarar medicinen beskrivning
    public String getDescription(){
        return description;
    }

    // kontrollerar om medicinen är tagen idag
    //logiken är:
    //1.takenDate får inte vara null
    //2.takendate måste vara datum som dagens datum
    public boolean isTakenToday(){
        return takenDate != null && takenDate.equals(LocalDate.now());
    }

    // markerar medicinen som tagen eller inte tagen
    //om taken = true sparas dagens datum i takenDate
    //om taken = false sätts takenDate till null, vilket betyder "inte tagen"
    public void markTakenToday(boolean taken){
        if(taken){
            this.takenDate = LocalDate.now();
        } else {
            this.takenDate = null;
        }
    }

    //denna metod används när mediciner sorteras 
    //vi jämnför tiderna mellan två mediciner
    //om denna medicin har tidigare tid kommer den före i listan
    @Override
    public int compareTo(Medicine other){
        return this.time.compareTo(other.time);
    }

    //toString används när objektet ska visas som text
    //Exampel: 08:00 - Alvedon - After breakfast
        @Override
    public String toString(){
        return time + " - " + name + " - " + description;
    }

    public String getDaysFormatted() {
        if (days == null || days.isBlank()) {
            return "Every day";
        }

        String[] splitDays = days.split(",");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < splitDays.length; i++) {
            String day = splitDays[i].trim().toLowerCase();
            day = day.substring(0, 1).toUpperCase() + day.substring(1);

            result.append(day);

            if (i < splitDays.length - 1) {
                result.append(", ");
            }
        }

        return result.toString();
    }
    }