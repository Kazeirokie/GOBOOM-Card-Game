
public class GameLogic {

    private int numTricks = 1;
    private int cardPlays;
    private int winnerPlayer;
    private int winningRank = 0;
    private static final int NUM_CARDS_PER_HAND = 7;

    public void setWinningRank(int rank) {
        winningRank = rank;
    }

    public int getWinningRank() {
        return winningRank;
    }

    public int getNumTricks() {
        return numTricks;
    }

    public void setNumTricks(int num) {
        numTricks = num;
    }

    public void increaseNumTricks() {
        numTricks++;
    }

    public int getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(int playerIndex) {
        winnerPlayer = playerIndex;
    }

    public int getCardPlays() {
        return cardPlays;
    }

    public void setCardPlays(int num) {
        cardPlays = num;
    }

    public void increaseCardPlays() {
        cardPlays++;
    }

    // Check every card in Player's hand
    public boolean hasPlayableCard(Table table) {
        for (Card card : table.getCurrentPlayer().getCards()) {
            if (table.isCardPlayable(card))
                return true;
        }
        return false;
    }

    public int getNumCardPerPlayer() {
        return NUM_CARDS_PER_HAND;
    }

    public void initGame(Table table, Deck deck) {

        // Initialize Data
        deck.initializeDeck();
        for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            for (Player player : table.getPlayers()) {
                player.dealCards(deck);
            }
        }
        table.determineFirstPlayer(deck);
    }

    public void addPlayer(Player player, Table table) {
        table.getPlayers().add(player);
    }

    public void restartGame(Deck deck, Table table) {

        // Reset all value
        for (Player player : table.getPlayers()) {
            player.getCards().clear();
        }
        deck.getCards().clear();
        table.getCards().clear();

        setNumTricks(1);
        setCardPlays(0);
        setWinnerPlayer(-1);
        setWinningRank(0);
    }

    public void resetData(Table table, Deck deck) {
        for (Player player : table.getPlayers()) {
            player.getCards().clear();
            player.setScore(0);
            player.setTrickWon(0);
        }
        deck.getCards().clear();
        table.getCards().clear();
        setNumTricks(1);
        setCardPlays(0);
        setWinnerPlayer(-1);
        setWinningRank(0);
    }

    // Player plays their cards
    public Card playCard(String input, Deck deck, Table table) {

        // Split the input into 2 parts.
        String suit = input.substring(0, 1);
        String rank = input.substring(1);

        // Change rank from string to numbers.
        int newRank = Integer.parseInt(Key.encryptRank(rank));
        Card card = new Card(suit, newRank);

        // only return this error if there is already a lead card in the center
        if (!table.getCards().isEmpty()) {
            if (!table.isCardPlayable(card)) {
                System.out.println("You must follow the suit or the rank of the lead card, please try again.");
                return null;
            }
        } else {
            // Winner get to put new lead card to the deck.
            table.setLeadCard(card);
        }

        return card;
    }

    public void checkWinner(Card card, Table table) {
        // Get Tricks Winner
        String leadSuit = null;
        if (getNumTricks() == 1) {
            leadSuit = table.getCards().get(1).getSuit();
        } else {
            leadSuit = table.getCards().get(0).getSuit();
        }

        if (card.getSuit().equals(leadSuit) && card.getRank() > winningRank) {
            setWinnerPlayer(table.getCurrentPlayer().getPlayerID() - 1);
            winningRank = card.getRank();
        }
    }

}
