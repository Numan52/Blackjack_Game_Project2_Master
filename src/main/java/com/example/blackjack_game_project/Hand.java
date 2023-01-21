package com.example.blackjack_game_project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import com.example.blackjack_game_project.Card.Rank;
public class Hand {

    private ObservableList<Node> cards; //
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    private int aces = 0; //black jack !! 21 on hit

    public Hand(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public void take_card(com.example.blackjack_game_project.Card card) {
        cards.add(card);

        if (card.rank == Rank.ACE) {
            aces++;
        }

        if (value.get() + card.value > 21 && aces > 0) {
            value.set(value.get() + card.value - 10);    //Ass wird als '1' und nicht als '11' gezählt
            aces--;
        }
        else {
            value.set(value.get() + card.value);
        }
    }

    public void reset() {
        cards.clear();
        value.set(0);
        aces = 0;
    }

    public SimpleIntegerProperty value_property() {
        return value;
    }
}
