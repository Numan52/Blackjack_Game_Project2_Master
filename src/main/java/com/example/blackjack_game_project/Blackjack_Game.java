package com.example.blackjack_game_project;

import javafx.application.Application; //Die Anwendungsklasse stellt ein Framework zum Verwalten einer JavaFX-Anwendung bereit
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.beans.property.SimpleBooleanProperty; //damit man nicht bevor die Karten ausgeteilt wurden auf "Hit" oder "Stand" klicken kann.
import javafx.beans.property.SimpleStringProperty; // Diese Klasse stellt eine vollständige Implementierung einer Property bereit, die einen String-Wert umschließt.
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

//
public class Blackjack_Game extends Application {

    private int score_dealer = 0;
    private int score_player = 0;
    private final Text score = new Text();
    private Hand dealer; //Player Karten
    private Hand player; //Dealer Karten
    private final Deck deck = new Deck(); //Deck Karten
    private final Text end_message = new Text(); //message = System.out.println(Dealer oder Player + WON);

    private HBox dealer_cards = new HBox(20); //Der Abstand der Karten vom Dealer
    private HBox player_cards = new HBox(20); //Der Abstand der Karten vom Player

    SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
    //false, damit man nicht bevor die Karten ausgeteilt wurden auf "Hit" oder "Stand" klicken kann.
    // BIND PROPERTIES FOR Buttons

    private Parent createGame() {

        dealer = new Hand(dealer_cards.getChildren());//getChildren ist alle properties und funktionen von class parent
        player = new Hand(player_cards.getChildren());

        Pane root = new Pane(); //Screen erstellen
        Region background = new Region();
        background.setPrefSize(1280, 720); //Background größe
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 0)"); //Weiße Background (in unser Fall ist der weiße Rand)
        HBox root_layout = new HBox(-350);
        root_layout.setPadding(new Insets(10, 0, 0, 3));
        Rectangle main_rectangle = new Rectangle(1258, 670);
        Image images = new Image("com/example/blackjack_game_project/background.png");
        main_rectangle.setArcWidth(20);
        main_rectangle.setArcHeight(20);
        main_rectangle.setFill(new ImagePattern(images));
        Rectangle right_rectangle = new Rectangle(300, 500);
        right_rectangle.setArcWidth(20);      //Das ist für runde Ecken
        right_rectangle.setArcHeight(20);     //Das ist für runde Ecken
        right_rectangle.setFill(new Color(0f,0f,0f,0.3f )); //halb Transparent


        // Main Box
        VBox all_in_main_rectangle = new VBox();

        Text welcome_text = new Text("Welcome to our \nBlackjack Game");
        welcome_text.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, 30));
        welcome_text.setFill(Color.WHITE);

        Text info_text = new Text("\n(Press PLAY to start the game)\n");
        info_text.setFont(Font.font("arial", FontWeight.EXTRA_LIGHT, 15));
        info_text.setFill(Color.WHITE);

        Text dealer_score = new Text("Dealer: ");
        dealer_score.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, 20));

        Text player_score = new Text("Player: ");
        player_score.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, 20));
        all_in_main_rectangle.getChildren().addAll(dealer_score, dealer_cards, end_message, player_cards, player_score);

        //Rechte BOX
        VBox all_in_right_rectangle = new VBox(20);
        all_in_right_rectangle.setAlignment(Pos.CENTER);

        Button button_play = new Button("PLAY");
        Button button_hit = new Button("HIT");
        Button button_stand = new Button("STAND");

        HBox buttons_in_HBox = new HBox(15, button_hit, button_stand);  //Abstand von "Hit" und "Stand"
        buttons_in_HBox.setAlignment(Pos.CENTER);                  //"Hit" und "Stand" sollen in der mitte stehen

        all_in_right_rectangle.getChildren().addAll(welcome_text, info_text, score, button_play, buttons_in_HBox);


        // ADD BOTH STACKS TO ROOT LAYOUT
        root_layout.getChildren().addAll(new StackPane(main_rectangle, all_in_main_rectangle), new StackPane(right_rectangle, all_in_right_rectangle));
        root.getChildren().addAll(background, root_layout);

        button_play.disableProperty().bind(playable);   //damit man nicht immer ein neues Spiel beginnen kann, wenn man auf Play klickt
        button_hit.disableProperty().bind(playable.not()); //damit "Hit" am Anfang Disable bleibt
        button_stand.disableProperty().bind(playable.not()); //damit "Stand" am Anfang Disable bleibt

        player_score.textProperty().bind(new SimpleStringProperty("\t\t        Player: ").concat(player.value_property().asString()));
        player_score.setFill(Color.WHITE);
        //Der aktuelle Gesamtwert vom Player Karten wird nach "Player: " geschrieben

        dealer_score.textProperty().bind(new SimpleStringProperty("\n\t\t        Dealer: ").concat(dealer.value_property().asString()));
        dealer_score.setFill(Color.WHITE);
        //Der aktuelle Gesamtwert vom Dealer Karten wird nach "Dealer: " geschrieben

        player.value_property().addListener((observable_value, old_value, new_value) -> {
            if (new_value.intValue() >= 21) {
                end_game();
            }
        });

        dealer.value_property().addListener((observable_value, value, new_value) -> {
            if (new_value.intValue() >= 21) {
                end_game();
            }
        });


        button_play.setOnAction(event -> { //event ist ein Signal, das vom User verursacht wird. Z.B. Mausklick
            start_new_game(); // Aufruf vom start_new_game()
            info_text.setText("\n"); //macht ein Absatz nach info_text
        });

        button_hit.setOnAction(event -> { //Player bekommt neue Karte, immer wenn er auf "HIT" klickt
            player.take_card(deck.draw_card()); //zieht neue Karte für Player
        });

        button_stand.setOnAction(event -> {
            while (dealer.value_property().get() < 17) { //wenn der Wert von dealer Karten kleiner ist als 17, wird immer eine neue Karte gezogen
                dealer.take_card(deck.draw_card()); //zieht neue Karte für Dealer
            }

            end_game(); //end_game wird aufgerufen
        });

        return root;

    }

    private void start_new_game() {
        GridPane grid = new GridPane();
        playable.set(true); //true, damit wenn man das Spiel beginnt "HIT" und "STAND" playable werden und "PLAY" not playable wird.
        dealer_cards.setPadding(new Insets(55)); //Abstand der Dealer Karten vom Rand
        player_cards.setPadding(new Insets(55)); //Abstand der Dealer Karten vom Rand
        end_message.setText(""); //Der Text wird anfangs jeder Spiel verschwinden.  end_message => (Dealer/Player + WON)
        end_message.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, 20));

        deck.refill();  //das Deck wird erneut gefüllt.

        dealer.reset(); //Dealer Karten reset
        player.reset(); //Player Karten reset


        //take cards 4 times because function is being used 4 times (Class Deck function drawCard())
        dealer.take_card(deck.draw_card()); //eine Karte wird ausgegeben
        dealer.take_card(deck.draw_card()); //eine Karte wird ausgegeben
        player.take_card(deck.draw_card()); //eine Karte wird ausgegeben
        player.take_card(deck.draw_card()); //eine Karte wird ausgegeben

    }

    private void end_game() {

        playable.set(false); //false, damit beim Spielende nur PLAY Button Playable wird

        int dealer_cards_value = dealer.value_property().get();
        int player_cards_value = player.value_property().get();
        String winner = "Exceptional case: dealer: " + dealer_cards_value + " player: " + player_cards_value;

        // the order of checking is important
        if (dealer_cards_value == 21 || player_cards_value > 21 || dealer_cards_value == player_cards_value
                || (dealer_cards_value < 21 && dealer_cards_value > player_cards_value)) {
            winner = "DEALER";
            score_dealer++;
        }
        else  {
            winner = "PLAYER";
            score_player++;
        }

        end_message.setText("\t\t\t\t\t\t\t\t\t\t\t\t" + winner + " WON");
        end_message.setFill(Color.WHITE);
        score.setText("Dealer: " + score_dealer + "\n\nPlayer: " + score_player + "\n\n");
        score.setFont(Font.font("arial", FontWeight.EXTRA_LIGHT, 20));
        score.setFill(Color.WHITE);
    }

    @Override
    public void start(Stage main_stage) throws Exception { //start method inherited from Application class
        main_stage.setScene(new Scene(createGame()));
        main_stage.setTitle("Blackjack_Game");
        main_stage.setWidth(1280);
        main_stage.setHeight(720);
        main_stage.setResizable(false);
        main_stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args); //calls the start method above
    }
}