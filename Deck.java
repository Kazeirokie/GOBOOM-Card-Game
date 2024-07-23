import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements ICardHolder {
    private List<Card> deck = new ArrayList<>();

    public Card getTopMostCard() {
        return deck.remove(0);
    }

    @Override
    public List<Card> getCards() {
        return deck;
    }

    public void initializeDeck() {
        deck.clear();
        for (String suit : Card.SUITS) {
            for (int rank : Card.RANKS) {
                deck.add(0, new Card(suit, rank));
            }
        }
        Collections.shuffle(deck);
    }
}
