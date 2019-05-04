package com.example.ai_lab_one.repos;

import com.example.ai_lab_one.entities.Diseases;
import com.example.ai_lab_one.entities.Matches;
import com.example.ai_lab_one.entities.Symptoms;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchesRepository extends CrudRepository<Matches, Integer> {

    List<Matches> findByDiseases(Diseases diseases);
    List<Matches> findByDiseasesAndSymptoms(Diseases diseases, Symptoms symptoms);
    List<Matches> getByDiseasesAndSymptoms(Diseases diseases, Symptoms symptoms);
}
