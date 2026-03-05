package com.medicine.medication_reminder.storage;

import java.util.ArrayList; 
import java.util.List;
import java.util.Comparator; // NYTT: för egen sortering

import org.springframework.stereotype.Component;

import com.medicine.medication_reminder.model.Medicine;

//@component betyder att spring hanterar denna klass
//den fungerar som en enkel databas i minnet
@Component
public class MedicineStore {

    private final List<Medicine> medicines = new ArrayList<>(); //lista som lagrar alla mediciner 

    //metod för att lägga till en medicin
    public void add(Medicine m) {
        medicines.add(m); //lägg till medicinen i listan
        sort(); // NYTT: sortera med vår egen regel (not taken först, taken sist, sen tid)
    }

    //returnerar alla mediciner
    public List<Medicine> getAll() {
        sort(); // NYTT: sortera alltid innan vi skickar listan till UI så allt är korrekt
        return new ArrayList<>(medicines); //returnerar en kopia av listan så att ingen kod kan ändra orignalet direkt
    }

    //tar bort en medicin baserat på id
    //removeif går igenom listan
    //och tar bort medicinen som har samma id
    public void removeById(String id) {
        medicines.removeIf(m -> m.getId().equals(id));
    }

    //NYTT: toggla taken för idag och flytta den i listan direkt
    public void toggleTaken(String id) {

        for (Medicine m : medicines) {

            if (m.getId().equals(id)) {

                // NYTT: toggla (om den är tagen idag -> avmarkera, annars markera)
                m.markTakenToday(!m.isTakenToday());
                break;
            }
        }

        sort(); // NYTT: efter toggle, sortera om så att "taken" hamnar längst ner
    }

    //NYTT: egen sortering:
    // 1) Inte tagen idag först
    // 2) Tagen idag sist
    // 3) Inom varje grupp: sortera efter tid
    private void sort() {
        medicines.sort(
            Comparator
                .comparing(Medicine::isTakenToday) // false kommer före true
                .thenComparing(Medicine::getTime)  // sen tid
        );
    }
}