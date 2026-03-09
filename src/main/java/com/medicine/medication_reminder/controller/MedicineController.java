package com.medicine.medication_reminder.controller;

// Importerar LocalTime för att kunna konvertera tiden från formuläret till ett LocalTime-objekt
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medicine.medication_reminder.model.Medicine;
import com.medicine.medication_reminder.storage.MedicineStore;


// @Controller betyder att denna klass hanterar webbförfrågningar
// Den tar emot requests från användaren och returnerar HTML-sidor
@Controller
public class MedicineController {

    // Referens till MedicineStore
    // Den används för att hämta, lägga till och ta bort mediciner
    private final MedicineStore store;

    // Konstruktor
    // Spring skickar automatiskt in ett MedicineStore-objekt (dependency injection)
    public MedicineController(MedicineStore store) {
        this.store = store;
    }

    // När användaren går till startsidan "/"
    // omdirigeras användaren till /medicines
    @GetMapping("/")
    public String home() {

        // redirect betyder att webbläsaren skickas vidare till en annan URL
        return "redirect:/medicines";
    }

    // Denna metod körs när användaren öppnar sidan /medicines
    // Den visar listan med alla mediciner
    @GetMapping("/medicines")
    public String list(Model model) {

        // Hämtar alla mediciner från MedicineStore
        // och lägger dem i modellen
        // Thymeleaf kan sedan använda denna data i HTML-sidan
        model.addAttribute("medicines", store.getAll());

        // Returnerar namnet på HTML-filen som ska visas
        // Spring letar då efter templates/medicines.html
        return "medicines";
    }
    
    // Denna metod körs när användaren öppnar sidan /medicines/new
    // Den visar formuläret för att lägga till en ny medicin
    @GetMapping("/medicines/new")
    public String form() {

        // Returnerar HTML-sidan med formuläret
        // Spring öppnar templates/medicine-form.html
        return "medicine-form";
    }
    
    // Denna metod körs när formuläret skickas
    // Formuläret använder POST-metoden
    @PostMapping("/medicines")
    public String add(

        // Hämtar värdet från input-fältet "name"
        @RequestParam String name,

        // Hämtar tiden från formuläret (kommer som text)
        @RequestParam String time,

        // description är optional (required=false)
        // om användaren inte skriver något blir den null
        @RequestParam(required = false) String description
    ) {

        // Skapar ett nytt Medicine-objekt
        // LocalTime.parse konverterar texten till ett LocalTime-objekt
        store.add(new Medicine(name, LocalTime.parse(time), description));

        // Efter att medicinen lagts till skickas användaren tillbaka till listan
        return "redirect:/medicines";
    }

    // Denna metod körs när användaren trycker på delete-knappen
    @PostMapping("/medicines/delete")
    public String delete(@RequestParam String id) {

        // Tar bort medicinen från databasen baserat på id
        store.removeById(id);

        // Omdirigerar tillbaka till listan
        return "redirect:/medicines";
    }

    // Denna metod körs när användaren trycker på "Take" eller "Undo"
    @PostMapping("/medicines/toggle")
    public String toggle(@RequestParam String id) {

        // Ändrar status på medicinen
        // Om den inte är tagen -> markeras som tagen
        // Om den är tagen -> markeras som inte tagen
        store.toggleTaken(id);

        // Efter ändringen uppdateras sidan
        return "redirect:/medicines";
    }
}