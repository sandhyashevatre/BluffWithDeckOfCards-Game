package Play_bluff;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class Table {
    private Stack<Card> cards;

    public Table() {
        cards = new Stack<>();
    }

    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
    }

    public List<Card> revealCards() {
        return new ArrayList<>(cards);
    }

    public void clear() {
        cards.clear();
    }
}
