import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GUIGame extends Application {

    private final static int WINDOW_WIDTH = 1200;
    private final static int WINDOW_HEIGHT = 800;
    private final static int CARD_WIDTH = 90;
    private final static int CARD_HEIGHT = 135;
    private Stage winStage;
    private BorderPane root;
    private BorderPane topBorderPane;
    private Deck deck;
    private Table table;
    private Database data;
    private GameLogic gamelogic;

    public GUIGame() {
        deck = new Deck();
        table = new Table();
        data = new Database();
        gamelogic = new GameLogic();
        gamelogic.addPlayer(new Player(1), table);
        gamelogic.addPlayer(new Player(2), table);
        gamelogic.addPlayer(new Player(3), table);
        gamelogic.addPlayer(new Player(4), table);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        gamelogic.initGame(table, deck);

        root = new BorderPane();
        topBorderPane = new BorderPane();
        root.setStyle("-fx-background-image: url('/images/Background.jpg');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;");

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.GREEN);
        Image icon = new Image("/images/Icon.png");

        logUpdate();

        // CLOSE WINNER POPUP IF MAIN WINDOW IS CLOSED
        stage.setOnCloseRequest(event -> {
            if (winStage != null && winStage.isShowing()) {
                winStage.close();
            }
            Platform.exit();
        });

        stage.getIcons().add(icon);
        stage.setTitle("Go Boom!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    private void logUpdate() {

        // UPDATE GAME EVERY TURN
        root.setTop(topBorderPane);
        topBorderPane.setRight(scorePane());
        topBorderPane.setLeft(turnPane());
        root.setCenter(deckPane());
        root.setRight(centerPane());
        root.setBottom(playerPane());
        root.setLeft(buttonPane());

        // PRINT GAME DATA
        System.out.println(" ");
        System.out.println("TRICK #" + gamelogic.getNumTricks());
        for (int i = 0; i < table.getPlayers().size(); i++) {
            Player player = table.getPlayers().get(i);
            System.out.println("PLAYER " + player.getPlayerID() + "'s cards: " + player.getCards().toString());
        }
        System.out.println("CENTER CARDS: " + table.getCards());
        System.out.println("DECK: " + deck.getCards());
        System.out.print("SCORE: ");
        for (Player player : table.getPlayers()) {
            System.out.print("PLAYER " + player.getPlayerID() + " = " + player.getScore() + " | ");
        }
        System.out.println("CURRENT PLAYER: " + (table.getCurrentPlayer().getPlayerID()));
        System.out.println(" ");
    }

    // RESTART NEW GAME
    private void newGame() {

        // RESET DATA & CREATE NEW GAME
        gamelogic.resetData(table, deck);
        gamelogic.initGame(table, deck);
        logUpdate();
    }

    // DISPLAY NUM PLAYER & TRICKS
    public FlowPane turnPane() {

        FlowPane turnPane = new FlowPane();
        turnPane.setPadding(new Insets(0, 5, 5, 35));
        turnPane.setAlignment(Pos.CENTER);

        turnPane.setVgap(10);
        turnPane.setHgap(15);
        turnPane.setPrefWrapLength(200); // preferred width allows for two columns

        Text playerTurn = new Text();
        Text trickTurn = new Text();

        playerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        playerTurn.setFill(Color.WHITESMOKE);
        playerTurn.setLineSpacing(20);
        playerTurn.setText("Player " + (table.getCurrentPlayer().getPlayerID()));

        trickTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        trickTurn.setFill(Color.WHITESMOKE);
        trickTurn.setLineSpacing(20);
        trickTurn.setText("Trick " + gamelogic.getNumTricks());

        turnPane.getChildren().add(playerTurn);
        turnPane.getChildren().add(trickTurn);

        return turnPane;
    }

    // DISPLAY SCORE
    public FlowPane scorePane() {
        FlowPane scorePane = new FlowPane();
        scorePane.setPadding(new Insets(35, 35, 5, 5));
        scorePane.setAlignment(Pos.CENTER);

        scorePane.setVgap(10);
        scorePane.setHgap(15);
        scorePane.setPrefWrapLength(600); // preferred width allows for two columns

        for (Player player : table.getPlayers()) {
            Text score = new Text();
            score.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
            score.setFill(Color.WHITESMOKE);
            score.setLineSpacing(20);
            score.setText("Player " + player.getPlayerID() + " = " + player.getScore() + "\n ");
            scorePane.getChildren().add(score);
        }

        return scorePane;
    }

    // DISPLAY DECK
    public VBox deckPane() {
        VBox deckPane = new VBox();
        deckPane.setPadding(new Insets(5, 5, 5, 50));
        deckPane.setAlignment(Pos.CENTER);

        ImageView deckView = new ImageView();
        deckView.setFitHeight(CARD_HEIGHT);
        deckView.setFitWidth(CARD_WIDTH);

        Button deckButton = new Button();

        if (!deck.getCards().isEmpty()) {
            // Set initial deck image
            Image deckImage = new Image("/images/Deck.jpg");
            deckView.setImage(deckImage);

            deckButton.setGraphic(deckView);
            deckButton.setOnAction(e -> {
                if (deck.getCards().size() > 0) {
                    Card drawnCard = deck.getTopMostCard();
                    table.getCurrentPlayer().getCards().add(drawnCard);
                }
                Audio.deckClicked();
                Caretaker.save(gamelogic, deck, table);
                logUpdate();
            });
        } else {
            // Deck is empty
            Image outOfCardImage = new Image("/images/Deckout.png");
            deckView.setImage(outOfCardImage);
            deckButton.setGraphic(deckView);

            deckButton.setDisable(true);
        }

        deckPane.getChildren().add(deckButton);

        return deckPane;
    }

    // DISPLAY PLAYER CARDS
    public FlowPane playerPane() {

        FlowPane playerPane = new FlowPane();
        playerPane.setPadding(new Insets(25, 0, 25, 0));
        playerPane.setAlignment(Pos.CENTER);
        playerPane.setVgap(4);
        playerPane.setHgap(4);
        playerPane.setPrefWrapLength(170); // preferred width allows for two columns

        for (Card card : table.getCurrentPlayer().getCards()) {

            Button cardButton = new Button();
            cardButton.setPrefSize(CARD_WIDTH, CARD_HEIGHT);

            // Set the background image of the button based on the card name
            String imagePath = CardButton.cardImageMap.get(card.toString());

            if (imagePath != null) {
                cardButton.setStyle("-fx-background-image: url('/images/cards/" + imagePath + "');" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-size: contain;");
                if (!table.isCardPlayable(card)) {
                    // Disable unplayable card
                    cardButton.setDisable(true);
                }
            }
            cardButton.setOnAction(e -> {
                // Player still have Cards
                if (!table.getCurrentPlayer().getCards().isEmpty()) {
                    // If deck still have Cards or Player has a playable card (skip a trick)

                    if ((deck.getCards().size() != 0)
                            || (gamelogic.hasPlayableCard(table) || table.getCards().isEmpty())) {
                        Card playedCard = gamelogic.playCard(card.toString(), deck, table);

                        if (playedCard != null) {

                            table.getCurrentPlayer().getCards().remove(playedCard);
                            System.out.println(
                                    "Player " + (table.getCurrentPlayer().getPlayerID()) + " played " + playedCard
                                            + ".");

                            table.receiveCard(playedCard);

                            // Set winner as player with highest rank card
                            gamelogic.checkWinner(playedCard, table);

                            // Empty player's hand wins
                            if (table.getCurrentPlayer().getCards().isEmpty()) {
                                System.out.println(
                                        "\nPlayer " + (table.getCurrentPlayer().getPlayerID()) + " wins the game!");
                                System.out.println(
                                        "Player " + (table.getCurrentPlayer().getPlayerID()) + " manages to win "
                                                + table.getCurrentPlayer().getTrickWon() + " tricks.");

                                // Count scores earn for each player
                                for (Player player : table.getPlayers())
                                    player.setScore(player.countScore(player.getCards()));

                                // DISPLAY WINNER & SCORES
                                gameWinnerPopup(table.getCurrentPlayer().getPlayerID());

                            } else {

                                // Check if all players have played their cards
                                gamelogic.increaseCardPlays();

                                table.setCurrentPlayer(table.getNextPlayer());

                                if (gamelogic.getCardPlays() >= 4) {

                                    // In the first trick, center will have 5 cards
                                    System.out.println(
                                            "\nPlayer " + (table.getCurrentPlayer().getPlayerID()) + " wins Trick #"
                                                    + gamelogic.getNumTricks());
                                    winTrickNotification(gamelogic.getWinnerPlayer());
                                    table.setCurrentPlayer(gamelogic.getWinnerPlayer());
                                    table.getPlayers().get(gamelogic.getWinnerPlayer()).increaseTrickWon();
                                    gamelogic.increaseNumTricks();
                                    gamelogic.setCardPlays(0);
                                    gamelogic.setWinningRank(0);
                                    table.setLeadCard(null);
                                    table.getCards().clear();
                                    
                                }
                            }
                        }
                    } else {
                        // If deck still have Cards or Player has a playable card (skip a trick)
                        if ((deck.getCards().isEmpty()) && !(gamelogic.hasPlayableCard(table))
                                && !(table.getCards().isEmpty())) {

                            // Skip the player's turn in the next round.
                            System.out.println("Player " + (table.getCurrentPlayer().getPlayerID())
                                    + " does not have any playable card.");

                            showSkipTurnNotification();

                            // Check if all players have played their cards
                            gamelogic.increaseCardPlays();

                            table.setCurrentPlayer(table.getNextPlayer());

                            if (gamelogic.getCardPlays() >= 4) {

                                // In the first trick, center will have 5 cards
                                System.out.println(
                                        "\nPlayer " + (table.getCurrentPlayer().getPlayerID()) + " wins Trick #"
                                                + gamelogic.getNumTricks());
                                winTrickNotification(gamelogic.getWinnerPlayer());
                                table.setCurrentPlayer(gamelogic.getWinnerPlayer());
                                table.getPlayers().get(gamelogic.getWinnerPlayer()).increaseTrickWon();
                                gamelogic.increaseNumTricks();
                                gamelogic.setCardPlays(0);
                                gamelogic.setWinningRank(0);
                                table.setLeadCard(null);
                                table.getCards().clear();
                            }
                        }
                    }
                }

                // Play Audio
                Audio.cardClicked();
                // Save log for Redo and Undo function
                Caretaker.save(gamelogic, deck, table);
                logUpdate();
            });

            playerPane.getChildren().add(cardButton);
        }

        return playerPane;

    }

    // NOTIFY WHEN PLAYER SKIPS TURN.
    private void showSkipTurnNotification() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Turn Skipped");
        alert.setHeaderText(null);
        alert.setContentText(
                "Player " + (table.getCurrentPlayer().getPlayerID()) + " does not have any playable card.");
        alert.showAndWait();
    }

    // NOTIFY WHEN PLAYER SKIPS TURN.
    private void winTrickNotification(int winningPlayer) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Trick Winner");
        alert.setHeaderText(null);
        alert.setContentText("Player " + (winningPlayer + 1) + " wins Trick #" + gamelogic.getNumTricks());
        alert.showAndWait();
    }

    // POPUP WHEN A PLAYER WINS.
    private void gameWinnerPopup(int winningPlayer) {

        winningPlayer++;

        winStage = new Stage();
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.initStyle(StageStyle.UNDECORATED);

        BorderPane winBase = new BorderPane();
        winBase.setStyle("-fx-background-image: url('/images/winBackground.jpg');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: cover;" +
                "-fx-border-style: solid;" +
                "-fx-border-color: black;");

        VBox winner = new VBox(20);
        winner.setAlignment(Pos.CENTER);
        winner.setPadding(new Insets(20, 25, 20, 25));
        winner.setStyle("-fx-background-color:#4B2D08;");

        Text winnerText = new Text();
        winnerText.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
        winnerText.setFill(Color.WHITESMOKE);
        winnerText.setText("PLAYER " + (winningPlayer) + " WON ");

        winner.getChildren().add(winnerText);

        VBox winScore = new VBox(5);
        winScore.setAlignment(Pos.CENTER);
        winScore.setPadding(new Insets(25, 25, 10, 25));

        for (Player player : table.getPlayers()) {
            Text score = new Text();
            score.setFill(Color.WHITESMOKE);
            score.setFont(Font.font("Arial", 15));
            score.setLineSpacing(20);
            score.setText("Player " + player.getPlayerID() + " = " + player.getScore() + "\n ");
            winScore.getChildren().add(score);
        }

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.setPadding(new Insets(15, 25, 40, 25));
        Button playAgainButton = new Button("Play Again");
        Button restartButton = new Button("Restart Game");

        playAgainButton.setPrefSize(150, 50);
        playAgainButton.setOnAction(e -> {
            winStage.close();
            gamelogic.restartGame(deck, table);
            gamelogic.initGame(table, deck);
            logUpdate();
        });

        restartButton.setPrefSize(150, 50);
        restartButton.setOnAction(e -> {
            winStage.close();
            newGame();
        });

        buttons.getChildren().add(playAgainButton);
        buttons.getChildren().add(restartButton);

        winBase.setTop(winner);
        winBase.setCenter(winScore);
        winBase.setBottom(buttons);

        Scene dialogScene = new Scene(winBase, 300, 500);
        winStage.setScene(dialogScene);
        winStage.show();
    }

    // DISPLAY CENTER CARDS
    public HBox centerPane() {

        HBox centerPane = new HBox();
        centerPane.setPadding(new Insets(25, 25, 25, 25));
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPrefWidth(800);
        // centerPane.setStyle("-fx-background-color: DAE6F3;");
        centerPane.setSpacing(10);

        for (Card card : table.getCards()) {

            Label centerCard = new Label();
            centerCard.setPrefSize(CARD_WIDTH, CARD_HEIGHT);

            // Set the background image of the button based on the card name
            String imagePath = CardButton.cardImageMap.get(card.toString());

            if (imagePath != null) {
                centerCard.setStyle("-fx-background-image: url('/images/cards/" + imagePath + "');" +
                        "-fx-background-repeat: no-repeat;" +
                        "-fx-background-size: contain;");
            }

            centerPane.getChildren().add(centerCard);
        }
        return centerPane;

    }

    // DISPLAY BUTTONS
    private VBox buttonPane() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5, 20, 5, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Button newGameButton = new Button("New Game");
        Button loadGameButton = new Button("Load Game");
        Button saveGameButton = new Button("Save Game");
        Button exitGameButton = new Button("Exit Game");
        Button undoGameButton = new Button("Undo");
        Button redoGameButton = new Button("Redo");

        newGameButton.setPrefSize(150, 50);
        newGameButton.setOnAction(e -> {
            newGame();
        });

        loadGameButton.setPrefSize(150, 50);
        loadGameButton.setOnAction(e -> {
            data.loadGame(gamelogic, table, deck);
            logUpdate();
        });

        saveGameButton.setPrefSize(150, 50);
        saveGameButton.setOnAction(e -> {
            data.saveGame(gamelogic, table, deck);
        });

        exitGameButton.setPrefSize(150, 50);
        exitGameButton.setOnAction(e -> {
            Platform.exit();
        });

        undoGameButton.setPrefSize(150, 50);
        undoGameButton.setOnAction(e -> {
            Caretaker.undo(gamelogic, deck, table);
            logUpdate();

        });

        redoGameButton.setPrefSize(150, 50);
        redoGameButton.setOnAction(e -> {
            Caretaker.redo(gamelogic, deck, table);
            logUpdate();
        });

        vbox.getChildren().add(newGameButton);
        vbox.getChildren().add(loadGameButton);
        vbox.getChildren().add(saveGameButton);
        vbox.getChildren().add(exitGameButton);
        vbox.getChildren().add(undoGameButton);
        vbox.getChildren().add(redoGameButton);

        return vbox;
    }

}
