package com.example.ai_lab_one.comparers;

import com.example.ai_lab_one.MatchesCoefs;
import com.example.ai_lab_one.entities.Diseases;
import com.example.ai_lab_one.entities.Matches;
import com.example.ai_lab_one.entities.Symptoms;
import com.example.ai_lab_one.repos.DiseaseRepository;
import com.example.ai_lab_one.repos.MatchesRepository;
import com.example.ai_lab_one.repos.SymptomsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Compare {
    private final MatchesRepository matchesRepository;
    private final DiseaseRepository diseaseRepository;
    private final SymptomsRepository symptomsRepository;

    public Compare(MatchesRepository matchesRepository, DiseaseRepository diseaseRepository, SymptomsRepository symptomsRepository) {
        this.matchesRepository = matchesRepository;
        this.diseaseRepository = diseaseRepository;
        this.symptomsRepository = symptomsRepository;
    }

    public Boolean compareByNewDisease(List<String> symptoms){

        Iterable<Diseases> all = diseaseRepository.findAll();
        boolean value = true;

        for(Diseases d:all){

            List<String> stringList = matchesRepository.findByDiseases(d)
                    .stream().map(Matches::getSymptoms)
                    .map(s -> s.getId() + "")
                    .collect(Collectors.toList());
            if(stringList.size() != 0) {
                if (symptoms.containsAll(stringList)) {
                    value = false;
                    break;
                }
            }

        }


        return value;
    }

    public boolean compareByExistDisease(List<String> symptoms){

        Iterable<Diseases> all = diseaseRepository.findAll();
        boolean value = true;


        for(Diseases d:all){

            if(matchesRepository.findByDiseases(d)
                    .stream().map(Matches::getSymptoms)
                    .map(s -> s.getId() + "")
                    .collect(Collectors.toList()).containsAll(symptoms)){
                value = false;
                break;
            }

        }


        return value;
    }

    public String findDis(List<String> symptoms){

        List<Diseases> all = (List<Diseases>) diseaseRepository.findAll();
        String disease = "";
        for(Diseases d:all){

            List<String> stringList = matchesRepository.findByDiseases(d)
                    .stream().map(Matches::getSymptoms)
                    .map(s -> s.getId() + "")
                    .collect(Collectors.toList());

            if(symptoms.size() == stringList.size()){
                if(stringList.containsAll(symptoms)){
                    disease = d.getDisease();



                    break;
                }
            }

        }


        return disease;
    }

    public List<MatchesCoefs> findDiseases(List<String> symptoms){
        List<MatchesCoefs> diseases = new ArrayList<>();
        List<Diseases> all = (List<Diseases>) diseaseRepository.findAll();

        double dov = 0.0;
        double nedov = 0.0;
        String disease = "";

        for(Diseases d:all){

            List<Matches> stringList = new ArrayList<>();

            for(String s: symptoms){
                Symptoms ss = symptomsRepository.findById(Integer.parseInt(s)).get();

                if(matchesRepository.findByDiseasesAndSymptoms(d,ss).size() != 0){
                    Matches m = matchesRepository.findByDiseasesAndSymptoms(d,ss).get(0);

                    stringList.add(m);

                }

            }



            for(Matches m : stringList){
                dov += (m.getConfidenceMeasure() * (1-dov));
                nedov += (m.getDistrustMeasure() * (1-nedov));
            }

            MatchesCoefs matchesCoefs = new MatchesCoefs(d.getDisease(), (dov - nedov));
            diseases.add(matchesCoefs);

        }


        return diseases;
    }



}
