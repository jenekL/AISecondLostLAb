package com.example.ai_lab_one.controllers;

import com.example.ai_lab_one.MatchesCoefs;
import com.example.ai_lab_one.comparers.Compare;
import com.example.ai_lab_one.entities.Symptoms;
import com.example.ai_lab_one.repos.DiseaseRepository;
import com.example.ai_lab_one.repos.MatchesRepository;
import com.example.ai_lab_one.repos.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    private final SymptomsRepository symptomsRepository;
    private Compare compare;

    @Autowired
    public TestController(DiseaseRepository diseaseRepository, SymptomsRepository symptomsRepository, MatchesRepository matchesRepository) {
        this.symptomsRepository = symptomsRepository;
        this.compare = new Compare(matchesRepository, diseaseRepository, symptomsRepository);
    }

    @GetMapping("/find")
    public String find(Map<String, Object> model){

        Iterable<Symptoms> s = symptomsRepository.findAll();
        model.put("symptoms", s);

        return "find";
    }



    @PostMapping("findDis")
    public String find(@RequestParam("symptom") List<String> symptom,
                       ModelMap model){


        if(symptom != null){

            List<MatchesCoefs> compareDiseases = compare.findDiseases(symptom);

            compareDiseases.sort(Comparator.comparingDouble(MatchesCoefs::getCoef).reversed());

            List<MatchesCoefs> list = new ArrayList<>();

            for (MatchesCoefs matchesCoefs: compareDiseases){
                if(matchesCoefs.getCoef() >= 0.5){
                    list.add(matchesCoefs);
                }
            }

            model.addAttribute("list", list);

        }
        return "result";
    }
}
