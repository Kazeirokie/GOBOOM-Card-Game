public class Card implements Comparable<Card> {
    public static final String[] SUITS = { "h", "d", "c", "s" };
    public static final int[] RANKS = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
    private final int rank;
    private final String suit;

    public Card(String suit, int rank) {
        this.rank = rank;
        this.suit = suit;
    }

    // need object
    public int getRank() {
        return rank;
    }

    // need object
    public String getSuit() {
        return suit;
    }

    // for scoring
    public int getPoints() {
        if (rank == 14) {
            return 1;
        } else if (rank >= 2 && rank <= 10) {
            return rank;
        } else {
            return 10;
        }
    }

    @Override
    public boolean equals(Object card) {
        // card cannot be null & must be from Card class
        if (card == null || getClass() != card.getClass()) {
            return false;
        }
        // cast as Card object
        Card other = (Card) card;
        return rank == other.rank && suit.equals(other.suit);
    }

    @Override
    public int compareTo(Card card) {
        return this.toString().compareTo(card.toString());
    }

    // change int to char
    @Override
    public String toString() {
        String rankStr;
        if (rank == 11) {
            rankStr = "J";
        } else if (rank == 12) {
            rankStr = "Q";
        } else if (rank == 13) {
            rankStr = "K";
        } else if (rank == 14) {
            rankStr = "A";
        } else {
            rankStr = Integer.toString(rank);
        }
        return suit + rankStr;
    }
}
