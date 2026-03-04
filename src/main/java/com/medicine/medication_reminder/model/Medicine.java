package com.medicine.medication_reminder.model;

import java.time.LocalTime; //import för att kunna hantera tiden 
import java.util.UUID; //import för att kunna hantera tid(HH: mm)


//klassen representerar en medicin i systemet
//Copmarable gör att vi kan sortera mediciner efter tid
public class Medicine implements Comparable<Medicine> {

    private final String id; //unikt id för varje medicin, används tex när vi tar bort en medicin
    private final String name; //namnet på medicin
    private final LocalTime time; //tiden då medicin ska tas
    private final String description; // beskrivning tex efter frukost

    private boolean taken;

    //Konstrucktor- körs när vi skapar en ny medicin
    public Medicine(String name, LocalTime time, String description) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be empty"); //kontrollera att namnet är inte tomt
        if (time == null) throw new IllegalArgumentException("time must not be null"); //kontrollera att tiden är inte tomt
        this.id = UUID.randomUUID().toString(); //skapa en unikt id
        this.name = name.trim(); //trim() tar bort exstra mellan slag 
        this.time = time; //spara tiden 
        this.description = (description == null) ? "" : description.trim(); //om desciption är null sätt tom sträng
        this.taken=false;
    }

    public String getId() { return id; } //getter för id, används av Thymeleaf och controller
    public String getName() { return name; } //getter för name 
    public LocalTime getTime() { return time; } //getter för tid
    public String getDescription() { return description; } //getter för desciption
    public boolean isTaken(){
        return taken;
    }
    public void setTaken(boolean taken){
        this.taken=taken;
    }

    //metod som används när vi sorterar mediciner
    //collections.sort använder denna metod
    @Override
    public int compareTo(Medicine other) {
        return this.time.compareTo(other.time);
    }

    //bestämmer hur objektet visas som text
    //används i Thymeleaf när vi skriver ${m}
    @Override
    public String toString() {
        String status = taken ? "(taken)": "";
        //format :08:00 -alvedon- after breakfast
        return String.format("%02d:%02d - %s - %s",
                time.getHour(), time.getMinute(), name, description, status);
    }
}