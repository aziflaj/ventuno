package com.aziflaj.ventuno;

import java.util.Random;

public class Card {
    private Face mFace;
    private Suit mSuit;

    private Card(Face face, Suit suit) {
        mFace = face;
        mSuit = suit;
    }

    public static Card generateRandomCard() {
        Random rnd = new Random();
        Face[] faces = Face.values();
        Suit[] suits = Suit.values();
        return new Card(faces[rnd.nextInt(faces.length)], suits[rnd.nextInt(suits.length)]);
    }

    public int getPoints() {
        return mFace.asInt();
    }

    public Face getFace() {
        return mFace;
    }

    @Override
    public String toString() {
        return String.format("%c, %c", mFace.asChar(), mSuit.asChar());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card &&  mFace == ((Card) obj).mFace && mSuit == ((Card) obj).mSuit;
    }

    public enum Face {
        ACE, TWO, THREE, FOUR, FIVE,
        SIX, SEVEN, EIGHT, NINE,
        TEN, JACK, QUEEN, KING;

        public int asInt() {
            switch (this) {
                case ACE:
                    return 1;
                case TWO:
                    return 2;
                case THREE:
                    return 3;
                case FOUR:
                    return 4;
                case FIVE:
                    return 5;
                case SIX:
                    return 6;
                case SEVEN:
                    return 7;
                case EIGHT:
                    return 8;
                case NINE:
                    return 9;
                case TEN:
                    return 10;
                case JACK:
                    return 10;
                case QUEEN:
                    return 10;
                case KING:
                    return 10;
                default:
                    return 0;
            }
        }

        public char asChar() {
            switch (this) {
                case ACE:
                    return 'A';
                case TWO:
                    return '2';
                case THREE:
                    return '3';
                case FOUR:
                    return '4';
                case FIVE:
                    return '5';
                case SIX:
                    return '6';
                case SEVEN:
                    return '7';
                case EIGHT:
                    return '8';
                case NINE:
                    return '9';
                case TEN:
                    return 'T';
                case JACK:
                    return 'J';
                case QUEEN:
                    return 'Q';
                case KING:
                    return 'K';
                default:
                    return '0';
            }
        }
    }

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES;

        public char asChar() {
            return name().charAt(0);
        }
    }
}
