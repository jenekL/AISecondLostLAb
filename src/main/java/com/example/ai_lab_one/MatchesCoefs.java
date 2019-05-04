package com.example.ai_lab_one;

public class MatchesCoefs {
    private String disease;
    private Double coef;

    public MatchesCoefs(String disease, Double coef) {
        this.disease = disease;
        this.coef = coef;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Double getCoef() {
        return coef;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }
}
