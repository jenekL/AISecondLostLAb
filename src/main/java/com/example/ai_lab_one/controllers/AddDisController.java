package com.example.ai_lab_one.controllers;

import com.example.ai_lab_one.comparers.Compare;
import com.example.ai_lab_one.entities.Diseases;
import com.example.ai_lab_one.entities.Matches;
import com.example.ai_lab_one.entities.Symptoms;
import com.example.ai_lab_one.exceptions.DiseaseExistException;
import com.example.ai_lab_one.repos.DiseaseRepository;
import com.example.ai_lab_one.repos.MatchesRepository;
import com.example.ai_lab_one.repos.SymptomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AddDisController {
    private final DiseaseRepository diseaseRepository;
    private final SymptomsRepository symptomsRepository;
    private final MatchesRepository matchesRepository;
    private Compare compare;

    @Autowired
    public AddDisController(DiseaseRepository diseaseRepository, SymptomsRepository symptomsRepository, MatchesRepository matchesRepository) {
        this.diseaseRepository = diseaseRepository;
        this.symptomsRepository = symptomsRepository;
        this.matchesRepository = matchesRepository;
        this.compare = new Compare(matchesRepository, diseaseRepository, symptomsRepository);
    }

    @GetMapping("/addisiase")
    public String addisiase(Map<String, Object> model){

        showDis(model);

        Iterable<Symptoms> s = symptomsRepository.findAll();
        model.put("symptoms", s);

        return "addisiase";
    }


    @PostMapping("showDis")
    public String showDis(Map<String, Object> model){
        Iterable<Diseases> d = diseaseRepository.findAll();
        model.put("diseases", d);
        return "addisiase";
    }

    @PostMapping("addDis")
    public String add(@RequestParam(required=false, name = "newDisease") String newDisease,
                      @RequestParam(name = "symptom") List<String> symptoms,
                      @RequestParam("confidence") List<String> confidences,
                      @RequestParam("distrust") List<String> distrusts,
                      Map<String, Object> model) throws DiseaseExistException {


        if(compare.compareByExistDisease(symptoms) ) {
            if(compare.compareByNewDisease(symptoms)) {

                Diseases diseases = new Diseases(newDisease);

                diseaseRepository.save(diseases);

                int i = 0;
                if (symptoms != null) {

                    List<Double> dists = new ArrayList<>();
                    List<Double> confs = new ArrayList<>();

                    for (String s : confidences) {
                        if (!s.equals("")) {
                            confs.add(Double.parseDouble(s));
                        }
                    }
                    for (String s : distrusts) {
                        if (!s.equals("")) {
                            dists.add(Double.parseDouble(s));
                        }

                    }

                    for (String s : symptoms) {
                        int sy = Integer.parseInt(s);

                        matchesRepository.save(new Matches(diseaseRepository.findByDisease(newDisease).get(0),
                                symptomsRepository.findById(sy).get(),
                                confs.get(i), dists.get(i)));
                        i++;
                    }


                }
            }
            else{
                throw new DiseaseExistException("NELZYA TAK DELAT");
            }
        }
        else{
            throw new DiseaseExistException("Already existing disease");
        }

        return "index";
    }

    @ExceptionHandler(value = DiseaseExistException.class)
    public String diseaseExistException(){
        return "myexception";
    }

    @PostMapping("delDis")
    public String del(@RequestParam(required=false, name = "delDisease") Integer delDisease,
                      Map<String, Object> model){


        diseaseRepository.deleteById(delDisease);

        return "addisiase";
    }
}
