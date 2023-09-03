package Play_bluff;


import java.util.ArrayList;
import java.util.List;

 

public class Hand {
    private List<Card> cards;

 

    public Hand() {
        cards = new ArrayList<>();
    }

 

    public void addCard(Card card) {
        cards.add(card);
    }

 

    public List<Card> getCards() {
        return cards;
    }

 

    public void clear() {
        cards.clear();
    }

 

    public void removeCards(List<Card> cardsToRemove) {
        cards.removeAll(cardsToRemove);
    }

 

    public void addAll(List<Card> tableCards) {
        cards.addAll(tableCards);
    }
}