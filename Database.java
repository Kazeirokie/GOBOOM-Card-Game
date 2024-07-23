import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Database {
    private final static String DELIM = ",";
    private static int handSize[] = new int[4];
    private static int deckSize;
    private static int centerSize;

    // SAVE GAME DATA AND SET DELIMITER
    public void saveGame(GameLogic game, Table table, Deck deck) {
        try {

            FileWriter save = new FileWriter("save.txt");

            // NO. TRICKS & CURRENT PLAYER & TURNS
            save.write(Integer.toString(game.getNumTricks()) + DELIM);
            save.write(Integer.toString(table.getCurrentPlayer().getPlayerID() - 1) + DELIM);
            save.write(Integer.toString(game.getCardPlays()) + DELIM);
            save.write(Integer.toString(game.getWinnerPlayer()) + DELIM);
            save.write(Integer.toString(game.getWinningRank()) + DELIM);

            // SIZE FOR PLAYER CARDS / DECK / CENTER
            for (Player player : table.getPlayers()) {
                save.write(Integer.toString(player.getCards().size()) + DELIM);
            }
            save.write(Integer.toString(deck.getCards().size()) + DELIM);
            save.write(Integer.toString(table.getCards().size()) + DELIM);

            // PLAYER CARDS
            for (int i = 0; i < table.getPlayers().size(); i++) {
                // for (int j = 0; j < player.getCards(i).size(); j++) {
                for (Card card : table.getPlayers().get(i).getCards()) {

                    String suit = card.toString().substring(0, 1);
                    String rank = card.toString().substring(1);

                    save.write(Key.encryptSuit(suit) + DELIM + Key.encryptRank(rank) + DELIM);

                }
            }

            // DECK
            for (int i = 0; i < deck.getCards().size(); i++) {

                String suit = deck.getCards().get(i).toString().substring(0, 1);
                String rank = deck.getCards().get(i).toString().substring(1);

                save.write(Key.encryptSuit(suit) + DELIM + Key.encryptRank(rank) + DELIM);
            }

            // CENTER
            for (int i = 0; i < table.getCards().size(); i++) {

                String suit = table.getCards().get(i).toString().substring(0, 1);
                String rank = table.getCards().get(i).toString().substring(1);

                save.write(Key.encryptSuit(suit) + DELIM + Key.encryptRank(rank) + DELIM);
            }

            // SCORES
            for (Player player : table.getPlayers()) {
                save.write(Integer.toString(player.getScore()) + DELIM);
            }

            // TRICKS WON
            for (Player player : table.getPlayers()) {
                save.write(Integer.toString(player.getTrickWon()) + DELIM);
            }

            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // LOAD GAME DATA AND SEPARATE BY DELIMITER
    public void loadGame(GameLogic game, Table table, Deck deck) {
        try {
            Scanner reader = new Scanner(new FileReader("save.txt"));
            reader.useDelimiter(DELIM);

            // RESET ALL DATA
            game.resetData(table, deck);

            // NO. TRICKS & CURRENT PLAYER
            game.setNumTricks(Integer.valueOf(reader.next()));
            table.setCurrentPlayer(Integer.valueOf(reader.next()));
            game.setCardPlays(Integer.valueOf(reader.next()));
            game.setWinnerPlayer(Integer.valueOf(reader.next()));
            game.setWinningRank(Integer.valueOf(reader.next()));

            // SIZE FOR PLAYER CARDS / DECK / CENTER
            for (int i = 0; i < handSize.length; i++) {
                handSize[i] = Integer.valueOf(reader.next());
            }
            deckSize = Integer.valueOf(reader.next());
            centerSize = Integer.valueOf(reader.next());

            // PLAYER CARDS
            for (int i = 0; i < handSize.length; i++) {
                for (int j = 0; j < handSize[i]; j++) {

                    String suit = Key.decryptSuit(reader.next());
                    int rank = Integer.valueOf(reader.next());

                    table.getPlayers().get(i).getCards().add(new Card(suit, rank));
                }
            }

            // DECK
            for (int i = 0; i < deckSize; i++) {
                String suit = Key.decryptSuit(reader.next());
                int rank = Integer.valueOf(reader.next());

                deck.getCards().add(new Card(suit, rank));
            }

            // CENTER
            for (int i = 0; i < centerSize; i++) {
                String suit = Key.decryptSuit(reader.next());
                int rank = Integer.valueOf(reader.next());

                table.getCards().add(new Card(suit, rank));

                // Set new Lead Card after load saved game
                table.setLeadCard(table.getCards().get(0));
            }

            // SCORES
            // for (int i = 0; i < game.getNumPlayer(); i++) {
            // oldPlayer.setScore(i, Integer.valueOf(reader.next()));

            // }

            for (Player player : table.getPlayers())
                player.setScore(Integer.valueOf(reader.next()));

            // TRICKS WON
            // for (int i = 0; i < oldPlayer.getNumPlayers(); i++) {
            // oldPlayer.setTricksWon(i, Integer.valueOf(reader.next()));
            // }

            for (Player player : table.getPlayers())
                player.setTrickWon(Integer.valueOf(reader.next()));

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
