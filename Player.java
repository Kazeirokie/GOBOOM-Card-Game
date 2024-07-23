import java.util.ArrayList;
import java.util.List;

public class Player implements ICardHolder{

    private int playerID;
    private int score;
    private int trickWon;
    private List<Card> hand;

    public Player(int playerID) {
        this.score = 0;
        this.trickWon = 0;
        this.playerID = playerID;
        this.hand = new ArrayList<>();
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getTrickWon() {
        return trickWon;
    }

    public void setTrickWon(int trickWon) {
        this.trickWon = trickWon;
    }

    public void increaseTrickWon() {
        this.trickWon++;
    }

    // Cards held by players
    @Override
    public List<Card> getCards() {
        return hand;
    }

    // Score collected by each player
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Deal cards to players and remove the card from deck
    public void dealCards(Deck deck) {
        hand.add(deck.getTopMostCard());
    }

    // Count player scores after game ends (winner decided)
    public int countScore(List<Card> hand) {

        for (int i = 0; i < hand.size(); i++) {
            for (Card card : hand) {
                score += card.getPoints();
            }
        }
        return score;
    }
}