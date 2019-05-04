package com.example.ai_lab_one.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "diseases_id", nullable = false)
    private Diseases diseases;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "symptoms_id", nullable = false)
    private Symptoms symptoms;

    private double confidenceMeasure;
    private double distrustMeasure;

    public double getDistrustMeasure() {
        return distrustMeasure;
    }

    public void setDistrustMeasure(double distrustMeasure) {
        this.distrustMeasure = distrustMeasure;
    }

    public Matches() {
    }

    public Matches(Diseases diseases, Symptoms symptoms, double confidenceMeasure, double distrustMeasure) {
        this.diseases = diseases;
        this.symptoms = symptoms;
        this.confidenceMeasure = confidenceMeasure;
        this.distrustMeasure = distrustMeasure;
    }

    public Matches(Diseases diseases, Symptoms symptoms) {
        this.diseases = diseases;
        this.symptoms = symptoms;
    }

    public double getConfidenceMeasure() {
        return confidenceMeasure;
    }

    public void setConfidenceMeasure(double confidenceMeasure) {
        this.confidenceMeasure = confidenceMeasure;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Diseases getDiseases() {
        return diseases;
    }

    public void setDiseases(Diseases diseases) {
        this.diseases = diseases;
    }

    public Symptoms getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Symptoms symptoms) {
        this.symptoms = symptoms;
    }
}
