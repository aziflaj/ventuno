package com.aziflaj.ventuno;

import java.util.Stack;

public class CardDeck {
    private Stack<Card> deck;

    public CardDeck() {
        deck = new Stack<>();
        int i = 0;
        while (i < 52) {
            Card card = Card.generateRandomCard();
            if (!cardDuplicate(card)) {
                deck.push(card);
                i++;
            }
        }
    }

    public Card drawCard() {
        return deck.pop();
    }

    private boolean cardDuplicate(Card card) {
        for (Card c : deck) {
            if (c.equals(card)) return true;
        }
        return false;
    }
}
