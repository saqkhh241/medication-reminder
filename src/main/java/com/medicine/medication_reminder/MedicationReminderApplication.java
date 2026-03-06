package com.medicine.medication_reminder;

import java.awt.Desktop;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicationReminderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicationReminderApplication.class, args);

        // Öppna appen automatiskt i webbläsaren efter start
        new Thread(() -> {
            try {
                Thread.sleep(3000); // vänta 3 sek så servern hinner starta

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI("http://localhost:8081/medicines"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}