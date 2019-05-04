package com.example.ai_lab_one.controllers;

import com.example.ai_lab_one.comparers.Compare;
import com.example.ai_lab_one.entities.Diseases;
import com.example.ai_lab_one.entities.Matches;
import com.example.ai_lab_one.entities.Symptoms;
import com.example.ai_lab_one.repos.DiseaseRepository;
import com.example.ai_lab_one.repos.MatchesRepository;
import com.example.ai_lab_one.repos.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"symptomms", "del_id"})
public class MainController {
    private final DiseaseRepository diseaseRepository;
    private final SymptomsRepository symptomsRepository;
    private final MatchesRepository matchesRepository;
    private Compare compare;

    @Autowired
    public MainController(DiseaseRepository diseaseRepository, SymptomsRepository symptomsRepository, MatchesRepository matchesRepository) {
        this.diseaseRepository = diseaseRepository;
        this.symptomsRepository = symptomsRepository;
        this.matchesRepository = matchesRepository;
        this.compare = new Compare(matchesRepository, diseaseRepository, symptomsRepository);
    }

    @GetMapping("/matches")
    public String matches(Map<String, Object> model,
                          @RequestParam(name = "name", required = false, defaultValue = "none") String name){

        Iterable<Symptoms> s = symptomsRepository.findAll();
        model.put("symptoms", s);

        Iterable<Diseases> d = diseaseRepository.findAll();
        model.put("diseases", d);

        model.put("name", name);

        return "matches";
    }



    @PostMapping("add")
    public ModelAndView findMatch(@RequestParam("symptom") List<String> symptom,
                                  @RequestParam(required = false, defaultValue = "none", name = "name") String name,
                                  @RequestParam("disease_id") List<String> disease,
                                  @RequestParam("confidence") List<String> confidences,
                                  @RequestParam("distrust") List<String> distrusts,
                                  Map<String, Object> model){

        matches(model, name);

        int dis = Integer.parseInt(disease.get(0));
        int i = 0;
        if(symptom != null){

            List<Double> dists = new ArrayList<>();
            List<Double> confs = new ArrayList<>();

            for(String s: confidences){
                if(!s.equals("")){
                    confs.add(Double.parseDouble(s));
                }
            }
            for(String s: distrusts){
                if(!s.equals("")){
                    dists.add(Double.parseDouble(s));
                }

            }

            for(String s: symptom){
                int sy = Integer.parseInt(s);

                matchesRepository.save(new Matches(diseaseRepository.findById(dis).get(), symptomsRepository.findById(sy).get(),
                        confs.get(i), dists.get(i)));
                i++;
            }


        }



        return new ModelAndView("matches", model);
    }



}
