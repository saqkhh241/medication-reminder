package com.medicine.medication_reminder.controller;

import com.medicine.medication_reminder.model.Medicine; //import av medicin
import com.medicine.medication_reminder.storage.MedicineStore;//import av store klassen 
import org.springframework.stereotype.Controller;//spring MVC
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;


//@controller betyder att denna klass hanterar web requests
@Controller
public class MedicineController {

    private final MedicineStore store; //referens till MedicineStore

    //konstruktor
    //spring skickar automatisk in MedicinStore
    public MedicineController(MedicineStore store) {
        this.store = store;
    }

    //när användaren går till "/"
    //redirecta till /medicines
    @GetMapping("/")
    public String home() {
        return "redirect:/medicines";
    }

    //visar listan med mediciner
    @GetMapping("/medicines")
    public String list(Model model) {
        model.addAttribute("medicines", store.getAll()); //lägg medicinlistan i modellen, så att thymeleaf kan använda den
        return "medicines"; //öppna templates/medicines.html
    }
    
    //visar formulärt för att lägga till medicin
    @GetMapping("/medicines/new")
    public String form() {
        return "medicine-form"; //öppna templates/medicine-form.html
    }
    
    //körs när formuläret skickas
    @PostMapping("/medicines")
    public String add(@RequestParam String name,
                      @RequestParam String time,
                      @RequestParam(required = false) String description) {

        store.add(new Medicine(name, LocalTime.parse(time), description)); //lägg till i store
        return "redirect:/medicines";
    }

    //tar bort en medicin
    @PostMapping("/medicines/delete")
    public String delete(@RequestParam String id) {
        store.removeById(id); //tar bort medicinen 
        return "redirect:/medicines"; //gå tillbaka till listan
    }
    @PostMapping("/medicines/toggle")
    public String toggle(@RequestParam String id) {

    store.toggleTaken(id);

    return "redirect:/medicines";
}
}