package com.medicine.medication_reminder.storage;
//importerar Comparator som används för att sortera mediciner
import java.util.Comparator;
import java.util.List;//importerar List eftersom databasen returnerar en lista med mediciner

import org.springframework.stereotype.Component;//importerar Spring-annotation som gör att spring automatisk skapar ett objekt av denna klass

import com.medicine.medication_reminder.model.Medicine; //importerar medicin-klassen som representerar en medicine i systemet

// @Component betyder att Spring hanterar denna klass automatist
//när applikationen startar skapar Spring ett objekt av MedcineStore
//och gör det tillgängligt för andra delar av programmet
@Component
public class MedicineStore {

    //detta är repository-objektet som används för att kommunicera med databasen
    //repository innehåller metoder som save(), findAll(), deleteById() osv
    private final MedicineRepository repository;

    public MedicineStore(MedicineRepository repository) {
      this.repository = repository;
   }
    // konstruktor som används för dependency injection
    //Spring skickar automatiskt in MedicineRepository när applikationen startar
   // sparar den nya medicinen i database
   public void add1(Medicine m){
      this.repository.save(m);
   }

    // metod för att lägga till en medicin i databasen
    public void add(Medicine m) {
        //repository.save() sparar medicinen i databasen
        //om medicinen inte finns skapas en ny rad i tabellen
        repository.save(m);
    }

    // metod returnerar alla mediciner från databasen
    public List<Medicine> getAll() {
        List<Medicine> medicines = repository.findAll();

        // sortera:
        // 1) inte tagen idag först
        // 2) tagen idag sist
        // 3) inom varje grupp sortera efter tid
        medicines.sort(
            Comparator
                .comparing(Medicine::isTakenToday)
                .thenComparing(Medicine::getTime)
        );

        return medicines;
    }

    // tar bort en medicin baserat på id
    public void removeById(String id) {
        repository.deleteById(id);
    }

    // toggla taken för idag och spara tillbaka i databasen
    public void toggleTaken(String id) {
        Medicine medicine = repository.findById(id).orElse(null);

        if (medicine != null) {
            medicine.markTakenToday(!medicine.isTakenToday());
            repository.save(medicine);
        }
    }
}