package Play_bluff;

import java.util.ArrayList;
import java.util.List;

public class Game_Controller {
    private final List<Player> players;
    private final Deck deck;
    private final Table table;
    private int currentPlayerIndex; 

    public Game_Controller(String[] playerNames) {
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }

        deck = new Deck();
        deck.shuffle();

        table = new Table();
        currentPlayerIndex = -1; 
    }

    public void dealCards() {
        List<Player> aceOfSpadesHolders = new ArrayList<>(players);
        List<Player> playersWithAceOfSpades = new ArrayList<>();

        for (int i = 0; i < 13; i++) {
            List<Player> currentRoundPlayers = new ArrayList<>(aceOfSpadesHolders);
            
            for (Player player : currentRoundPlayers) {
                Card card = deck.dealCard();
                if (card != null) {
                    player.addCard(card);
                    System.out.println(player.getName() + " received: " + card);

                    if (card.getRank() == Card.Rank.ACE && card.getSuit() == Card.Suit.SPADES) {
                        playersWithAceOfSpades.add(player);
                    }
                }
            }

            aceOfSpadesHolders.removeAll(playersWithAceOfSpades);
            playersWithAceOfSpades.clear();
        }
    }


    public void startGame() {
        Player aceOfSpadesHolder = null;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getHand().contains(new Card(Card.Rank.ACE, Card.Suit.SPADES))) {
                aceOfSpadesHolder = player;
                currentPlayerIndex = i;
                System.out.println(player.getName() + " has the Ace of Spades and starts.");
                break;
            }
        }

        if (aceOfSpadesHolder == null) {
            System.out.println("None of the players have the Ace of Spades. Game cannot start.");
            return;
        }

        Player winner = playRounds();

        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
        } else {
            System.out.println("No winner yet.");
        }
    }



    private Player playRounds() {
        Player winner = null;

        while (winner == null) {
            Player currentPlayer = players.get(currentPlayerIndex);
            winner = getWinner();

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        return winner;
    }

    public void printGameState() {
        for (Player player : players) {
            System.out.println(player.getName() + "'s hand: " + player.getHand());
        }
        System.out.println("Table cards: " + table.revealCards());
    }

    public Player getWinner() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return player;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String[] playerNames = {"Sandhya", "Rucha", "Revathi", "Srima"};
        Game_Controller bluffGame = new Game_Controller(playerNames);
        System.out.println("Dealing cards...");
        bluffGame.dealCards();
        System.out.println("\nInitial game state:");
        bluffGame.printGameState();

        bluffGame.startGame();
    }
}
