package com.example.ai_lab_one.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Diseases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String disease;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "diseases", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private Set<Matches> matches;


    public Diseases(String disease) {
        this.disease = disease;
    }

    public Diseases() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Set<Matches> getMatches() {
        return matches;
    }

    public void setMatches(Set<Matches> matches) {
        this.matches = matches;
    }
}
