package com.example.ai_lab_one.repos;

import com.example.ai_lab_one.entities.Symptoms;
import org.springframework.data.repository.CrudRepository;

public interface SymptomsRepository extends CrudRepository<Symptoms, Integer> {
    Symptoms findBySymptom(String symptom);
}
