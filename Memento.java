import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Memento {

    private List<Card> deckCards;
    private List<Card> centerCards;
    private Set<Card>[] hands = new Set[4];
    private List<Integer> handSize = new ArrayList<>();
    private List<Integer> scores = new ArrayList<>();
    private List<Integer> tricksWon = new ArrayList<>();

    private int numTrick, cardPlay, winRank, winPlayer;
    private Player curPlayer;

    public Memento(GameLogic game, Deck deck, Table table) {

        // NO. TRICKS & CURRENT PLAYER & TURNS
        numTrick = game.getNumTricks();
        curPlayer = table.getCurrentPlayer();
        cardPlay = game.getCardPlays();
        winPlayer = game.getWinnerPlayer();
        winRank = game.getWinningRank();

        // SIZE FOR PLAYER CARDS / DECK / CENTER
        for (Player player : table.getPlayers()) {
            handSize.add(player.getCards().size());
        }

        // PLAYER CARDS
        for (int i = 0; i < 4; i++) {
            hands[i] = new TreeSet<>();
            hands[i].addAll(table.getPlayers().get(i).getCards());

        }

        // DECK
        deckCards = new LinkedList<>(deck.getCards());

        // CENTER
        centerCards = new LinkedList<>(table.getCards());

        // SCORES
        for (Player player : table.getPlayers()) {
            scores.add(player.getScore());
            tricksWon.add(player.getTrickWon());
        }

    }

    public void restore(GameLogic game, Deck deck, Table table) {

        game.resetData(table, deck);

        game.setNumTricks(numTrick);
        table.setCurrentPlayer(curPlayer.getPlayerID() - 1);
        game.setCardPlays(cardPlay);
        game.setWinnerPlayer(winPlayer);
        game.setWinningRank(winRank);

        // PLAYER CARDS
        for (int i = 0; i < 4; i++) {

            table.getPlayers().get(i).getCards().addAll(hands[i]);

        }

        // DECK
        deck.getCards().addAll(deckCards);

        // CENTER
        table.getCards().addAll(centerCards);
        table.setLeadCard(table.getCards().get(0));

        // SCORES

        for (int i = 0; i < 4; i++) {
            table.getPlayers().get(i).setScore(scores.get(i));
            table.getPlayers().get(i).setTrickWon(tricksWon.get(i));
        }

    }
}
