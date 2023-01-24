package com.example.blackjack_game_project;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import com.example.blackjack_game_project.Card.Rank;
public class Hand {

    private ObservableList<Node> cards; // allows for elements in the list of cards to be observed for changes.
                                        // automatic update of any part of the application that is using the list, without the need for manual updates
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0); //total value of cards in hand, allows for a variable to be observed for changes

    private int ACE_counter = 0; //zählt wie viel ASSe es gibt, um den Wert entweder als '1' oder als '11' ausgibt.

    public Hand(ObservableList<Node> cards) {
        this.cards = cards; //
    }

    public void take_card(Card card) {
        cards.add(card);
        if (card.rank == Rank.ACE) {
            ACE_counter++;
        }

        if (value.get() + card.value > 21 && ACE_counter > 0) {
            value.set(value.get() + card.value - 10);    //Ass wird als '1' und nicht als '11' gezählt
            ACE_counter--;
        }
        else {
            value.set(value.get() + card.value);
        }
    }

    public SimpleIntegerProperty cards_value() {
        return value;
    }

    public void reset_hand() {
        cards.clear();
        value.set(0);
        ACE_counter = 0;
    }

}
