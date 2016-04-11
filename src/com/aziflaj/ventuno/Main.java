package com.aziflaj.ventuno;

public class Main {

    public static void main(String[] args) {
        System.out.println("Untrained:");
        PlayGame.playUntrained();

        System.out.println("\nLinear Stretching:");
        PlayGame.playWithModel(PlayGame.linearModel());

        System.out.println("\n1st Exponential Stretching:");
        PlayGame.playWithModel(PlayGame.exponentialModel());

        System.out.println("\n2nd Exponential Stretching:");
        PlayGame.playWithModel(PlayGame.otherExponentialModel());
    }
}
