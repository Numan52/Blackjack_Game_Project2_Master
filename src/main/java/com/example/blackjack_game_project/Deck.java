package com.example.blackjack_game_project;

import com.example.blackjack_game_project.Card.Rank;
import com.example.blackjack_game_project.Card.Suit;

public class Deck { //floor

    private final Card[] cards = new Card[52]; //cards

    public Deck() {
        refill();
    }   //übergibt erneut alle Karten

    public final void refill() {   //give cards creating all cards
        int i = 0;
        for (Suit suit : Suit.values()) {   //value of cards
            for (Rank rank : Rank.values()) {    //rank of cards like
                cards[i++] = new Card(suit, rank);   //add all 52 cards to array
            }
        }
    }
///dadasda
    public Card draw_card() {
        Card card = null;
        while (card == null) {
            int index = (int)(Math.random()*cards.length); //give random card
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
}
