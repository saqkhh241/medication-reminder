package com.medicine.medication_reminder.storage;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.medicine.medication_reminder.model.Medicine;

// @Component betyder att Spring hanterar denna klass
// Den fungerar nu som ett mellanlager mellan controller och databasen
@Component
public class MedicineStore {

    private final MedicineRepository repository;

    // Spring skickar in repository automatiskt
    public MedicineStore(MedicineRepository repository) {
        this.repository = repository;
    }

    // metod för att lägga till en medicin i databasen
    public void add(Medicine m) {
        repository.save(m);
    }

    // returnerar alla mediciner från databasen
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