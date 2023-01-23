package com.example.blackjack_game_project;

import com.example.blackjack_game_project.Card.Rank;
import com.example.blackjack_game_project.Card.Suit;

public class Deck {

    private final Card[] cards = new Card[52]; //Liste mit 52 Karten

    public Deck() {
        fill_deck(); //übergibt erneut alle Karten
    }

    public final void fill_deck() {   //give cards, creating all cards
        int i = 0;
        for (Suit suit : Suit.values()) {   //value of cards
            for (Rank rank : Rank.values()) {    //rank of cards like
                cards[i++] = new Card(suit, rank);   //add all 52 cards to array
            }
        }
    }
    public Card draw_card() { //Create one random card
        Card card = null;
        while (card == null) {
            int index = (int)(Math.random()*cards.length);
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
}
