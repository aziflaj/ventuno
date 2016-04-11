package com.aziflaj.ventuno;

public class Prob {
    public double nom;
    public double denom;

    public Prob() {
        nom = 0;
        denom = 0;
    }

    public double probability() {
        return nom/denom;
    }
}
