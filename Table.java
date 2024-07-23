import java.util.ArrayList;
import java.util.List;

public class Table implements ICardHolder {
    private Card leadCard;
    private Player currentPlayer;
    private List<Card> center;
    private List<Player> players;

    Table() {
        players = new ArrayList<>();
        center = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void receiveCard(Card card) {
        center.add(card);
    }

    public Card getLeadCard() {
        return leadCard;
    }

    public void setLeadCard(Card leadCard) {
        this.leadCard = leadCard;
    }

    // Player turns
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int id) {
        this.currentPlayer = players.get(id);
    }

    public int getNextPlayer() {
        return (getCurrentPlayer().getPlayerID()) % players.size();
    }

    @Override
    public List<Card> getCards() {
        return center;
    }

    public boolean isSuitValid(String suit) {
        return suit.equals("c") || suit.equals("d") || suit.equals("h") || suit.equals("s");
    }

    public boolean isRankValid(String rank) {
        if (rank.length() == 1 && Character.isDigit(rank.charAt(0))) {
            int num = Character.getNumericValue(rank.charAt(0));
            return num >= 2 && num <= 9;
        } else if (rank.equals("10") || rank.equals("j") || rank.equals("q") || rank.equals("k") || rank.equals("a")) {
            return true;
        }
        return false;
    }

    public void determineFirstPlayer(Deck deck) {
        // Get a random card from the deck
        int randomIndex = (int) (Math.random() * deck.getCards().size());
        Card randomCard = deck.getCards().get(randomIndex);

        // Lead card is chosen and put in the center
        getCards().add(randomCard);
        setLeadCard(randomCard);

        // Determine the first player based on the random card
        switch (randomCard.getRank()) {
            case 14:
            case 5:
            case 9:
            case 13:
                setCurrentPlayer(0);
                break;
            case 2:
            case 6:
            case 10:
                setCurrentPlayer(1);
                break;
            case 3:
            case 7:
            case 11:
                setCurrentPlayer(2);
                break;
            case 4:
            case 8:
            case 12:
                setCurrentPlayer(3);
                break;
        }

        // Remove the random card from the deck and the current player's hand
        deck.getCards().remove(randomIndex);
    }

    // Check the winner for every trick.
    public int getWinningPlayer(List<Card> center) {
        // first card in the center will be the lead card.
        String leadSuit = center.get(0).getSuit();

        int winningIndex = 0;
        int winningRank = 0;

        // if center have 5 cards, ignore first card (first card needed to decide turns)
        for (int i = (center.size() == 5) ? 1 : 0; i < center.size(); i++) {

            Card card = center.get(i);

            if (card.getSuit().equals(leadSuit) && card.getRank() > winningRank) {
                winningIndex = i;
                winningRank = card.getRank();
            }
        }
        if (center.size() == 5) {
            winningIndex--;
        }

        int winningPlayer = (((getCurrentPlayer().getPlayerID() - 1) + winningIndex) % 4) + 1;
        if (winningPlayer < 4) {
            return winningPlayer;
        } else {
            return 0;
        }
    }

    // Check if card played valid.
    public boolean isCardPlayable(Card card) {
        // Need to override equals method
        if (leadCard == null || card.getSuit().equals(leadCard.getSuit()) || card.getRank() == leadCard.getRank()) {
            return true;
        }
        return false;
    }
}
