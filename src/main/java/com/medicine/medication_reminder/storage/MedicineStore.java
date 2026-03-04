package com.medicine.medication_reminder.storage;

import com.medicine.medication_reminder.model.Medicine; //import av medicin -klassen
import org.springframework.stereotype.Component; //spring annotation, gör att spring automatisk skapar ett objekt av denna klass

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@component betyder att spring hanterar denna klass
//den fungerar som en enkel databas i minnet
@Component
public class MedicineStore {

    private final List<Medicine> medicines = new ArrayList<>(); //lista som lagrar alla mediciner 

    //metod för att lägga till en medicin
    public void add(Medicine m) {
        medicines.add(m); //lägg till medicinen i listan
        Collections.sort(medicines); //sortera medicin efter tiden 
    }

    //returnerar alla mediciner
    public List<Medicine> getAll() {
        return new ArrayList<>(medicines); //returnerar en kopia av listan så att ingen kod kan ändra orignalet direkt
    }

    //tar bort en medicin baserat på id
    //removeif går igenom listan
    //och tar bort medicinen som har samma id
    public void removeById(String id) {
        medicines.removeIf(m -> m.getId().equals(id));
    }
    public void toggleTaken(String id) {

        for (Medicine m : medicines) {

            if (m.getId().equals(id)) {

                m.setTaken(!m.isTaken());
                break;
            }
        }
    }

}