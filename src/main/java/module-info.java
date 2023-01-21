module com.example.blackjack_game_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.blackjack_game_project to javafx.fxml;
    exports com.example.blackjack_game_project;
}