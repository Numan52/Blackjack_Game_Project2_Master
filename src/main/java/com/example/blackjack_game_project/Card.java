package com.example.blackjack_game_project;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card extends Parent {

    private static final int CARD_WIDTH = 120; //Bereite der Karten (Weiße Fläche)
    private static final int CARD_HEIGHT = 170; //Höhe der Karten (Weiße Fläche)


    enum Suit { //Eine Liste, in der der Inhalt (HEARTS, DIAMONDS, CLUBS, SPADES) als integer gezählt wird.
        //Z.B. HEARTS = 0, DIAMONDS = 1........
        HEARTS, DIAMONDS, CLUBS, SPADES;

        final Image image;

        Suit() { //ließt die vier png Fotos in resources
            this.image = new Image(Card.class.getResourceAsStream("symbols/".concat(name().toUpperCase()).concat(".png")),
                    30, 30, true, true); // die größe
        }
    }

    enum Rank { //Wert der Karten
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(11);

        final int value;
        Rank(int value) {
            this.value = value;
        }

        String displayName() { //take the first letter from the name of the card (Z.B KING = K, ......)
            if (ordinal() < 9) {
                return String.valueOf(value);
            } else {
                return name().substring(0, 1);
            }  //function "name()" zeigt den Namen im enum
        }
    }

    public final Suit suit;
    public final Rank rank;
    public final int value;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = rank.value;

        Rectangle card_rectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        card_rectangle.setArcWidth(15); //Für die Rundung der Ecken
        card_rectangle.setArcHeight(15); ////Für die Rundung der Ecken
        card_rectangle.setFill(Color.WHITE);

        Text top_cardname_firstletter = new Text(rank.displayName());
        top_cardname_firstletter.setFont(Font.font(18));
        top_cardname_firstletter.setX(CARD_WIDTH - top_cardname_firstletter.getLayoutBounds().getWidth() - 10); //obere Buchstabe: Abstand zum Kartenrand
        top_cardname_firstletter.setY(top_cardname_firstletter.getLayoutBounds().getHeight()); //

        Text lower_cardname_firstletter = new Text(top_cardname_firstletter.getText());
        lower_cardname_firstletter.setFont(Font.font(18));
        lower_cardname_firstletter.setX(10);
        lower_cardname_firstletter.setY(CARD_HEIGHT - 10);

        ImageView view = new ImageView(suit.image);
        view.setRotate(180);
        view.setX(CARD_WIDTH - 32);
        view.setY(CARD_HEIGHT - 32);

        getChildren().addAll(card_rectangle, new ImageView(suit.image), view, top_cardname_firstletter, lower_cardname_firstletter);
    }

}
