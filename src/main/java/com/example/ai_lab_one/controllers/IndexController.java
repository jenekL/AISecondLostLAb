package com.example.ai_lab_one.controllers;

import com.example.ai_lab_one.comparers.Compare;
import com.example.ai_lab_one.entities.Diseases;
import com.example.ai_lab_one.entities.Matches;
import com.example.ai_lab_one.repos.DiseaseRepository;
import com.example.ai_lab_one.repos.MatchesRepository;
import com.example.ai_lab_one.repos.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    private final DiseaseRepository diseaseRepository;
    private final SymptomsRepository symptomsRepository;
    private final MatchesRepository matchesRepository;
    private Compare compare;

    @Autowired
    public IndexController(DiseaseRepository diseaseRepository, SymptomsRepository symptomsRepository, MatchesRepository matchesRepository) {
        this.diseaseRepository = diseaseRepository;
        this.symptomsRepository = symptomsRepository;
        this.matchesRepository = matchesRepository;
        this.compare = new Compare(matchesRepository, diseaseRepository, symptomsRepository);
    }



    @PostMapping("addNewExist")
    public String addNewExist(ModelMap model,

                              @RequestParam(name = "addNew") String name){

        Integer del_id = (Integer) model.get("del_id");
        List<String> symptoms = (List<String>) model.get("symptomms");

        diseaseRepository.deleteById(del_id);

        Diseases disease = new Diseases(name);
        diseaseRepository.save(disease);
        if(symptoms != null) {
            for (String s : symptoms) {
                matchesRepository.save(new Matches(disease, symptomsRepository.findById(Integer.parseInt(s)).get()));
            }
        }
        return "index";
    }


    @PostMapping("addNew")
    public String addNew(ModelMap model,
                         @RequestParam(name = "addNew") String name){

        List<String> symptoms = (List<String>) model.get("symptomms");
        Diseases disease = new Diseases(name);
        diseaseRepository.save(disease);
        if(symptoms != null) {
            for (String s : symptoms) {
                matchesRepository.save(new Matches(disease, symptomsRepository.findById(Integer.parseInt(s)).get()));
            }
        }
        return "index";
    }

    @GetMapping(path = "/")
    public String main(Map<String, Object> model){

        return "index";
    }
}
