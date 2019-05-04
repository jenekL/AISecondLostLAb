package com.example.ai_lab_one.repos;

import com.example.ai_lab_one.entities.Diseases;
import com.example.ai_lab_one.entities.Matches;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiseaseRepository extends CrudRepository<Diseases, Integer> {

    List<Diseases> findByDisease(String disease);

}
