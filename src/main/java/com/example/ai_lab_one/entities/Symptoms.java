package com.example.ai_lab_one.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Symptoms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String symptom;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "symptoms", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Set<Matches> matches;

    public Set<Matches> getMatches() {
        return matches;
    }

    public void setMatches(Set<Matches> matches) {
        this.matches = matches;
    }

    public Symptoms() {
    }

    public Symptoms(String symptom) {
        this.symptom = symptom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }
}
