package Play_bluff;

import java.util.ArrayList;
import java.util.List;

class Player {
    private final String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }
    
    public Player(Player p) {
    	this.name= p.name;
    	this.hand = p.hand;
    }
    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCards(List<Card> cards) {
        hand.removeAll(cards);
    }

    @Override
    public String toString() {
        return name;
    }
}
