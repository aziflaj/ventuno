package com.aziflaj.ventuno;

import java.util.ArrayList;
import java.util.List;

public class PlayGame {
    public static final int DATA_POINTS = 100000;
    private static Prob[] probs;

    public static void playUntrained() {
        int wins = 0;

        for (int i = 0; i < DATA_POINTS; i++) {
            CardDeck deck = new CardDeck();
            List<Card> dealerCards = new ArrayList<>();
            dealerCards.add(deck.drawCard());
            dealerCards.add(deck.drawCard());
            do {
                int dealerPoints = calculatePoints(dealerCards);

                // Black Jack
                if ((dealerPoints == 11 && isBlackJack(dealerCards)) || dealerPoints == 21) {
//                    System.out.print("\nWinner winner, Chicken dinner  ");
                    wins++;
                    break;
                } else if (dealerPoints > 21) {
//                    System.out.println("Lost");
                    break;
                } else {
                    dealerCards.add(deck.drawCard());
                }

            } while (true);
        }

        System.out.println("Winning probability: " + ((double) wins / DATA_POINTS));
    }

    public static void playWithModel(Double[] model) {
        int wins = 0;

        for (int i = 0; i < DATA_POINTS; i++) {
            CardDeck deck = new CardDeck();
            List<Card> dealerCards = new ArrayList<>();
            dealerCards.add(deck.drawCard());
            dealerCards.add(deck.drawCard());
            do {
                int dealerPoints = calculatePoints(dealerCards);

                // Black Jack
                if ((dealerPoints == 11 && isBlackJack(dealerCards)) || dealerPoints == 21) {
                    wins++;
                    break;
                } else if (dealerPoints > 21) {
//                    System.out.println("Lost");
                    break;
                } else if (dealerPoints > 11) {
                    Double prob = model[dealerPoints - 12];
                    double rnd = Math.random();
                    if (prob > rnd) {
                        dealerCards.add(deck.drawCard());
                    } else {
                        // end game
//                        System.out.printf("Game: %d\nSum before end: %d\n\n", i, dealerPoints);
                        wins++;
                        break;
                    }
                } else {
                    dealerCards.add(deck.drawCard());
                }

            } while (true);
        }

        System.out.println("Winning probability: " + ((double) wins / DATA_POINTS));
    }

    public static Double[] linearModel() {
        if (probs == null) {
            train();
        }

        ArrayList<Double> model = new ArrayList<>();

        for (Prob p : probs) {
            if (p.probability() < 1.0) {
                double linearProb = 3.28 * p.probability() - 1.6756;
                model.add(linearProb);
            }
        }

        Double[] probModel = new Double[model.size()];
        model.toArray(probModel);
        return probModel;
    }

    public static Double[] exponentialModel() {
        if (probs == null) {
            train();
        }

        ArrayList<Double> model = new ArrayList<>();

        for (Prob p : probs) {
            if (p.probability() < 1.0) {
                double linearProb = 1.7162 * Math.exp(p.probability()) - 2.8566;
                model.add(linearProb);
            }
        }

        Double[] probModel = new Double[model.size()];
        model.toArray(probModel);
        return probModel;
    }

    public static Double[] otherExponentialModel() {
        if (probs == null) {
            train();
        }

        ArrayList<Double> model = new ArrayList<>();

        for (Prob p : probs) {
            if (p.probability() < 1.0) {
                double linearProb = Math.pow(100000, p.probability() - .79);
                model.add(linearProb);
            }
        }

        Double[] probModel = new Double[model.size()];
        model.toArray(probModel);
        return probModel;
    }

    private static Prob[] train() {
        if (probs == null) {
            probs = new Prob[22];
            for (int i = 0; i < probs.length; i++) {
                probs[i] = new Prob();
            }
        }

        for (int i = 0; i < DATA_POINTS; i++) {
            CardDeck deck = new CardDeck();
            List<Card> dealerCards = new ArrayList<>();
            dealerCards.add(deck.drawCard());
            dealerCards.add(deck.drawCard());
            int lastSum = 0;
            do {
                int dealerPoints = calculatePoints(dealerCards);

                // Black Jack
                if ((dealerPoints == 11 && isBlackJack(dealerCards)) || dealerPoints == 21) {
                    probs[21].nom++;
                    probs[21].denom++;
                    break;
                } else if (dealerPoints > 21) {
                    probs[lastSum].denom++;
                    break;
                } else {
                    lastSum = calculatePoints(dealerCards);
                    dealerCards.add(deck.drawCard());
                    probs[lastSum].nom++;
                    probs[lastSum].denom++;
                }

            } while (true);
        }

        return probs;
    }

    private static int calculatePoints(List<Card> cards) {
        return cards.stream()
                .mapToInt(Card::getPoints)
                .sum();
    }

    private static boolean isBlackJack(List<Card> cards) {
        if (cards.size() == 2) {
            if (cards.get(0).getFace() == Card.Face.ACE || cards.get(1).getFace() == Card.Face.ACE)
                return true;
        }
        return false;
    }
}
