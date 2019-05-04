package com.example.ai_lab_one.controllers;

import com.example.ai_lab_one.comparers.Compare;
import com.example.ai_lab_one.entities.Symptoms;
import com.example.ai_lab_one.repos.DiseaseRepository;
import com.example.ai_lab_one.repos.MatchesRepository;
import com.example.ai_lab_one.repos.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AddSymController {
    private final DiseaseRepository diseaseRepository;
    private final SymptomsRepository symptomsRepository;
    private final MatchesRepository matchesRepository;
    private Compare compare;

    @Autowired
    public AddSymController(DiseaseRepository diseaseRepository, SymptomsRepository symptomsRepository, MatchesRepository matchesRepository) {
        this.diseaseRepository = diseaseRepository;
        this.symptomsRepository = symptomsRepository;
        this.matchesRepository = matchesRepository;
        this.compare = new Compare(matchesRepository, diseaseRepository, symptomsRepository);
    }

    @GetMapping("/addsym")
    public String addSym(Map<String, Object> model){

        return "addsym";
    }

    @PostMapping("showSym")
    public String showSym(Map<String, Object> model){
        Iterable<Symptoms> d = symptomsRepository.findAll();
        model.put("symptoms", d);
        return "addsym";
    }

    @PostMapping("addSym")
    public String addSym(@RequestParam(required=false, name = "newSym") String newSym,
                         Map<String, Object> model){

        Symptoms symptoms = new Symptoms(newSym);

        symptomsRepository.save(symptoms);

        return "addsym";
    }

    @PostMapping("delSym")
    public String delSym(@RequestParam(required=false, name = "delSym") Integer delSym,
                         Map<String, Object> model){


        symptomsRepository.deleteById(delSym);

        return "addsym";
    }
}
