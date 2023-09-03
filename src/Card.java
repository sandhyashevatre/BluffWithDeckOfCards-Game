package Play_bluff;

import java.util.Objects;

public class Card implements Comparable<Card>{
    public enum Rank {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    public enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card otherCard = (Card) obj;
        return suit == otherCard.suit && rank == otherCard.rank;
    }

    @Override
    public int compareTo(Card otherCard) {
    	return this.rank.compareTo(otherCard.rank);
    }
    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
    
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
    
}
