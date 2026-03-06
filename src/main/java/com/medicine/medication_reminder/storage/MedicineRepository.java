package com.medicine.medication_reminder.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicine.medication_reminder.model.Medicine;

// Repository pratar direkt med databasen
public interface MedicineRepository extends JpaRepository<Medicine, String> {
}